package com.sbz.moviehead.ui.topRated

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbz.moviehead.api.ApiClient
import com.sbz.moviehead.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopRatedViewModel : ViewModel() {

    private val _topRatedMoviesList = MutableLiveData<List<Result>>()
    val topRatedMovieList get() = _topRatedMoviesList

    fun fetchTopRatedMovieByPage(pageNumber: Int) {
        viewModelScope.launch {
            try {
                val response = with(Dispatchers.IO) {
                    ApiClient().api.getTopRatedMovies(pageNumber)
                }
                if (response.isSuccessful && response.body() != null) {
                    val topRatedMovies = response.body()
                    topRatedMovies?.results.let {
                        _topRatedMoviesList.postValue(it)
                    }
                }

            } catch (e: Exception) {
                Log.d("TOP_RATED_EXCEPTION", e.message.toString())
            }
        }
    }

}