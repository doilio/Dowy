package com.doiliomatsinhe.mymovies.ui.details

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.adapter.CastAdapter
import com.doiliomatsinhe.mymovies.adapter.TrailerAdapter
import com.doiliomatsinhe.mymovies.databinding.FragmentDetailsBinding
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var movie: Movie
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val arguments = DetailsFragmentArgs.fromBundle(requireArguments())
        movie = arguments.Movie
        setupActionBar(movie)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        populateMoviesUI(movie)
    }

    private fun populateMoviesUI(movie: Movie) {

        // Cast Adapter
        binding.recyclerCast.hasFixedSize()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCast.layoutManager = layoutManager
        val castAdapter = CastAdapter()

        // Trailer Adapter
        binding.recyclerTrailer.hasFixedSize()
        binding.recyclerTrailer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val trailerAdapter = TrailerAdapter()

        Glide.with(this).load(movie.fullBackDropPath).into(binding.movieCover)
        Glide.with(this).load(movie.fullPosterPath).into(binding.moviePoster)
        binding.movieTitleText.text = movie.title
        binding.languageText.text = movie.original_language
        binding.releaseDateText.text = movie.release_date
        binding.ratingText.text = movie.vote_average.toString()
        binding.overviewText.text = movie.overview

        // Set Chips
        viewModel.listOfGenres.observe(viewLifecycleOwner, Observer { listOfGenres ->
            listOfGenres?.let {

                for (elem in movie.genre_ids) {
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

        viewModel.getMovieCast(movie.id).observe(viewLifecycleOwner, Observer {
            it?.let { castMembers ->
                castAdapter.submitList(castMembers)
                Timber.d("Cast Members: $castMembers")
            }
        })

        viewModel.getMovieTrailers(movie.id).observe(viewLifecycleOwner, Observer {
            it?.let { listOfTrailers ->
                trailerAdapter.submitList(listOfTrailers)
            }
        })

    }

    private fun setupActionBar(movie: Movie) {
        ((activity as AppCompatActivity).supportActionBar)?.title = movie.title
    }

}