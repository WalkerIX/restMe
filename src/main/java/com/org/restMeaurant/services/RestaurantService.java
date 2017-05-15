package com.org.restMeaurant.services;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.org.restMeaurant.data.Restaurant;

import javax.inject.Named;

import com.org.restMeaurant.parsers.RestaurantEntityParser;
import com.org.restMeaurant.utils.Result;
import com.org.restMeaurant.utils.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.appengine.api.datastore.Query.*;

import java.util.ArrayList;
import java.util.List;


@Api(name = "restaurant", version = "v1")
public class RestaurantService extends DataStoreEndpointService {
    private final static Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public RestaurantService() {
        super();
    }

    @ApiMethod(name = "set", httpMethod = ApiMethod.HttpMethod.POST)
    public ReturnInfo setRestaurant(Restaurant restaurant, @Named("userId")String userId) {
        // check if userId is valid
        Result<Entity> userResult = queryUserById(userId);
        if (!userResult.isValid()) {
            logger.info("No valid user is found for userName: " + userId);
            return new ReturnInfo("Invalid User Name", false);
        }
        // add restaurant to dataStore
        Entity restaurantEntity = RestaurantEntityParser.restaurantToEntity(restaurant, userResult.getData());
        Key storeKey = datastoreService.put(restaurantEntity);
        return new ReturnInfo(storeKey.toString(), true);
    }

    @ApiMethod(name = "get", httpMethod = ApiMethod.HttpMethod.GET)
    public Restaurant getRestaurant(@Named("restaurant")String restaurantName, @Named("userId")String userId) {
        // check if userId is valid
        Result<Entity> userResult = queryUserById(userId);
        if (!userResult.isValid()) {
            logger.info("No valid user is found for userName: " + userId);
            return new Restaurant();
        }

        // prepare queery for restaurants
        Filter restNameFilter =
                new FilterPredicate(RestaurantEntityParser.FieldName, FilterOperator.EQUAL, restaurantName);
        Query query = new Query(Restaurant.Type).setFilter(restNameFilter).setAncestor(userResult.getData().getKey());
        Entity restaurantEntity = datastoreService.prepare(query).asSingleEntity();
        Result<Restaurant> result = restaurantEntity == null ? new Result<Restaurant>(null, false)
                : RestaurantEntityParser.entityToRestaurant(restaurantEntity);
        if (!result.isValid()) {
            logger.info("Invalid parsing result for restaurant entity: " +
                    restaurantEntity == null ? "" : restaurantEntity.toString());
            return new Restaurant();
        }
        return result.getData();
    }

    @ApiMethod(name = "search")
    public List<Restaurant> getAllRestaurantsByUser(@Named("userId")String userId){
        // check if userId is valid
        Result<Entity> userResult = queryUserById(userId);
        if (!userResult.isValid()) {
            logger.info("No valid user is found for userName: " + userId);
            return new ArrayList<Restaurant>();
        }
        // prepare queery for restaurants
        Query query = new Query(Restaurant.Type).setAncestor(userResult.getData().getKey());
        List<Entity> restaurantEntities = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        for (Entity restaurantEntity: restaurantEntities) {
            Result<Restaurant> result = RestaurantEntityParser.entityToRestaurant(restaurantEntity);
            if(!result.isValid()){
                logger.info("Invalid parsing result for restaurant entity: "+restaurantEntity.toString());
                continue;
            }
            restaurants.add(result.getData());
        }
        return restaurants;
    }
}
