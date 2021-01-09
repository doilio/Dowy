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

    fun getPerson(personId: Int): LiveData<Person> {
        val person = MutableLiveData<Person>()

        uiScope.launch {
            person.value = repository.getPerson(personId)
        }
        return person
    }

    fun getPersonMovieList(personId: Int): LiveData<List<PersonMovieCast>> {
        val personMovieCastList = MutableLiveData<List<PersonMovieCast>>()

        uiScope.launch {
            personMovieCastList.value =
                when (val movieCastList = repository.getPersonMovies(personId)) {
                    is Result.Success -> movieCastList.data
                    is Result.Error -> null
                }
        }

        return personMovieCastList

    }

    fun getPersonSeriesList(personId: Int): LiveData<List<PersonTvCast>> {
        val personTvCastList = MutableLiveData<List<PersonTvCast>>()

        uiScope.launch {
            personTvCastList.value =
                when (val tvCastList = repository.getPersonSeries(personId)) {
                    is Result.Success -> tvCastList.data
                    is Result.Error -> null
                }
        }

        return personTvCastList

    }

}