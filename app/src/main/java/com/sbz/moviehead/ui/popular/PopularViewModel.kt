package com.sbz.moviehead.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbz.moviehead.api.ApiClient
import com.sbz.moviehead.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<List<Result>>()
    val popularMoviesLiveData get() = _popularMoviesLiveData


    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData get() = _errorLiveData

    fun fetchPopularMoviesByPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient().api.getPopularMovies(page)
                if (response.isSuccessful) {
                    val popularMovies = response.body()
                    popularMovies?.results?.let { list ->
                        _popularMoviesLiveData.postValue(list)
                    }
                } else {
                    _errorLiveData.postValue("Error fetching popular movies.")
                }
            } catch (e: Exception) {
                _errorLiveData.postValue("Error fetching popular movies: ${e.message}")
            }
        }
    }

}