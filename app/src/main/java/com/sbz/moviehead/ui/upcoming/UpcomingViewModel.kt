package com.sbz.moviehead.ui.upcoming

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbz.moviehead.api.ApiClient
import com.sbz.moviehead.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {

    private var _upcomingMoviesList = MutableLiveData<List<Result>>()
    val upcomingMovies get() = _upcomingMoviesList


    fun fetchUpComingMovieByPage(pageNumber: Int) {

        viewModelScope.launch {
            try {
                val response = with(Dispatchers.IO) {
                    ApiClient().api.getUpcomingMovies(pageNumber)
                }

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    responseBody?.results?.let { upcomingMovies ->
                        _upcomingMoviesList.postValue(upcomingMovies)
                    }
                }

            } catch (e: Exception) {
                Log.d("UPCOMING_EXCEPTION", "${e.message}")
            }
        }
    }
}