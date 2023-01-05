package com.example.quero_anime_app.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quero_anime_app.databinding.MostWatchedItemBinding
import com.example.quero_anime_app.util.models.AnimeDbModel

class MostWatchedAnimeAdapter(private val context: Context): RecyclerView.Adapter<MostWatchedAnimeAdapter.MyViewHolder>() {

    private var recyclerViewAnimes: List<AnimeDbModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MostWatchedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(context)
            .load(recyclerViewAnimes[position].animeImage)
            .apply(requestOptions)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .into(holder.animeImage)
        holder.animeName.text = recyclerViewAnimes[position].animeTitle
    }

    override fun getItemCount(): Int {
        return recyclerViewAnimes.size
    }

    class MyViewHolder(itemView: MostWatchedItemBinding): RecyclerView.ViewHolder(itemView.root){
        val animeImage = itemView.animeImage
        val animeName = itemView.animeName
    }

    fun getAnimeData(animeData: List<AnimeDbModel>){
        recyclerViewAnimes = animeData
        notifyDataSetChanged()
    }
}