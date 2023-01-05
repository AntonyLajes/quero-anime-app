package com.example.quero_anime_app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quero_anime_app.databinding.FragmentFavoriteBinding
import com.example.quero_anime_app.ui.viewmodel.FavoriteViewModel
import com.example.quero_anime_app.util.adapter.AnimeFavoriteAdapter
import com.example.quero_anime_app.util.models.AnimeDbModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private lateinit var viewmodel: FavoriteViewModel
    private lateinit var animeFavoriteAdapter: AnimeFavoriteAdapter
    private var animeList: MutableList<AnimeDbModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        animeFavoriteAdapter = AnimeFavoriteAdapter(requireContext())
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView(){
        binding.recyclerView.apply{
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = animeFavoriteAdapter
            animeFavoriteAdapter.setOnItemClickListener(object : AnimeFavoriteAdapter.OnItemClickListener{
                override fun onClick(position: Int) {
                    findNavController().navigate(FavoriteFragmentDirections.navFavoritesToNavAnimeHome(animeList[position]))
                }
            })
        }
    }
}