package com.doiliomatsinhe.mymovies.ui.about

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doiliomatsinhe.mymovies.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return AboutPage(context)
            .isRTL(false)
            //.setImage(R.drawable.guide_icon)
            .setDescription(getString(R.string.about_descricao))
            .addEmail(getString(R.string.dev_email), getString(R.string.envie_email))
            .addPlayStore(
                getString(R.string.about_app_link),
                getString(R.string.playstore_page)
            )

            .addGroup(getString(R.string.redes_sociais))
            .addInstagram(getString(R.string.dev_instagram_page), getString(R.string.instagram))
            .addGitHub(getString(R.string.dev_github_page), getString(R.string.github))
            .addFacebook(getString(R.string.dev_facebook_page), getString(R.string.facebook))
            .addItem(createCopyright())
            .create()
    }

    private fun createCopyright(): Element {
        val copyright = Element()
        val copyrightString = String.format(
            "Copyright %d by My Movies", Calendar.getInstance()
                .get(Calendar.YEAR)
        )
        copyright.title = copyrightString
        copyright.iconDrawable = R.drawable.guide_icon
        copyright.gravity = Gravity.CENTER
        return copyright

    }
}