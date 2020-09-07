package com.doiliomatsinhe.mymovies.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.doiliomatsinhe.mymovies.databinding.FragmentPersonBinding
import com.doiliomatsinhe.mymovies.model.person.Person
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var arguments: PersonFragmentArgs

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

        binding.lifecycleOwner = this

        viewModel.getPerson(arguments.personId).observe(viewLifecycleOwner, Observer {
            it?.let { person ->
                populateUI(person)
            }
        })

    }

    private fun populateUI(person: Person) {
        Glide.with(this).load(person.fullProfilePath).into(binding.actorImage)
        binding.textName.text = person.name
        binding.textKnownFor.text = person.known_for_department
        binding.textBirthdate.text = person.birthday
        binding.textPlaceOfBirth.text = person.place_of_birth
        binding.textPopularity.text = person.popularity.toString()

        // Biography
        binding.textBiography.text = person.biography

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