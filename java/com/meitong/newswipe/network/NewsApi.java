package com.meitong.newswipe.network;

import com.meitong.newswipe.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize);

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("q") String query, @Query("pageSize") int pageSize);
}
