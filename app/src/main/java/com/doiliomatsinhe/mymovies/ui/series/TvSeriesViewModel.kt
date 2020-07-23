package com.doiliomatsinhe.mymovies.ui.series

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.TvSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TvSeriesViewModel(
    private val repository: Repository,
    private val category: String?,
    private val language: String?
) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val listOfTvSeries = MediatorLiveData<List<TvSeries>>()

    init {

        uiScope.launch {
            repository.refreshSeries(category, language)
        }
        listOfTvSeries.addSource(repository.getSeries(), listOfTvSeries::setValue)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}