package com.doiliomatsinhe.mymovies.ui.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.adapter.SeriesAdapter
import com.doiliomatsinhe.mymovies.adapter.SeriesClickListener
import com.doiliomatsinhe.mymovies.databinding.FragmentTvSeriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvSeriesFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesBinding
    private val viewModel: TvSeriesViewModel by viewModels()
    private lateinit var adapter: SeriesAdapter

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
        adapter = SeriesAdapter(SeriesClickListener {
            Toast.makeText(activity, "${it.name} clicked!", Toast.LENGTH_SHORT).show()
        })

        binding.seriesList.hasFixedSize()
        binding.seriesList.layoutManager =
            GridLayoutManager(activity, resources.getInteger(R.integer.span_count))
        binding.seriesList.adapter = adapter
    }
}