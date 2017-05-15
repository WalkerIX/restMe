package com.org.restMeaurant.services;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.*;
import com.org.restMeaurant.data.User;
import com.org.restMeaurant.parsers.UserEntityParser;
import com.org.restMeaurant.utils.Result;
import com.org.restMeaurant.utils.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;


@Api(name = "user", version = "v1")
public class UserService extends DataStoreEndpointService{
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(){
        super();
    }
    @ApiMethod(name = "get", httpMethod = ApiMethod.HttpMethod.GET)
    public User getUser(@Named("userName")String userName) {
        Result<Entity> userIdResult = queryUserById(userName);
        // userName should be unique
        if(!userIdResult.isValid()){
            logger.info("No valid user is found for userName: "+userName);
            return new User("","");
        }
        Result<User> result = UserEntityParser.entityToUser(userIdResult.getData());
        if(!result.isValid()){
            logger.info("No valid user is found for userName: "+userName);
            return new User("","");
        }
        return result.getData();
    }

    @ApiMethod(name = "insert", httpMethod = ApiMethod.HttpMethod.POST)
    public ReturnInfo insertUser(User user){
        Result<Entity> parseResult = UserEntityParser.userToEntity(user);
        if(parseResult.isValid()) {
            Key key = datastoreService.put(parseResult.getData());
            return new ReturnInfo("Key: "+key.toString(), true);
        }
        // if the input user is not valid
        logger.warn("The input user {0} is not valid", user.toString());
        return new ReturnInfo("Invalid input user", false);
    }

}
