package com.org.restMeaurant.services;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.org.restMeaurant.data.User;
import com.org.restMeaurant.parsers.UserEntityParser;
import com.org.restMeaurant.utils.ReturnInfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


@Api(name = "user", version = "v1")
public class UserService extends DataStoreEndpointService{
//    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(){
        super();
    }
    @ApiMethod(name = "get", httpMethod = ApiMethod.HttpMethod.GET)
    public User getUser(User user){
        Entity userEntity = UserEntityParser.userToEntity(user);
        if(userEntity.getKind().equalsIgnoreCase(User.TYPE)) {
            try {
                Entity resultEntity = datastoreService.get(userEntity.getKey());
                return UserEntityParser.entityToUser(resultEntity);
            } catch (EntityNotFoundException e) {
//                logger.warn("Fail to get Entity for User {0}", user.toString(), e);
                return new User();
            }
        }
        // if the input user is not valid
//        logger.warn("The input user {0} is not valid", user.toString());
        return new User();
    }

    @ApiMethod(name = "insert", httpMethod = ApiMethod.HttpMethod.POST)
    public ReturnInfo insertUser(User user){
        Entity userEntity = UserEntityParser.userToEntity(user);
        if(userEntity.getKind().equalsIgnoreCase(User.TYPE)) {
            Key key = datastoreService.put(userEntity);
            return new ReturnInfo("Key: "+key.toString(), true);
        }
        // if the input user is not valid
//        logger.warn("The input user {0} is not valid", user.toString());
        return new ReturnInfo("Invalid input user", false);
    }

}
