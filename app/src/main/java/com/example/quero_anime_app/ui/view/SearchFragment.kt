package com.example.quero_anime_app.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quero_anime_app.databinding.FragmentSearchBinding
import com.example.quero_anime_app.ui.viewmodel.SearchViewModel
import com.example.quero_anime_app.util.adapter.AnimeSearchedAdapter
import com.example.quero_anime_app.util.models.AnimeDbModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var viewbinding: SearchViewModel
    private lateinit var animeSearchedAdapter: AnimeSearchedAdapter
    private var animeList: MutableList<AnimeDbModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewbinding = ViewModelProvider(this).get(SearchViewModel::class.java)
        animeSearchedAdapter = AnimeSearchedAdapter(requireContext())
        setupRecyclerView()
        observers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchHandler()
    }

    override fun onResume() {
        super.onResume()
        if(binding.inputAnimeName.text.toString().isNotEmpty()){
            fieldHandler()
            viewbinding.getAnimeByName(binding.inputAnimeName.text.toString())
        }
    }

    private fun observers() {
        viewbinding.animeList.observe(viewLifecycleOwner) {
            animeList = it.data.toMutableList()
            animeSearchedAdapter.getAnimeData(it.data)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = animeSearchedAdapter
            animeSearchedAdapter.setOnItemClickListener(object : AnimeSearchedAdapter.OnItemClickListener {
                override fun onClick(position: Int) {
                    findNavController().navigate(SearchFragmentDirections.navSearchToNavAnimeHome(animeList[position]))
                }

            })
        }
    }

    private fun searchHandler() {
        binding.inputAnimeName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (text != null) {
                    fieldHandler()
                    viewbinding.getAnimeByName(text.toString())
                }
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text != null) {
                    fieldHandler()
                    viewbinding.getAnimeByName(text.toString())
                }
            }

            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    val s = text
                }
            }

        })
    }

    private fun fieldHandler() {
        val animeSearchInput = binding.inputAnimeName.text
        if (animeSearchInput.isNullOrEmpty()) {
            binding.tvSearchAdvise.isVisible = false
            binding.recyclerView.isVisible = true
        }
    }
}