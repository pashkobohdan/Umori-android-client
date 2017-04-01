package com.pashkobohdan.umori_jokesandstories.data.retrofitApi;

import com.pashkobohdan.umori_jokesandstories.data.model.PostModel;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bohdan on 25.03.17.
 */

public interface UmoriliApi {

    @GET("/api/get")
    Call<List<PostModel>> getData(@Query("site") String siteName, @Query("name") String resourceName, @Query("num") int count);

    @GET("/api/sources")
    Call<SourceModel[][]> getSources();


    @GET("/api/random")
    Call<List<PostModel>> getRandomData(@Query("num") int count);

}
