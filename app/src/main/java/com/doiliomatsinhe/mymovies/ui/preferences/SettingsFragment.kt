package com.doiliomatsinhe.mymovies.ui.preferences

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.doiliomatsinhe.mymovies.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val nightModeList: ListPreference? = findPreference("night_mode_list")
        val categoryList: ListPreference? = findPreference("category_list")
        val languageList: ListPreference? = findPreference("language_list")

        nightModeList?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(requireContext(), newValue.toString(), Toast.LENGTH_SHORT).show()
            true
        }

        categoryList?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(requireContext(), newValue.toString(), Toast.LENGTH_SHORT).show()
            true
        }

        languageList?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(requireContext(), newValue.toString(), Toast.LENGTH_SHORT).show()
            true
        }


    }


}