package com.dowy.android.ui.movieDetails

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.dowy.android.databinding.FragmentMovieDetailsBinding
import com.dowy.android.model.movie.Movie
import com.dowy.android.model.movie.MovieCast
import com.dowy.android.model.movie.MovieReview
import com.dowy.android.model.movie.MovieTrailer
import com.dowy.android.utils.TEXT_PLAIN
import com.dowy.android.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movie: Movie
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var trailers: List<MovieTrailer>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val arguments = MovieDetailsFragmentArgs.fromBundle(requireArguments())
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
        val castAdapter = CastAdapter(CastClickListener {
            openCastMember(it as MovieCast)
        })

        // Trailer Adapter
        binding.recyclerTrailer.hasFixedSize()
        binding.recyclerTrailer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val trailerAdapter = TrailerAdapter(TrailerClickListener {
            openTrailer(it as MovieTrailer)
        })

        Glide.with(this).load(movie.fullBackDropPath).error(R.drawable.no_image).into(binding.movieCover)
        Timber.d(movie.fullBackDropPath)
        Glide.with(this).load(movie.fullPosterPath).error(R.drawable.no_image_portrait1).into(binding.moviePoster)
        binding.movieTitleText.text = movie.title
        binding.languageText.text = movie.original_language
        binding.releaseDateText.text = movie.release_date
        binding.ratingText.text = movie.vote_average.toString()
        binding.overviewText.text = movie.overview

        // Reviews Adapter
        binding.recyclerReview.hasFixedSize()
        binding.recyclerReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val reviewAdapter =
            ReviewAdapter(ReviewClickListener {
                openReview(it as MovieReview)
            })

        // Set Chips
        viewModel.listOfGenres.observe(viewLifecycleOwner, { listOfGenres ->
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
        binding.recyclerReview.adapter = reviewAdapter

        viewModel.getMovieCast(movie.id).observe(viewLifecycleOwner, {
            it?.let { castMembers ->
                if(castMembers.isNotEmpty()){
                    castAdapter.submitMovieCastList(castMembers)
                }else{
                    binding.textCast.visibility = View.GONE
                }

            }
        })

        viewModel.getMovieTrailers(movie.id).observe(viewLifecycleOwner, {
            it?.let { listOfTrailers ->
                if (listOfTrailers.isNotEmpty()) {
                    trailerAdapter.submitMovieTrailers(listOfTrailers)
                } else {
                    binding.movieTrailerTitle.visibility = View.GONE
                }
                trailers = listOfTrailers
            }
        })

        viewModel.getMovieReview(movie.id).observe(viewLifecycleOwner, {
            it?.let { reviews ->
                if (reviews.isNotEmpty()) {
                    reviewAdapter.submitMovieReviewList(reviews)
                } else {
                    binding.textView1sds3.visibility = View.GONE
                }
            }
        })

    }

    private fun openCastMember(movieCast: MovieCast) {
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionDetailsFragmentToPersonFragment(
                movieCast.id,
                movieCast.name
            )
        )
    }

    private fun openTrailer(it: MovieTrailer) {
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

    private fun openReview(review: MovieReview) {
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
        val intentExtra: String = if (trailers.isNotEmpty()) {
            val firstTrailer = "https://youtu.be/${trailers[0].key}"
            "Check out: $firstTrailer"
        } else {
            "I recommend: **${movie.title}**\n\n${movie.overview}"
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

    private fun setupActionBar(movie: Movie) {
        ((activity as AppCompatActivity).supportActionBar)?.title = movie.title
        setHasOptionsMenu(true)
    }

}