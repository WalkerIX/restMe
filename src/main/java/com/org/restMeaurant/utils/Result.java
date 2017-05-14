package com.org.restMeaurant.utils;

public class Result<T> {
    private T data;
    private boolean isValid;

    public Result(final T data, final boolean isValid) {
        this.data = data;
        this.isValid = isValid;
    }

    public T getData() {
        return data;
    }

    public boolean isValid() {
        return isValid;
    }
}
