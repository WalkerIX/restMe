package com.org.restMeaurant.data;

public enum SatisfactionLevel {
    LIKE,
    DISLIKE,
    NEUTRAL;

    public static boolean contains(String level) {
        for (SatisfactionLevel sLevel :SatisfactionLevel.values()) {
            if(sLevel.toString().contains(level)){
                return true;
            }
        }
        return false;
    }
}
