package com.mzakialkhairi.prakpbs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mzakialkhairi.prakpbs.model.Feed


class MainAdapter(private val data : ArrayList<Feed> ) : RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {

    inner class MainAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val temp : TextView = itemView.findViewById(R.id.tv_temp)
        val humidity : TextView = itemView.findViewById(R.id.tv_humidity)
        val date : TextView = itemView.findViewById(R.id.tv_date)
        val dew : TextView = itemView.findViewById(R.id.tv_dew)


        fun bind (feed: Feed){
            with(itemView){
                date.text = feed.created_at
                temp.text = "Temperatur : " + feed.field1 + " F"
                if (feed.field2 == null){
                    humidity.text = "Kelembapan : -  "
                }else{
                    humidity.text = "Kelembapan : " +  feed.field2 + "%"
                }
                dew.text = "Titik Embun : "+ feed.field3
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.MainAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MainAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainAdapterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

