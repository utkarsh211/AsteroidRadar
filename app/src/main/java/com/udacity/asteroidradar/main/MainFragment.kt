package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {
            Picasso.get()
                .load(it.url)
                .into(binding.activityMainImageOfTheDay)
        })
//
//        viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
//            asteroidsList ->
//            for(element in asteroidsList){
//                Log.d("MainFragment", element.codename)
//            }
//        })
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}