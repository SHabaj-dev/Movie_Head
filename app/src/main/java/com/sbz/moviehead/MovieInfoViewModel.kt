package com.sbz.moviehead

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbz.moviehead.api.ApiClient
import com.sbz.moviehead.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieInfoViewModel : ViewModel() {

    private val _movieDetailsList = MutableLiveData<MovieDetails>()
    val movieDetailsList get() = _movieDetailsList

    fun getMovieDetails(movieId: Int) {

        viewModelScope.launch {
            try {
                val request = with(Dispatchers.IO) {
                    ApiClient().api.getMovieDetails(movieId)
                }

                if (request.isSuccessful && request.body() != null) {
                    val requestBody = request.body()
                    requestBody.let {
                        _movieDetailsList.postValue(requestBody!!)
                    }
                }

            } catch (e: Exception) {
                Log.d("MOVIE_DETAILS_ERROR", "${e.message}")
            }
        }
    }

}