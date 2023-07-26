package com.sbz.moviehead.ui.topRated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbz.moviehead.adapter.TopRatedMoviesAdapter
import com.sbz.moviehead.databinding.FragmentTopRatedBinding

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null

    private val binding get() = _binding!!
    private lateinit var topRatedViewModel: TopRatedViewModel
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        topRatedViewModel =
            ViewModelProvider(this).get(TopRatedViewModel::class.java)

        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topRatedMoviesAdapter = TopRatedMoviesAdapter(requireContext(), emptyList())

        topRatedViewModel.fetchTopRatedMovieByPage(pageNumber)

        binding.rvMoviesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = topRatedMoviesAdapter
            binding.pbPopular.visibility = View.GONE
        }

        binding.btnNextPage.setOnClickListener {
            increasePageNumber()
        }
        binding.btnPrevPage.setOnClickListener {
            decreasePageNumber()
        }

        topRatedViewModel.topRatedMovieList.observe(viewLifecycleOwner) { topRatedMovies ->
            topRatedMoviesAdapter.submitList(topRatedMovies)
        }

    }

    private fun increasePageNumber() {
        pageNumber++
        binding.tvPageNumber.text = pageNumber.toString()
        topRatedViewModel.fetchTopRatedMovieByPage(pageNumber)
    }

    private fun decreasePageNumber() {
        if (pageNumber > 1) {
            pageNumber--
            binding.tvPageNumber.text = pageNumber.toString()
            topRatedViewModel.fetchTopRatedMovieByPage(pageNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}