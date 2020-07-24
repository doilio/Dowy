package com.doiliomatsinhe.mymovies.ui.movies

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.adapter.MovieAdapter
import com.doiliomatsinhe.mymovies.adapter.MovieClickListener
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.databinding.FragmentMoviesBinding
import com.doiliomatsinhe.mymovies.utils.CATEGORY_KEY
import com.doiliomatsinhe.mymovies.utils.DEFAULT_CATEGORY
import com.doiliomatsinhe.mymovies.utils.DEFAULT_LANGUAGE
import com.doiliomatsinhe.mymovies.utils.LANGUAGE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var sharedPreference: SharedPreferences

    @Inject
    lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        lifecycleScope.launch {
            viewModel.getMoviesList().collectLatest {
                adapter.submitData(it)
            }
        }
//        viewModel.listOfMovies.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                if (it.isNotEmpty()) {
//                    adapter.submitList(it)
//                    binding.movieProgress.visibility = View.GONE
//                    binding.movieList.visibility = View.VISIBLE
//                    //binding.moviesError.visibility = View.GONE
//                } else {
//                    binding.movieProgress.visibility = View.VISIBLE
//                    //binding.moviesError.visibility = View.VISIBLE
//                }
//
//            }
//        })

    }

    private fun initComponents() {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val category = sharedPreference.getString(CATEGORY_KEY, DEFAULT_CATEGORY)
        val language = sharedPreference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        val factory = MoviesViewModelFactory(repository, category, language)
        viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)

        adapter = MovieAdapter(MovieClickListener {
            Toast.makeText(activity, "${it.title} clicked!", Toast.LENGTH_SHORT).show()
        })

        binding.movieList.adapter = adapter
        binding.movieList.hasFixedSize()
        binding.movieList.layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.span_count))


    }
}