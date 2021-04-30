package com.dowy.android.ui.movieDetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.dowy.android.utils.Utils
import com.dowy.android.utils.Utils.openReview
import com.dowy.android.utils.Utils.openTrailer
import com.dowy.android.utils.Utils.shareDetails
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movie: Movie
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).Movie
        setupActionBar(movie.title)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        initAdapters()
        populateMoviesUI(movie)
    }

    private fun populateMoviesUI(movie: Movie) {

        binding.movie = movie
        binding.executePendingBindings()

        viewModel.getMovieGenre().observe(viewLifecycleOwner, { listOfGenres ->
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

        viewModel.getMovieCast().observe(viewLifecycleOwner, {
            it?.let { castMembers ->
                if (castMembers.isNotEmpty()) {
                    castAdapter.submitMovieCastList(castMembers)
                } else {
                    binding.castError.visibility = View.VISIBLE
                    binding.recyclerCast.visibility = View.GONE
                }
            }
        })

        viewModel.getMovieTrailers().observe(viewLifecycleOwner, {
            it?.let { listOfTrailers ->
                if (listOfTrailers.isNotEmpty()) {
                    trailerAdapter.submitMovieTrailers(listOfTrailers)
                } else {
                    binding.trailerError.visibility = View.VISIBLE
                    binding.recyclerTrailer.visibility = View.GONE
                }
            }
        })

        viewModel.getMovieReview().observe(viewLifecycleOwner, {
            it?.let { reviews ->
                if (reviews.isNotEmpty()) {
                    reviewAdapter.submitMovieReviewList(reviews)
                } else {
                    binding.reviewError.visibility = View.VISIBLE
                    binding.recyclerReview.visibility = View.GONE
                }
            }
        })
    }

    private fun initAdapters() {
        binding.recyclerCast.hasFixedSize()
        binding.recyclerTrailer.hasFixedSize()
        binding.recyclerReview.hasFixedSize()

        binding.recyclerCast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTrailer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        castAdapter = CastAdapter(CastClickListener {
            (it as MovieCast).let { movieCast ->
                openCastMember(movieCast.id, movieCast.name)
            }
        })

        trailerAdapter = TrailerAdapter(TrailerClickListener {
            openTrailer((it as MovieTrailer).youtubeLink, requireContext())
        })

        reviewAdapter =
            ReviewAdapter(ReviewClickListener {
                openReview((it as MovieReview).url, requireContext())
            })

        binding.recyclerCast.adapter = castAdapter
        binding.recyclerTrailer.adapter = trailerAdapter
        binding.recyclerReview.adapter = reviewAdapter
    }

    private fun openCastMember(id: Int, name: String) {
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionDetailsFragmentToPersonFragment(id, name)
        )
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val notifierItem = menu.findItem(R.id.ic_notify)

        notifierItem.setOnMenuItemClickListener {
            if (viewModel.clicked) {
                // TODO: Remove the Movie from the Watch List
                notifierItem.setIcon(R.drawable.ic_baseline_notifications_none_24)
                viewModel.clicked = false
                Toast.makeText(requireContext(), "Removed from watch list", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Add the Movie to Watch List
                notifierItem.setIcon(R.drawable.ic_baseline_notifications_active_24)
                Toast.makeText(requireContext(), "Added to watch list", Toast.LENGTH_SHORT).show()
                viewModel.clicked = true
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_share -> {
                val message =
                    "*${movie.title}*\n${movie.overview}\n\n${getString(R.string.more_details)}\n${
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