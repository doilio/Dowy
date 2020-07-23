package com.doiliomatsinhe.mymovies.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doiliomatsinhe.mymovies.data.Repository
import java.lang.IllegalArgumentException

class TvSeriesViewModelFactory(private val repository: Repository,
                               private val category: String?,
                               private val language: String?):
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvSeriesViewModel::class.java)) {
            return TvSeriesViewModel(repository, category, language) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}