package com.sbz.moviehead

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.sbz.moviehead.databinding.ActivityMovieInfoBinding
import com.sbz.moviehead.utils.Constants.POSTER_URL

class MovieInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieInfoBinding
    private lateinit var movieInfoViewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("movie_data", 0)

        movieInfoViewModel = ViewModelProvider(this).get(MovieInfoViewModel::class.java)

        movieInfoViewModel.getMovieDetails(movieId)

        val circularProgressDrawable = CircularProgressDrawable(this@MovieInfoActivity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        movieInfoViewModel.movieDetailsList.observe(this) { movieList ->
            binding.apply {
                tvMovieTitle.text = movieList.title
                tvMovieTagLine.text = movieList.tagline
                tvMovieDateRelease.text = movieList.releaseDate
                tvMovieRating.text = movieList.voteAverage.toString()
                tvMovieRuntime.text = movieList.runtime.toString()
                tvMovieBudget.text = movieList.budget.toString()
                tvMovieRevenue.text = movieList.revenue.toString()
                tvMovieOverview.text = movieList.overview
                Glide.with(this@MovieInfoActivity)
                    .load(POSTER_URL + movieList.posterPath)
                    .placeholder(circularProgressDrawable)
                    .into(imgMovie)

                Glide.with(this@MovieInfoActivity)
                    .load(POSTER_URL + movieList.posterPath)
                    .placeholder(circularProgressDrawable)
                    .into(imgMovieBack)
            }

        }


    }
}