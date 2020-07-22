package com.doiliomatsinhe.mymovies.ui.series

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.TvSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TvSeriesViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel(){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _listOfTvSeries = MutableLiveData<List<TvSeries>>()
    val listOfTvSeries: LiveData<List<TvSeries>>
        get() = _listOfTvSeries

    init {

        uiScope.launch {
            _listOfTvSeries.value = repository.getTvSeries()
        }
    }

}