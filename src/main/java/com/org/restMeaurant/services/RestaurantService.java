package com.org.restMeaurant.services;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.org.restMeaurant.data.Restaurant;
import com.org.restMeaurant.data.User;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(name = "restaurant", version = "v1")
public class RestaurantService extends DataStoreEndpointService {
    private final static Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public RestaurantService() {
        super();
    }

    @ApiMethod(name = "get", httpMethod = ApiMethod.HttpMethod.GET)
    public Restaurant getRestaurant(@Named("times")String restaurantName, User user){
        return null;
    }

    @ApiMethod(name = "search")
    public Restaurant getAllRestaurantsByUser(Restaurant restaurant){
        return null;
    }
}
