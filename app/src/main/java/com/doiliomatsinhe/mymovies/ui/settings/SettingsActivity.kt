package com.doiliomatsinhe.mymovies.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doiliomatsinhe.mymovies.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }
}