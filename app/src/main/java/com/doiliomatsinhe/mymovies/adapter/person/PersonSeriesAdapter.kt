package com.doiliomatsinhe.mymovies.adapter.person

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.mymovies.model.person.PersonTvCast


class PersonSeriesAdapter(val clickListener: PersonSeriesClickListener) :
    ListAdapter<PersonTvCast, RecyclerView.ViewHolder>(PersonSeriesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonSeriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PersonSeriesViewHolder).bind(getItem(position), clickListener)
    }

}

class PersonSeriesDiffUtilCallback : DiffUtil.ItemCallback<PersonTvCast>() {
    override fun areItemsTheSame(oldItem: PersonTvCast, newItem: PersonTvCast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonTvCast, newItem: PersonTvCast): Boolean {
        return oldItem == newItem
    }

}

class PersonSeriesClickListener(val clicklistener: (series: PersonTvCast) -> Unit) {
    fun onClick(series: PersonTvCast) = clicklistener(series)
}