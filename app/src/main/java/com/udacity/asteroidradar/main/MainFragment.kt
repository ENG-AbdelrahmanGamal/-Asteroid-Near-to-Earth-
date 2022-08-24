package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val TAG = "MainFragment"
    private lateinit var adaptor :AsteroidAdapter
   private lateinit var binding:FragmentMainBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentMainBinding.inflate(inflater)
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


         adaptor = AsteroidAdapter(OnClickListener { it ->
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))

        })
        binding.asteroidRecycler.adapter = adaptor

        viewModel.astroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adaptor.submitList(it)
                Log.d(TAG, ": ${it}")

            }
        })


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.show_all_menu -> {
                adapteWeekAsteroids()
                return true

            }
            R.id.show_rent_menu -> {
                adapteTodayAsteroids()
                return true

            }
            R.id.show_buy_menu -> {
                Toast.makeText(requireContext(),"Soon as you see  will create a List of Asteroids",Toast.LENGTH_LONG).show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun adapteWeekAsteroids() {
        Toast.makeText(requireContext(), "item one ", Toast.LENGTH_LONG).show()
        viewModel.astroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adaptor.submitList(it)
                Log.d(TAG, ": ${it}")

            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun adapteTodayAsteroids() {
        binding.asteroidRecycler.adapter = adaptor

        viewModel.todayAsteroid.observe(viewLifecycleOwner, Observer {
            it?.let {
                adaptor.submitList(it)
                Log.d(TAG, ": ${it}")

            }
        })
    }
}
