package com.example.amr.bakingapp.RetrofitAPIs;

public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}