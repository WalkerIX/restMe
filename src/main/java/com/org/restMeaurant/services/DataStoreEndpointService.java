package com.org.restMeaurant.services;

import com.google.appengine.api.datastore.*;
import com.org.restMeaurant.data.User;
import com.org.restMeaurant.parsers.UserEntityParser;

import java.util.List;

public class DataStoreEndpointService {
    protected DatastoreService datastoreService;

    public DataStoreEndpointService() {
        datastoreService = DatastoreServiceFactory.getDatastoreService();
    }

    protected List<Entity> queryUserById(String userName) {
        Query.Filter userNameFilter =
                new Query.FilterPredicate(UserEntityParser.UserNameField, Query.FilterOperator.EQUAL, userName);
        Query query = new Query(User.TYPE).setFilter(userNameFilter);
        return datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
    }

}
