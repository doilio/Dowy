package com.dowy.android.ui.settings

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.dowy.android.R
import com.dowy.android.utils.*
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().onNavigateUp()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val nightModeList: ListPreference? = findPreference(NIGHT_MODE_KEY)
        val movieList: ListPreference? = findPreference(MOVIE_KEY)
        val tvList: ListPreference? = findPreference(TV_KEY)
        val languageList: ListPreference? = findPreference(LANGUAGE_KEY)

        nightModeList?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == NIGHT_MODE_ON) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }

        movieList?.setOnPreferenceChangeListener { _, newValue ->
            if (movieList.value == newValue) {
                Timber.d("Preference not altered")
            } else {
                Timber.d("Preference altered")
            }
            true
        }

        tvList?.setOnPreferenceChangeListener { _, newValue ->
            if (tvList.value == newValue) {
                Timber.d("Preference not altered")
            } else {
                Timber.d("Preference altered")
            }
            true
        }

        languageList?.setOnPreferenceChangeListener { _, newValue ->
            if (languageList.value == newValue) {
                Timber.d("Preference not altered")
            } else {
                Timber.d("Preference altered")
            }
            true
        }

    }

}