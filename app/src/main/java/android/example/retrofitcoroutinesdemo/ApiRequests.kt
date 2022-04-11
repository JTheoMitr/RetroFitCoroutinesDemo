package android.example.retrofitcoroutinesdemo

import android.example.retrofitcoroutinesdemo.api.CatJson
import android.example.retrofitcoroutinesdemo.api.MainObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    //@GET("/facts/random")
    //fun getCatFacts(): Call<MainObject>

    @GET("posts?pageNumber=1&pageSize=5")
    fun getCatFacts(): Call<MainObject>
}