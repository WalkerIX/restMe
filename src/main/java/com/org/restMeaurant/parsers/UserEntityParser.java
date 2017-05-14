package com.org.restMeaurant.parsers;


import com.google.appengine.api.datastore.Entity;
import com.org.restMeaurant.data.User;
import com.org.restMeaurant.utils.Result;

public class UserEntityParser {

    public final static String UserNameField = "userName";
    public final static String UserFirstNameField = "userFirstName";

    private UserEntityParser() {
    }

    public static Result<Entity> userToEntity(User user) {
        if(user.getUserId().isEmpty()){
            return new Result(null, false);
        }
        Entity entity = new Entity(User.TYPE, user.getUserId());
        entity.setProperty(UserNameField, user.getUserId());
        entity.setProperty(UserFirstNameField, user.getFirstName());
        return new Result(entity, true);
    }

    public static Result<User> entityToUser(Entity entity) {
        String userName = !entity.hasProperty(UserNameField) ? "" : (String) entity.getProperty(UserNameField);
        String userFirstName = !entity.hasProperty(UserFirstNameField) ? "" : (String) entity.getProperty(UserFirstNameField);
        return new Result(new User(userName, userFirstName), userName.isEmpty()?false:true);
    }
}
