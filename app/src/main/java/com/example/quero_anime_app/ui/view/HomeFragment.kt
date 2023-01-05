package com.example.quero_anime_app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quero_anime_app.databinding.FragmentHomeBinding
import com.example.quero_anime_app.ui.model.remote.FirebaseClient
import com.example.quero_anime_app.ui.viewmodel.HomeViewModel
import com.example.quero_anime_app.util.adapter.AnimeHighlightsAdapter
import com.example.quero_anime_app.util.adapter.MostMovieWatchedAnimeAdapter
import com.example.quero_anime_app.util.adapter.MostSerieWatchedAnimeAdapter
import com.example.quero_anime_app.util.adapter.MostWatchedAnimeAdapter
import com.example.quero_anime_app.util.models.AnimeDbModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var viewmodel: HomeViewModel
    private lateinit var animeHighlightsAdapter: AnimeHighlightsAdapter
    private lateinit var mostWatchedAnimeAdapter: MostWatchedAnimeAdapter
    private lateinit var mostMovieWatchedAnimeAdapter: MostMovieWatchedAnimeAdapter
    private lateinit var mostSerieWatchedAnimeAdapter: MostSerieWatchedAnimeAdapter
    private var animeList: MutableList<AnimeDbModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        animeHighlightsAdapter = AnimeHighlightsAdapter(requireContext())
        mostWatchedAnimeAdapter = MostWatchedAnimeAdapter(requireContext())
        mostMovieWatchedAnimeAdapter = MostMovieWatchedAnimeAdapter(requireContext())
        mostSerieWatchedAnimeAdapter = MostSerieWatchedAnimeAdapter(requireContext())
        //getAPIData()
        observers()
        setupRecyclerView()
        return binding.root
    }

    private fun getAPIData() {
        viewmodel.getAleatoryAnimes()
        viewmodel.getMostWatched()
        viewmodel.getMostSerieWatched()
        viewmodel.getMostMovieWatched()
    }

    private fun setupRecyclerView() {
        binding.animesSlider.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = animeHighlightsAdapter
            animeHighlightsAdapter.setOnItemClickListener(object :
                AnimeHighlightsAdapter.OnItemClickListener {
                override fun onClick(position: Int) {
                    findNavController().navigate(
                        HomeFragmentDirections.navHomeToNavAnimeHome(
                            animeList[position]
                        )
                    )
                }
            })
        }
        binding.mostWatchedContent.mostWatchedSlider.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostWatchedAnimeAdapter
        }
        binding.mostWatchedSerieContent.mostWatchedSlider.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostSerieWatchedAnimeAdapter
        }
        binding.mostWatchedMovieContent.mostWatchedSlider.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostMovieWatchedAnimeAdapter
        }
    }

    private fun observers() {
        viewmodel.resultAnimeModels.observe(requireActivity()) { animesData ->
            animeHighlightsAdapter.getAnimeData(animesData)
            animeList = animesData.toMutableList()
        }
        viewmodel.resultMostWatchedAnimeModels.observe(requireActivity()) { animesData ->
            mostWatchedAnimeAdapter.getAnimeData(animesData.data)
        }
        viewmodel.resultSerieMostWatchedAnimeModels.observe(requireActivity()) { animesData ->
            val animeList: MutableList<AnimeDbModel> = ArrayList()
            for (anime in animesData.data) {
                if (anime.animeType == "TV" && animeList.size < 10) {
                    animeList.add(anime)
                }
            }
            mostSerieWatchedAnimeAdapter.getAnimeData(animeList)
        }
        viewmodel.resultMovieMostWatchedAnimeModels.observe(requireActivity()) { animesData ->
            val animeList: MutableList<AnimeDbModel> = ArrayList()
            for (anime in animesData.data) {
                if (anime.animeType == "Movie" && animeList.size < 11) {
                    animeList.add(anime)
                }
            }
            mostMovieWatchedAnimeAdapter.getAnimeData(animeList)
        }
    }
}