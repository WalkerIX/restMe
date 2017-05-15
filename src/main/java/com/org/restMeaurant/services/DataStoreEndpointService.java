package com.org.restMeaurant.services;

import com.google.appengine.api.datastore.*;
import com.org.restMeaurant.data.User;
import com.org.restMeaurant.parsers.UserEntityParser;
import com.org.restMeaurant.utils.Result;

import java.util.List;


public class DataStoreEndpointService {
    protected DatastoreService datastoreService;

    public DataStoreEndpointService() {
        datastoreService = DatastoreServiceFactory.getDatastoreService();
    }

    protected Result<Entity> queryUserById(String userName) {
        Query.Filter userNameFilter =
                new Query.FilterPredicate(UserEntityParser.UserNameField, Query.FilterOperator.EQUAL, userName);
        Query query = new Query(User.TYPE).setFilter(userNameFilter);
        List<Entity> entities = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        if(entities.size()!=1){
            return new Result(null, false);
        }
        return new Result(entities.get(0), true);
    }

}
