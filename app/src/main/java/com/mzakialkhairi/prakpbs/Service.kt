package com.mzakialkhairi.prakpbs

import com.mzakialkhairi.prakpbs.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    // article list
    @GET("channels/1265323/feed.json")
    fun getData(
    ) : Call<Data>

}