package com.dowy.android.ui.person

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowy.android.adapter.person.PersonMovieClickListener
import com.dowy.android.adapter.person.PersonMoviesAdapter
import com.dowy.android.adapter.person.PersonSeriesAdapter
import com.dowy.android.adapter.person.PersonSeriesClickListener
import com.dowy.android.databinding.FragmentPersonBinding
import com.dowy.android.model.movie.Movie
import com.dowy.android.model.person.Person
import com.dowy.android.model.tv.TvSeries
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var adapterMovies: PersonMoviesAdapter
    private lateinit var adapterSeries: PersonSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        val person = PersonFragmentArgs.fromBundle(requireArguments())

        setupActionBar(person.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        initAdapters()

        viewModel.getPerson().observe(viewLifecycleOwner, {
            it?.let { person: Person ->
                binding.person = person

                // IMDB and Web Links
                setButtonVisibility(person.linkIMDB, person.homepage)

                binding.imdbLogo.setOnClickListener { openImdb(person.linkIMDB) }
                binding.websiteLogo.setOnClickListener { openWebsite(person.homepage) }
            }
        })

        viewModel.getPersonMovieList().observe(viewLifecycleOwner, {
            it?.let { listOfMovies ->
                if (listOfMovies.isNotEmpty()) {
                    adapterMovies.submitList(listOfMovies)
                } else {
                    binding.recyclerMoviesCastIn.visibility = View.GONE
                    binding.moviesCastInError.visibility = View.VISIBLE
                }
            }
        })

        viewModel.getPersonSeriesList().observe(viewLifecycleOwner, {
            it?.let { listOfSeries ->
                if (listOfSeries.isNotEmpty()) {
                    adapterSeries.submitList(listOfSeries)
                } else {
                    binding.recyclerSeriesCastIn.visibility = View.GONE
                    binding.tvCastInError.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun initAdapters() {

        binding.recyclerMoviesCastIn.hasFixedSize()
        binding.recyclerSeriesCastIn.hasFixedSize()

        binding.recyclerMoviesCastIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerSeriesCastIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterMovies = PersonMoviesAdapter(PersonMovieClickListener {
            val movie = Movie(
                it.popularity, it.vote_count, it.video, it.poster_path, it.id,
                it.adult, it.backdrop_path, it.original_language, it.original_title,
                it.genre_ids, it.title, it.vote_average, it.overview, it.release_date
            )
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToDetailsFragment(
                    movie
                )
            )
        })

        adapterSeries = PersonSeriesAdapter(PersonSeriesClickListener {
            val tvSeries = TvSeries(
                it.original_name, it.genre_ids, it.name, it.popularity,
                it.origin_country, it.vote_count, it.first_air_date, it.backdrop_path,
                it.original_language, it.id, it.vote_average, it.overview, it.poster_path
            )
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToTvSeriesDetailsFragment(
                    tvSeries
                )
            )
        })

        binding.recyclerMoviesCastIn.adapter = adapterMovies
        binding.recyclerSeriesCastIn.adapter = adapterSeries
    }

    private fun openWebsite(webpage: String?) {
        val websiteIntent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(webpage)
        }
        startActivity(websiteIntent)
    }

    private fun openImdb(linkIMDB: String?) {
        val imdbIntent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(linkIMDB)
        }
        startActivity(imdbIntent)
    }

    private fun setButtonVisibility(linkIMDB: String?, webPage: String?) {
        if (!linkIMDB.isNullOrEmpty()) {
            binding.imdbLogo.visibility = View.VISIBLE
        }
        if (!webPage.isNullOrEmpty()) {
            binding.websiteLogo.visibility = View.VISIBLE
        }

    }

    private fun setupActionBar(name: String) {
        ((activity as AppCompatActivity).supportActionBar)?.title = name

    }
}