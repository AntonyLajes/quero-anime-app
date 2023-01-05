package com.example.quero_anime_app.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.AnimeItemBinding
import com.example.quero_anime_app.util.models.AnimeDbMetaModel
import com.example.quero_anime_app.util.models.AnimeDbModel
import com.example.quero_anime_app.util.models.ResultAnimeDbModel

class AnimeHighlightsAdapter(private val context: Context): RecyclerView.Adapter<AnimeHighlightsAdapter.MyViewHolder>() {

    private var recyclerViewAnimes: List<AnimeDbModel> = ArrayList()
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view, mListener)
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
        holder.animeRank.text = recyclerViewAnimes[position].animeRanking.toString()
        holder.animeType.text = recyclerViewAnimes[position].animeType.toString()
    }

    override fun getItemCount(): Int {
        return recyclerViewAnimes.size
    }

    class MyViewHolder(itemView: AnimeItemBinding, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView.root){
        val animeImage = itemView.imageViewAnime
        val animeName = itemView.animeName
        val animeRank = itemView.animeRank
        val animeType = itemView.animeType

        init{
            itemView.root.setOnClickListener{
                listener.onClick(bindingAdapterPosition)
            }
        }
    }

    fun getAnimeData(animeData: List<AnimeDbModel>){
        recyclerViewAnimes = animeData
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
}