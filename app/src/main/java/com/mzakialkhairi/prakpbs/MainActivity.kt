package com.mzakialkhairi.prakpbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzakialkhairi.prakpbs.databinding.ActivityMainBinding
import com.mzakialkhairi.prakpbs.model.Channel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val rvData = binding.rvData

        showLoading(true)

        rvData.setHasFixedSize(true)
        rvData.layoutManager = LinearLayoutManager(this)
        viewModel.listFeed.observe(this, Observer {
            val feedAdapter = MainAdapter(it)
            rvData.adapter = feedAdapter
            showLoading(false)
        })
        viewModel.state.observe(this, Observer {
            "Fetching Data Article :".showLog(it)
        })

        viewModel.channel.observe(this, Observer {
            setChannel(it)
        })
    }

    private fun setChannel(channel : Channel){
        binding.tvNameChannel.text = "Nama Channel :" + channel.name
    }

    private fun showLoading(state : Boolean){
        if (state){
            binding.group.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.group.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun String.showLog(msg: String){
        Log.d(this,msg)
    }
}