package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDao
        val viewModelFactory=MainViewModelFactory(dataSource,application)
        viewModel=ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adaptor =AsteroidAdapter(OnClickListener {it->
            Toast.makeText(context,"on click gone",Toast.LENGTH_LONG).show()
            viewModel.asteroidOnClick(it.id)
        })
        binding.asteroidRecycler.adapter=adaptor


        viewModel.navigate.observe(viewLifecycleOwner, Observer {aster ->
            aster?.let {
                this.findNavController().navigate(R.id.detailFragment)
                viewModel.asteroidOnClickNavigate()
            }
        })
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
