package com.doiliomatsinhe.mymovies.ui.settings

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.utils.CATEGORY_KEY
import com.doiliomatsinhe.mymovies.utils.LANGUAGE_KEY
import com.doiliomatsinhe.mymovies.utils.NIGHT_MODE_KEY
import dagger.hilt.android.AndroidEntryPoint
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
        val categoryList: ListPreference? = findPreference(CATEGORY_KEY)
        val languageList: ListPreference? = findPreference(LANGUAGE_KEY)

        nightModeList?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == "2") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }

        categoryList?.setOnPreferenceChangeListener { _, newValue ->
            if (categoryList.value == newValue) {
                Timber.d("Preference not altered")
            } else {
                Timber.d("Preference altered")
                //uiScope.launch { invalidateCurrentData() }

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