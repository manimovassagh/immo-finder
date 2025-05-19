package com.github.manimovassagh.immo_finder.rent_service.util;

public class ApiResponse<T> {
    private String result;    // SUCCESS or ERROR
    private String message;   // success or error message
    private T data;           // return object from service class, if successful

    public ApiResponse() {
    }

    public ApiResponse(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
} 