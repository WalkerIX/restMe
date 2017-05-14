package com.org.restMeaurant.services;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.org.restMeaurant.data.Restaurant;
import com.org.restMeaurant.data.User;

import javax.inject.Named;

import com.org.restMeaurant.parsers.RestaurantEntityParser;
import com.org.restMeaurant.utils.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Api(name = "restaurant", version = "v1")
public class RestaurantService extends DataStoreEndpointService {
    private final static Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public RestaurantService() {
        super();
    }

    @ApiMethod(name = "set", httpMethod = ApiMethod.HttpMethod.POST)
    public ReturnInfo setRestaurant(Restaurant restaurant, @Named("restaurant")String userName) {
        // check if userId is valid
        List<Entity> userEntities = queryUserById(userName);
        if (userEntities.size() != 1) {
            logger.info("No valid user is found for userName: " + userName);
            return new ReturnInfo("Invalid User Name", false);
        }
        // add restaurant to dataStore
        Entity restaurantEntity = RestaurantEntityParser.restaurantToEntity(restaurant, userEntities.get(0));
        Key storeKey = datastoreService.put(restaurantEntity);
        return new ReturnInfo(storeKey.toString(), true);
    }

    @ApiMethod(name = "get", httpMethod = ApiMethod.HttpMethod.GET)
    public Restaurant getRestaurant(@Named("restaurant")String restaurantName, User user){
        return null;
    }

    @ApiMethod(name = "search")
    public Restaurant getAllRestaurantsByUser(Restaurant restaurant){
        return null;
    }
}
