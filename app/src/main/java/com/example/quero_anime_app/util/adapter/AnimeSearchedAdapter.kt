package com.example.quero_anime_app.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quero_anime_app.databinding.AnimeListItemBinding
import com.example.quero_anime_app.util.models.AnimeDbModel

class AnimeSearchedAdapter(private val context: Context): RecyclerView.Adapter<AnimeSearchedAdapter.MyViewHolder>() {

    private var recyclerViewAnimes: List<AnimeDbModel> = ArrayList()
    private lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = AnimeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.animeSynopsis.text = recyclerViewAnimes[position].animeSynopsis
        holder.animeRank.text = recyclerViewAnimes[position].animeRanking.toString()
        holder.animeType.text = recyclerViewAnimes[position].animeType
    }

    override fun getItemCount(): Int {
        return recyclerViewAnimes.size
    }

    class MyViewHolder(itemView: AnimeListItemBinding, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView.root){
        var animeName = itemView.animeName
        var animeImage = itemView.animeImage
        var animeRank = itemView.animeRank
        var animeType = itemView.animeType
        var animeSynopsis = itemView.animeSynopsis

        init {
            itemView.root.setOnClickListener {
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