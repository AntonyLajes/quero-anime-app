package com.example.quero_anime_app.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.FragmentAnimeHomeBinding
import com.example.quero_anime_app.ui.viewmodel.AnimeHomeViewModel

class AnimeHomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAnimeHomeBinding? = null
    private val binding: FragmentAnimeHomeBinding get() = _binding!!
    private lateinit var viewmodel: AnimeHomeViewModel
    private val navigationArgs: AnimeHomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeHomeBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(AnimeHomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeDataHandler()
        initClicks()
        observers()
        viewmodel.getAnimeById(navigationArgs.animeData.animeId)
    }

    private fun animeDataHandler(){
        binding.animeName.text = navigationArgs.animeData.animeTitle
        binding.animeRank.text = navigationArgs.animeData.animeRanking.toString()
        binding.animeType.text = navigationArgs.animeData.animeType
        binding.animeGender.text = navigationArgs.animeData.animeGenres.toString()
        binding.animeSynopsis.text = navigationArgs.animeData.animeSynopsis
        val urlImage = navigationArgs.animeData.animeImage
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(requireContext())
            .load(urlImage)
            .apply(requestOptions)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .into(binding.animeImage)
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonSeeDetails.id -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(navigationArgs.animeData.animeLink))
                startActivity(i)
            }
            binding.buttonFavorite.id -> {
                viewmodel.favoriteHandler(navigationArgs.animeData.animeId)
            }
        }
    }

    private fun initClicks(){
        binding.buttonSeeDetails.setOnClickListener(this)
        binding.buttonFavorite.setOnClickListener(this)
    }

    private fun observers(){
        viewmodel.favoriteResult.observe(requireActivity()){ insertion ->
            if(insertion.result){
               binding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
        viewmodel.hasFavorited.observe(requireActivity()){ favorite ->
            if(favorite){
                binding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }else {
                binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun makeToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}