package com.example.amr.bakingapp.RetrofitAPIs;

import com.example.amr.bakingapp.Models.BakingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<BakingResponse>> getBaking();
}