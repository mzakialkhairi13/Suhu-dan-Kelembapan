package com.mzakialkhairi.prakpbs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzakialkhairi.prakpbs.model.Channel
import com.mzakialkhairi.prakpbs.model.Data
import com.mzakialkhairi.prakpbs.model.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private  val _listFeed = MutableLiveData<ArrayList<Feed>>()
    val listFeed : LiveData<java.util.ArrayList<Feed>>
        get() = _listFeed

    private  val _channel = MutableLiveData<Channel>()
    val channel : LiveData<Channel>
        get() = _channel

    val _state = MutableLiveData<String>()
    val state : LiveData<String>
        get() = _state

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _state.value = ""
        getListFeed()
        getChannel()
    }

    fun getChannel(){
        uiScope.launch {
            try {
                RetrofitInstance.INSTANCE_ARTICLE.getData().enqueue(object :
                    Callback<Data> {
                    override fun onResponse(call: Call<Data>, response: Response<Data>) {
                        val listResponse = response.body()?.channel
                        _channel.value = listResponse
                    }
                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        Log.e("Get Article API",t.message.toString())
                        _state.value = t.message.toString()
                    }
                })
            }catch (t:Throwable){
                _state.value = "Tidak ada koneksi internet"
            }
        }
    }

    fun getListFeed(){
        uiScope.launch {
            try {
                RetrofitInstance.INSTANCE_ARTICLE.getData().enqueue(object :
                    Callback<Data> {
                    override fun onResponse(call: Call<Data>, response: Response<Data>) {
                        val listResponse = response.body()?.feeds

                        if (listResponse!!.isEmpty()){
                            _state.value = "Data Kosong"
                        }
                        else{
                            listResponse.let { _listFeed.value = listResponse as ArrayList<Feed>? }
                            _state.value = "Data fetching is completed"
                        }
                    }
                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        Log.e("Get Article API",t.message.toString())
                        _state.value = t.message.toString()
                    }
                })
            }catch (t:Throwable){
                _state.value = "Tidak ada koneksi internet"
            }

        }

    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}