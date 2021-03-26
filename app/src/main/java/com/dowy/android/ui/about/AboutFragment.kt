package com.dowy.android.ui.about

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dowy.android.R
import com.dowy.android.utils.NIGHT_MODE_KEY
import com.dowy.android.utils.NIGHT_MODE_OFF
import com.dowy.android.utils.NIGHT_MODE_ON
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        launchInAppReviewFlow()
        val visible = sharedPreferences.getString(NIGHT_MODE_KEY, NIGHT_MODE_OFF) == NIGHT_MODE_ON

        val aboutPage = AboutPage(context)
            .isRTL(false)
            .enableDarkMode(visible)
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

        return if (resources.getBoolean(R.bool.is_tablet)) {
            aboutPage
                .setImage(R.drawable.guide_icon)
                .create()
        } else {
            aboutPage.create()
        }
    }

    private fun launchInAppReviewFlow() {
        val manager: ReviewManager = ReviewManagerFactory.create(requireContext())

        val request = manager.requestReviewFlow()
        request.addOnCompleteListener {
            if (it.isSuccessful) {
                // We got the review object
                val reviewInfo = it.result

                val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                flow.addOnCompleteListener {
                    // Flow is finished...
                    // Don't inform the user if error occurs.... Continue with normal flow
                }
            }
        }
    }

    private fun createCopyright(): Element {
        val copyright = Element()
        val copyrightString = String.format(
            "Copyright %d by Dowy", Calendar.getInstance()
                .get(Calendar.YEAR)
        )
        copyright.title = copyrightString
        copyright.iconDrawable = R.drawable.guide_icon
        copyright.gravity = Gravity.CENTER
        return copyright

    }
}