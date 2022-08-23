package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.databinding.ItemListAsteroidBinding

class AsteroidAdapter (val onClickListener: OnClickListener) : ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>
    (AsteroidUtilDiffCallBack()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding :ItemListAsteroidBinding= ItemListAsteroidBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position)!!,onClickListener)
        val asteroid = getItem(position)
        holder.also {
            it.bind(asteroid, onClickListener)
        }
    }


    class AsteroidViewHolder(val binding:ItemListAsteroidBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(asteroid: Asteroid, onClickListener: OnClickListener) {
              binding.asteroid = asteroid
           binding.listener=onClickListener
            binding.executePendingBindings()
        }

        fun from_Astro(parent: ViewGroup): AsteroidAdapter.AsteroidViewHolder {
            val layoutInflate= LayoutInflater.from(parent.context)
            val binding=ItemListAsteroidBinding.inflate(layoutInflate,parent,false)
            return AsteroidAdapter.AsteroidViewHolder(binding)

        }


        }
    }
    class AsteroidUtilDiffCallBack:DiffUtil.ItemCallback<Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id

        }



    }


    class OnClickListener(val clickListener: (asteroid: Asteroid) ->Unit) {
        fun onclick(asteroid: Asteroid) = clickListener(asteroid)
    }







