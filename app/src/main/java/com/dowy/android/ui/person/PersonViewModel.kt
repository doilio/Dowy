package com.dowy.android.ui.person

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.person.Person
import com.dowy.android.model.person.PersonMovieCast
import com.dowy.android.model.person.PersonTvCast
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import com.dowy.android.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: Repository,
    preference: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var currentPersonId: Int? = null
    val language: String = preference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)!!
    private val personId = savedStateHandle.get<Int>("personId")!!

    // In Memory Caching
    private var personMovieResult: MutableLiveData<List<PersonMovieCast>>? = null
    private var personSeriesResult: MutableLiveData<List<PersonTvCast>>? = null
    private var personResult: MutableLiveData<Person>? = null
    private var currentLanguage: String? = null

    fun getPerson(): LiveData<Person> {
        val person = MutableLiveData<Person>()

        val lastResult = personResult
        if (personId == currentPersonId && language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            person.value = repository.getPerson(personId, language)
            personResult = person
        }
        return person
    }

    fun getPersonMovieList(): LiveData<List<PersonMovieCast>> {
        val personMovieCastList = MutableLiveData<List<PersonMovieCast>>()

        val lastResult = personMovieResult
        if (currentPersonId == personId && language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            personMovieCastList.value =
                when (val movieCastList = repository.getPersonMovies(personId, language)) {
                    is Result.Success -> movieCastList.data
                    is Result.Error -> null
                }
            personMovieResult = personMovieCastList
        }

        return personMovieCastList

    }

    fun getPersonSeriesList(): LiveData<List<PersonTvCast>> {
        val personTvCastList = MutableLiveData<List<PersonTvCast>>()

        val lastResult = personSeriesResult
        if (currentPersonId == personId && language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentPersonId = personId
        uiScope.launch {
            personTvCastList.value =
                when (val tvCastList = repository.getPersonSeries(personId, language)) {
                    is Result.Success -> tvCastList.data
                    is Result.Error -> null
                }
            personSeriesResult = personTvCastList
        }

        return personTvCastList

    }

}