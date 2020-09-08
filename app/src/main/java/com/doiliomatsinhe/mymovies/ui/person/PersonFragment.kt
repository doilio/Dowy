package com.doiliomatsinhe.mymovies.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.adapter.person.PersonMovieClickListener
import com.doiliomatsinhe.mymovies.adapter.person.PersonMoviesAdapter
import com.doiliomatsinhe.mymovies.adapter.person.PersonSeriesAdapter
import com.doiliomatsinhe.mymovies.adapter.person.PersonSeriesClickListener
import com.doiliomatsinhe.mymovies.databinding.FragmentPersonBinding
import com.doiliomatsinhe.mymovies.model.movie.Movie
import com.doiliomatsinhe.mymovies.model.person.Person
import com.doiliomatsinhe.mymovies.model.tv.TvSeries
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var arguments: PersonFragmentArgs
    private lateinit var adapterMovies: PersonMoviesAdapter
    private lateinit var adapterSeries: PersonSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        arguments = PersonFragmentArgs.fromBundle(requireArguments())
        setupActionBar(arguments.name)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        viewModel.getPerson(arguments.personId).observe(viewLifecycleOwner, Observer {
            it?.let { person ->
                populateUI(person)
            }
        })

        viewModel.getPersonMovieList(arguments.personId).observe(viewLifecycleOwner, Observer {
            it?.let { listOfMovies ->
                if (listOfMovies.isNotEmpty()) {
                    adapterMovies.submitList(listOfMovies)
                    binding.titleMoviesCastIn.visibility = View.VISIBLE
                } else {
                    Timber.d("Empty List")
                }
            }
        })

        viewModel.getPersonSeriesList(arguments.personId).observe(viewLifecycleOwner, Observer {
            it?.let { listOfSeries ->
                if (listOfSeries.isNotEmpty()) {
                    adapterSeries.submitList(listOfSeries)
                    binding.titleSeriesCastIn.visibility = View.VISIBLE
                } else {
                    Timber.d("Empty List")
                }
            }
        })

    }

    private fun initComponents() {
        binding.lifecycleOwner = this

        adapterMovies = PersonMoviesAdapter(PersonMovieClickListener {
            val movie = Movie(
                it.popularity,
                it.vote_count,
                it.video,
                it.poster_path,
                it.id,
                it.adult,
                it.backdrop_path,
                it.original_language,
                it.original_title,
                it.genre_ids,
                it.title,
                it.vote_average,
                it.overview,
                it.release_date
            )
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToDetailsFragment(
                    movie
                )
            )
        })

        binding.recyclerMoviesCastIn.hasFixedSize()
        binding.recyclerMoviesCastIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMoviesCastIn.adapter = adapterMovies

        adapterSeries = PersonSeriesAdapter(PersonSeriesClickListener {
            val tvSeries = TvSeries(
                it.original_name, it.genre_ids, it.name, it.popularity,
                it.origin_country, it.vote_count, it.first_air_date, it.backdrop_path,
                it.original_language, it.id, it.vote_average, it.overview, it.poster_path
            )
            findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToTvSeriesDetailsFragment(tvSeries))
        })
        binding.recyclerSeriesCastIn.hasFixedSize()
        binding.recyclerSeriesCastIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerSeriesCastIn.adapter = adapterSeries
    }

    private fun populateUI(person: Person) {
        Glide.with(this).load(person.fullProfilePath).into(binding.actorImage)
        binding.textName.text = person.name
        binding.textKnownFor.text = person.known_for_department
        binding.textBirthdate.text = person.birthday
        binding.textPlaceOfBirth.text = person.place_of_birth
        binding.textPopularity.text = person.popularity.toString()

        // Biography
        if (person.biography.isNotEmpty()) {
            binding.cardBiography.visibility = View.VISIBLE
            binding.textBiography.text = person.biography
        }
        // IMDB and Web Links
        setButtonVisibility(person)

    }

    private fun setButtonVisibility(person: Person) {
        if (person.imdb_id == null) {
            binding.imdbLogo.visibility = View.GONE
        } else {
            binding.imdbLogo.visibility = View.VISIBLE
        }

        if (person.homepage == null) {
            binding.websiteLogo.visibility = View.GONE
        } else {
            binding.websiteLogo.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar(name: String) {
        ((activity as AppCompatActivity).supportActionBar)?.title = name
    }
}