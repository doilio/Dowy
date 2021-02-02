package com.dowy.android.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.person.Person
import com.dowy.android.model.person.PersonMovieCast
import com.dowy.android.model.person.PersonTvCast
import com.dowy.android.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class PersonViewModel @ViewModelInject constructor(val repository: Repository) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var currentPersonId: Int? = null

    //TODO Internationalize

    // In Memory Caching
    private var personMovieResult: MutableLiveData<List<PersonMovieCast>>? = null
    private var personSeriesResult: MutableLiveData<List<PersonTvCast>>? = null
    private var personResult: MutableLiveData<Person>? = null

    fun getPerson(personId: Int): LiveData<Person> {
        val person = MutableLiveData<Person>()

        val lastResult = personResult
        if (personId == currentPersonId && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            person.value = repository.getPerson(personId,"")
            personResult = person
        }
        return person
    }

    fun getPersonMovieList(personId: Int): LiveData<List<PersonMovieCast>> {
        val personMovieCastList = MutableLiveData<List<PersonMovieCast>>()

        val lastResult = personMovieResult
        if (currentPersonId == personId && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            personMovieCastList.value =
                when (val movieCastList = repository.getPersonMovies(personId,"")) {
                    is Result.Success -> movieCastList.data
                    is Result.Error -> null
                }
            personMovieResult = personMovieCastList
        }

        return personMovieCastList

    }

    fun getPersonSeriesList(personId: Int): LiveData<List<PersonTvCast>> {
        val personTvCastList = MutableLiveData<List<PersonTvCast>>()

        val lastResult = personSeriesResult
        if (currentPersonId == personId && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            personTvCastList.value =
                when (val tvCastList = repository.getPersonSeries(personId,"")) {
                    is Result.Success -> tvCastList.data
                    is Result.Error -> null
                }
            personSeriesResult = personTvCastList
        }

        return personTvCastList

    }

}