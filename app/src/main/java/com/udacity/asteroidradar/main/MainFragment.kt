package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.network.AsteroidApiFilter
import timber.log.Timber

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

        binding.asteroidRecycler.adapter = AsteroidRecyclerAdapter(AsteroidRecyclerAdapter.OnClickListener{
            viewModel.displayAsteroidDetails(it)
        })
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        })

        viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
            for(asteroid in it){
                Log.d("MainFragment",asteroid.closeApproachDate)
            }
        })
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when(item.itemId){
                R.id.show_all_menu -> AsteroidApiFilter.SHOW_ALL
                R.id.show_rent_menu -> AsteroidApiFilter.SHOW_TODAY
                else-> AsteroidApiFilter.SHOW_SAVED
            }
        )
        return true
    }
}
