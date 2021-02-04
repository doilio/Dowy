package com.dowy.android.ui.person

import android.content.Intent
import android.content.SharedPreferences
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
import com.bumptech.glide.Glide
import com.dowy.android.R
import com.dowy.android.adapter.person.PersonMovieClickListener
import com.dowy.android.adapter.person.PersonMoviesAdapter
import com.dowy.android.adapter.person.PersonSeriesAdapter
import com.dowy.android.adapter.person.PersonSeriesClickListener
import com.dowy.android.databinding.FragmentPersonBinding
import com.dowy.android.model.movie.Movie
import com.dowy.android.model.person.Person
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var arguments: PersonFragmentArgs
    private lateinit var adapterMovies: PersonMoviesAdapter
    private lateinit var adapterSeries: PersonSeriesAdapter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        arguments = PersonFragmentArgs.fromBundle(requireArguments())
        setupActionBar(arguments.name)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)?.let { language ->
            viewModel.getPerson(arguments.personId, language).observe(viewLifecycleOwner, {
                it?.let { person ->
                    populateUI(person)
                }
            })
        }

        sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)?.let { language ->
            viewModel.getPersonMovieList(arguments.personId, language).observe(viewLifecycleOwner, {
                it?.let { listOfMovies ->
                    if (listOfMovies.isNotEmpty()) {
                        adapterMovies.submitList(listOfMovies)
                    } else {
                        binding.recyclerMoviesCastIn.visibility = View.GONE
                        binding.moviesCastInError.visibility = View.VISIBLE
                    }
                }
            })
        }


        sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)?.let { language ->
            viewModel.getPersonSeriesList(arguments.personId, language).observe(viewLifecycleOwner, {
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

    }

    private fun initComponents() {
        binding.lifecycleOwner = this

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
            findNavController().navigate(
                PersonFragmentDirections.actionPersonFragmentToTvSeriesDetailsFragment(
                    tvSeries
                )
            )
        })
        binding.recyclerSeriesCastIn.hasFixedSize()
        binding.recyclerSeriesCastIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerSeriesCastIn.adapter = adapterSeries
    }

    private fun populateUI(person: Person) {
        Glide.with(this).load(person.fullProfilePath).error(R.drawable.no_image_portrait1)
            .into(binding.actorImage)

        person.name.let {
            binding.textName.text = it ?: "-"
        }

        person.known_for_department.let {
            binding.textKnownFor.text = it ?: "-"
        }

        person.birthday.let {
            binding.textBirthdate.text = it ?: "-"
        }

        person.place_of_birth.let {
            binding.textPlaceOfBirth.text = it ?: "-"
        }

        person.popularity.let { popularity ->
            popularity.toString().let {
                binding.textPopularity.text = if (it.isNotEmpty()) it else "-"
            }
        }

        person.biography?.let {
            if (it.isNotEmpty()) {
                binding.textBiography.text = person.biography
            } else {
                binding.textBiography.visibility = View.GONE
                binding.textBiographyError.visibility = View.VISIBLE
            }
        }

        // IMDB and Web Links
        setButtonVisibility(person)

        binding.imdbLogo.setOnClickListener { openImdb(person.imdb_id) }
        binding.websiteLogo.setOnClickListener { openWebsite(person.homepage) }

    }

    private fun openWebsite(webpage: String?) {
        val websiteIntent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse(webpage)
        }
        startActivity(websiteIntent)
    }

    private fun openImdb(imdbId: String?) {
        val imdbIntent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse("https://www.imdb.com/name/$imdbId")
        }
        startActivity(imdbIntent)
    }

    private fun setButtonVisibility(person: Person) {
        if (person.imdb_id.isNullOrEmpty()) {
            binding.imdbLogo.visibility = View.GONE
        } else {
            binding.imdbLogo.visibility = View.VISIBLE
        }

        if (person.homepage.isNullOrEmpty()) {
            binding.websiteLogo.visibility = View.GONE
        } else {
            binding.websiteLogo.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar(name: String) {
        ((activity as AppCompatActivity).supportActionBar)?.title = name
    }
}