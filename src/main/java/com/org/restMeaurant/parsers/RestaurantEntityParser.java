package com.org.restMeaurant.parsers;

import com.google.appengine.api.datastore.Entity;
import com.org.restMeaurant.data.Restaurant;
import com.org.restMeaurant.data.SatisfactionLevel;
import com.org.restMeaurant.utils.Result;


public class RestaurantEntityParser {

    public static final String FieldName = "name";
    public static final String FieldAddress = "address";
    public static final String FieldCuisine = "cuisine";
    public static final String FieldPriceLevel = "priceLevel";
    public static final String FieldSatisfactionLevel = "satisfactionLevel";

    public static final String Seperator = "\t";

    private RestaurantEntityParser() {

    }

    public static Result<Restaurant> entityToRestaurant(Entity restaurantEntity) {
        String name = restaurantEntity.hasProperty(FieldName) ? (String) restaurantEntity.getProperty(FieldName) : "";
        String address = restaurantEntity.hasProperty(FieldAddress) ? (String) restaurantEntity.getProperty(FieldAddress) : "";
        String cuisine = restaurantEntity.hasProperty(FieldCuisine) ? (String) restaurantEntity.getProperty(FieldCuisine) : "";
        int priceLevel = restaurantEntity.hasProperty(FieldPriceLevel) ? (int) restaurantEntity.getProperty(FieldPriceLevel) : 1;
        SatisfactionLevel satisfactionLevel = restaurantEntity.hasProperty(FieldSatisfactionLevel) ?
                (SatisfactionLevel) restaurantEntity.getProperty(FieldSatisfactionLevel) : SatisfactionLevel.NEUTRAL;
        return new Result<Restaurant>(new Restaurant(name, address, cuisine, priceLevel, satisfactionLevel),
                name.isEmpty() ? false : true);
    }

    public static Entity restaurantToEntity(Restaurant restaurant, Entity userEntity) {
        Entity entity = new Entity(Restaurant.Type, composeRestaurantKey(restaurant, userEntity), userEntity.getKey());
        entity.setProperty(FieldName, restaurant.getName());
        entity.setProperty(FieldAddress, restaurant.getAddress());
        entity.setProperty(FieldCuisine, restaurant.getCuisine());
        entity.setProperty(FieldPriceLevel, restaurant.getPriceLevel());
        entity.setProperty(FieldSatisfactionLevel, restaurant.getSatisfactionLevel());
        return entity;
    }

    private static String composeRestaurantKey(Restaurant restaurant, Entity userEntity) {
        return userEntity.getProperty(UserEntityParser.UserNameField) + Seperator + restaurant.getName();
    }
}
