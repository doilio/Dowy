package com.dowy.android.ui.seriesDetails

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dowy.android.R
import com.dowy.android.adapter.cast.CastAdapter
import com.dowy.android.adapter.cast.CastClickListener
import com.dowy.android.adapter.review.ReviewAdapter
import com.dowy.android.adapter.review.ReviewClickListener
import com.dowy.android.adapter.trailer.TrailerAdapter
import com.dowy.android.adapter.trailer.TrailerClickListener
import com.dowy.android.databinding.FragmentTvSeriesDetailsBinding
import com.dowy.android.model.tv.TvCast
import com.dowy.android.model.tv.TvReview
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.model.tv.TvTrailer
import com.dowy.android.utils.TEXT_PLAIN
import com.dowy.android.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TvSeriesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesDetailsBinding
    private lateinit var tvSeries: TvSeries
    private val viewModel: TvSeriesDetailsViewModel by viewModels()
    private lateinit var trailers: List<TvTrailer>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvSeriesDetailsBinding.inflate(inflater, container, false)

        val arguments = TvSeriesDetailsFragmentArgs.fromBundle(requireArguments())
        tvSeries = arguments.TvSeries
        setupActionBar(tvSeries)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        populateSeriessUI(tvSeries)
    }

    private fun populateSeriessUI(tvSeries: TvSeries) {
        // Cast Adapter
        binding.recyclerCast.hasFixedSize()
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.recyclerCast.layoutManager = layoutManager
        val castAdapter = CastAdapter(CastClickListener {
            openCastMember(it as TvCast)
        })

        // Trailer Adapter
        binding.recyclerTrailer.hasFixedSize()
        binding.recyclerTrailer.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        val trailerAdapter = TrailerAdapter(TrailerClickListener {
            openTrailer(it as TvTrailer)
        })

        binding.tvCover?.let {
            Glide.with(this).load(tvSeries.fullBackDropPath)
                .error(R.drawable.no_image).into(it)
        }
        Glide.with(this).load(tvSeries.fullPosterPath).error(R.drawable.no_image_portrait1)
            .into(binding.tvPoster)
        binding.tvTitleText.text = tvSeries.name
        binding.languageText.text = tvSeries.original_language
        binding.releaseDateText.text = tvSeries.first_air_date
        binding.ratingText.text = tvSeries.vote_average.toString()

        if (tvSeries.overview.isNotEmpty()) {
            binding.overviewText.text = tvSeries.overview
        } else {
            binding.overviewText.visibility = View.GONE
            binding.overviewError.visibility = View.VISIBLE
        }

        // Reviews Adapter
        binding.recyclerReview.hasFixedSize()
        binding.recyclerReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val reviewAdapter =
            ReviewAdapter(ReviewClickListener {
                openReview(it as TvReview)
            })

        // Set Chips
        viewModel.listOfGenres.observe(viewLifecycleOwner, { listOfGenres ->
            listOfGenres?.let {

                for (elem in tvSeries.genre_ids) {
                    val filteredListOfGenres = listOfGenres.filter { it.id == elem }
                    for (item in filteredListOfGenres) {
                        val chip = Chip(requireContext())
                        chip.setChipBackgroundColorResource(android.R.color.transparent)
                        chip.chipStrokeColor = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.darker_gray
                            )
                        )
                        chip.chipStrokeWidth = Utils.dptoPx(requireContext(), 1)

                        chip.text = item.name
                        binding.chipGroup.addView(chip)
                    }
                }
            }
        })

        binding.recyclerCast.adapter = castAdapter
        binding.recyclerTrailer.adapter = trailerAdapter
        binding.recyclerReview.adapter = reviewAdapter

        viewModel.getTvCast(tvSeries.id).observe(viewLifecycleOwner, {
            it?.let { castMembers ->
                if (castMembers.isNotEmpty()) {
                    castAdapter.submitSeriesCastList(castMembers)
                } else {
                    binding.castError.visibility = View.VISIBLE
                    binding.recyclerCast.visibility = View.GONE
                }

            }
        })

        viewModel.getTvTrailers(tvSeries.id).observe(viewLifecycleOwner, {
            it?.let { listOfTrailers ->
                if (listOfTrailers.isNotEmpty()) {
                    trailerAdapter.submitSeriesTrailers(listOfTrailers)
                } else {
                    binding.trailerError.visibility = View.VISIBLE
                    binding.recyclerTrailer.visibility = View.GONE
                }
                trailers = listOfTrailers
            }
        })

        viewModel.getTvReview(tvSeries.id).observe(viewLifecycleOwner, {
            it?.let { reviews ->
                if (reviews.isNotEmpty()) {
                    reviewAdapter.submitSeriesReviewList(reviews)
                } else {
                    binding.reviewError.visibility = View.VISIBLE
                    binding.recyclerReview.visibility = View.GONE
                }
            }
        })
    }

    private fun openCastMember(tvCast: TvCast) {
        findNavController().navigate(
            TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentToPersonFragment(
                tvCast.id,
                tvCast.name
            )
        )
    }

    private fun openTrailer(it: TvTrailer) {
        val i = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse("https://www.youtube.com/watch?v=${it.key}")
            Timber.d("https://www.youtube.com/watch?v=${it.key}")
        }
        if (Utils.isAppInstalled(requireContext(), getString(R.string.youtube_app_name))) {
            i.`package` = getString(R.string.youtube_app_name)
        }
        startActivity(i)
    }

    private fun openReview(review: TvReview) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(review.url))
            .addCategory(Intent.CATEGORY_BROWSABLE)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_share -> {
                shareDetails()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareDetails() {
        // Decide what to share
        val intentExtra = if (trailers.isNotEmpty()) {
            val firstTrailer = "https://youtu.be/${trailers[0].key}"
            "Check out: $firstTrailer"
        } else {
            "I recommend: **${tvSeries.name}**\n\n${tvSeries.overview}"
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = TEXT_PLAIN
            putExtra(Intent.EXTRA_TEXT, intentExtra)
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
        } else {
            Toast.makeText(activity, getString(R.string.sharing_failed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupActionBar(tvSeries: TvSeries) {
        ((activity as AppCompatActivity).supportActionBar)?.title = tvSeries.name
        setHasOptionsMenu(true)
    }


}