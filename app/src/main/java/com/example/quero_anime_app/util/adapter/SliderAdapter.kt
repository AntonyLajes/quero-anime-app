package com.example.quero_anime_app.util.adapter

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quero_anime_app.R
import com.example.quero_anime_app.util.models.SliderItem
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class SliderAdapter (
    sliderItems: MutableList<SliderItem>,
    viewPager: ViewPager2,
    private val context: Context
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val sliderItems: List<SliderItem>
    private var sliderImages: List<String>
    private val viewPager2: ViewPager2
    private val runnable = Runnable{
        sliderItems.addAll(sliderItems)
    }
    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager
        this.sliderImages = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val urlImage = sliderImages[position]
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(context)
            .load(urlImage)
            .apply(requestOptions)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .into(holder.imageView)
        if(position == sliderImages.size - 1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: RoundedImageView = itemView.findViewById(R.id.image_slide)
    }

    fun getImagesUrl(urlList: List<String>){
        sliderImages = urlList
        notifyDataSetChanged()
    }

}