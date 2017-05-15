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
        int priceLevel = restaurantEntity.hasProperty(FieldPriceLevel) ?
                ((Long)restaurantEntity.getProperty(FieldPriceLevel)).intValue() : 1;

        SatisfactionLevel satisfactionLevel = SatisfactionLevel.NEUTRAL;
        if(restaurantEntity.hasProperty(FieldSatisfactionLevel)){
            String level = (String) restaurantEntity.getProperty(FieldSatisfactionLevel);
            if(SatisfactionLevel.contains(level)){
                satisfactionLevel = SatisfactionLevel.valueOf(level);
            }
        }
        return new Result(new Restaurant(name, address, cuisine, priceLevel, satisfactionLevel),
                name.isEmpty() ? false : true);
    }

    public static Entity restaurantToEntity(Restaurant restaurant, Entity userEntity) {
        Entity entity = new Entity(Restaurant.Type, composeRestaurantKey(restaurant, userEntity), userEntity.getKey());
        entity.setProperty(FieldName, restaurant.getName());
        entity.setProperty(FieldAddress, restaurant.getAddress());
        entity.setProperty(FieldCuisine, restaurant.getCuisine());
        entity.setProperty(FieldPriceLevel, restaurant.getPriceLevel());
        entity.setProperty(FieldSatisfactionLevel, restaurant.getSatisfactionLevel().toString());
        return entity;
    }

    public static String composeRestaurantKey(Restaurant restaurant, Entity userEntity) {
        return composeRestaurantKey((String) userEntity.getProperty(UserEntityParser.UserNameField), restaurant.getName());
    }

    public static String composeRestaurantKey(String restaurantName, String userName) {
        return restaurantName + Seperator + userName;
    }
}
