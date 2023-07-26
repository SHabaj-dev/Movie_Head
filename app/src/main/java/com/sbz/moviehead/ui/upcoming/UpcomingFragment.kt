package com.sbz.moviehead.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbz.moviehead.adapter.UpcomingMoviesAdapter
import com.sbz.moviehead.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    private var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingMoviesAdapter = UpcomingMoviesAdapter(requireContext(), emptyList())
        upcomingViewModel.fetchUpComingMovieByPage(pageNumber)
        binding.rvMoviesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = upcomingMoviesAdapter
            binding.pbUpcoming.visibility = View.GONE
        }

        upcomingViewModel.upcomingMovies.observe(viewLifecycleOwner) { moviesList ->
            upcomingMoviesAdapter.submitList(moviesList)
        }

        binding.btnNextPage.setOnClickListener {
            increasePageNumber()
        }
        binding.btnPrevPage.setOnClickListener {
            decreasePageNumber()
        }

    }

    private fun increasePageNumber() {
        pageNumber++
        binding.tvPageNumber.text = pageNumber.toString()
        upcomingViewModel.fetchUpComingMovieByPage(pageNumber)
    }

    private fun decreasePageNumber() {
        if (pageNumber > 1) {
            pageNumber--
            binding.tvPageNumber.text = pageNumber.toString()
            upcomingViewModel.fetchUpComingMovieByPage(pageNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}