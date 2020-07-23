package com.doiliomatsinhe.mymovies.ui.series

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.adapter.SeriesAdapter
import com.doiliomatsinhe.mymovies.adapter.SeriesClickListener
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.databinding.FragmentTvSeriesBinding
import com.doiliomatsinhe.mymovies.utils.CATEGORY_KEY
import com.doiliomatsinhe.mymovies.utils.DEFAULT_CATEGORY
import com.doiliomatsinhe.mymovies.utils.DEFAULT_LANGUAGE
import com.doiliomatsinhe.mymovies.utils.LANGUAGE_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvSeriesFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesBinding
    private lateinit var viewModel: TvSeriesViewModel
    private lateinit var adapter: SeriesAdapter
    private lateinit var sharedPreference: SharedPreferences

    @Inject
    lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTvSeriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        viewModel.listOfTvSeries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initComponents() {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val category = sharedPreference.getString(CATEGORY_KEY, DEFAULT_CATEGORY)
        val language = sharedPreference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        val factory = TvSeriesViewModelFactory(repository, category, language)
        viewModel = ViewModelProvider(this, factory).get(TvSeriesViewModel::class.java)


        adapter = SeriesAdapter(SeriesClickListener {
            Toast.makeText(activity, "${it.name} clicked!", Toast.LENGTH_SHORT).show()
        })

        binding.seriesList.hasFixedSize()
        binding.seriesList.layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.span_count))
        binding.seriesList.adapter = adapter
    }
}