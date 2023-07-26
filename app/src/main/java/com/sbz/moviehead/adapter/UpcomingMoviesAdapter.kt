package com.sbz.moviehead.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.sbz.moviehead.MovieInfoActivity
import com.sbz.moviehead.R
import com.sbz.moviehead.model.Result
import com.sbz.moviehead.utils.Constants

class UpcomingMoviesAdapter(private val context: Context, private var moviesList: List<Result>) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val movieTitle: TextView = item.findViewById(R.id.tv_movieName)
        val movieRating: TextView = item.findViewById(R.id.tv_movieRating)
        val movieReleaseDate: TextView = item.findViewById(R.id.tv_releaseDate)
        val movieLanguage: TextView = item.findViewById(R.id.tv_movieLanguage)
        val moviePoster: ShapeableImageView = item.findViewById(R.id.iv_moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = moviesList[position]
        holder.movieTitle.text = current.title
        holder.movieRating.text = current.vote_average.toString()
        holder.movieReleaseDate.text = current.release_date
        holder.movieLanguage.text = current.original_language

        val posterPath = Constants.POSTER_URL + current.poster_path
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(context)
            .load(posterPath)
            .placeholder(circularProgressDrawable)
            .into(holder.moviePoster)

        holder.itemView.setOnClickListener {
            val movieId = moviesList[position].id

            val intent = Intent(context, MovieInfoActivity::class.java)
            intent.putExtra("movie_data", movieId)
            context.startActivity(intent)
        }
    }

    fun submitList(newList: List<Result>) {
        moviesList = newList
        notifyDataSetChanged()
    }

}