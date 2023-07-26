package com.sbz.moviehead.ui.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbz.moviehead.adapter.PopularMoviesAdapter
import com.sbz.moviehead.databinding.FragmentPopularBinding

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null

    private val binding get() = _binding!!
    private lateinit var popularViewModel: PopularViewModel
    private lateinit var adapter: PopularMoviesAdapter
    private var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        popularViewModel =
            ViewModelProvider(this).get(PopularViewModel::class.java)

        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PopularMoviesAdapter(requireContext(), emptyList())

        binding.btnPrevPage.setOnClickListener {
            decreasePageNumber()
        }
        binding.btnNextPage.setOnClickListener {
            increasePageNumber()
        }

        popularViewModel.fetchPopularMoviesByPage(pageNumber)

        binding.rvMoviesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        popularViewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { movieList ->
            Log.d("SBZ_MOVIES_RESULT", movieList.toString())
            adapter.submitList(movieList)
        }


    }

    private fun increasePageNumber() {
        pageNumber++
        binding.tvPageNumber.text = pageNumber.toString()
        popularViewModel.fetchPopularMoviesByPage(pageNumber)
    }

    private fun decreasePageNumber() {
        if (pageNumber > 1) {
            pageNumber--
            binding.tvPageNumber.text = pageNumber.toString()
            popularViewModel.fetchPopularMoviesByPage(pageNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}