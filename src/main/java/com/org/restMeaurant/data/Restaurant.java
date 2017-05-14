package com.org.restMeaurant.data;

public class Restaurant {
    public static final String Type = "Restaurant";
    private String name;
    private String address;
    private String cuisine;
    private int priceLevel;
    private SatisfactionLevel satisfactionLevel;

    public Restaurant(){
        name = "";
        address = "";
        cuisine = "";
        priceLevel = 1;
        satisfactionLevel = SatisfactionLevel.NEUTRAL;
    }

    public Restaurant(String name, String address, String cuisine, int priceLevel, SatisfactionLevel satisfactionLevel) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.priceLevel = priceLevel;
        this.satisfactionLevel = satisfactionLevel;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public SatisfactionLevel getSatisfactionLevel() {
        return satisfactionLevel;
    }

}
