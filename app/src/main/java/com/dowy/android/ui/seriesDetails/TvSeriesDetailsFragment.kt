package com.dowy.android.ui.seriesDetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
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
import com.dowy.android.utils.Utils
import com.dowy.android.utils.Utils.openReview
import com.dowy.android.utils.Utils.openTrailer
import com.dowy.android.utils.Utils.shareDetails
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvSeriesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesDetailsBinding
    private lateinit var tvSeries: TvSeries
    private val viewModel: TvSeriesDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvSeriesDetailsBinding.inflate(inflater, container, false)
        tvSeries = TvSeriesDetailsFragmentArgs.fromBundle(requireArguments()).TvSeries
        setupActionBar(tvSeries.name)
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
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerCast.layoutManager = layoutManager
        val castAdapter = CastAdapter(CastClickListener {
            (it as TvCast).let { tvCast ->
                openCastMember(tvCast.id, tvCast.name)
            }
        })

        // Trailer Adapter
        binding.recyclerTrailer.hasFixedSize()
        binding.recyclerTrailer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val trailerAdapter = TrailerAdapter(TrailerClickListener {
            openTrailer((it as TvTrailer).youtubeLink, requireContext())
        })

        binding.tvCover?.let {
            Glide.with(this).load(tvSeries.fullBackDropPath).error(R.drawable.no_image).into(it)
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
                openReview((it as TvReview).url, requireContext())
            })

        viewModel.getTvGenre().observe(viewLifecycleOwner, { listOfGenres ->
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

        viewModel.getTvCast().observe(viewLifecycleOwner, {
            it?.let { castMembers ->
                if (castMembers.isNotEmpty()) {
                    castAdapter.submitSeriesCastList(castMembers)
                } else {
                    binding.castError.visibility = View.VISIBLE
                    binding.recyclerCast.visibility = View.GONE
                }
            }
        })

        viewModel.getTvTrailers().observe(viewLifecycleOwner, {
            it?.let { listOfTrailers ->
                if (listOfTrailers.isNotEmpty()) {
                    trailerAdapter.submitSeriesTrailers(listOfTrailers)
                } else {
                    binding.trailerError.visibility = View.VISIBLE
                    binding.recyclerTrailer.visibility = View.GONE
                }
            }
        })

        viewModel.getTvReview().observe(viewLifecycleOwner, {
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

    private fun openCastMember(id: Int, name: String) {
        findNavController().navigate(
            TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentToPersonFragment(
                id,
                name
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_share -> {
                val message =
                    "*${tvSeries.name}*\n${tvSeries.overview}\n\n${getString(R.string.more_details)}\n${
                        getString(R.string.playstore_link)
                    }"
                shareDetails(message, requireContext())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar(title: String) {
        ((activity as AppCompatActivity).supportActionBar)?.title = title
        setHasOptionsMenu(true)
    }

}