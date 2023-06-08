package com.impressico.moviesapp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.impressico.moviesapp.data.remote.model.PopularMovieItem
import com.impressico.recipesapp.R
import com.impressico.recipesapp.databinding.MoviesItemBinding

class PopularMovieListAdapter(private val movieItemClick: (Int) -> Unit) : RecyclerView.Adapter<PopularMovieListAdapter.MovieViewHolder>() {


    private var popularMovies: List<PopularMovieItem> =
        emptyList<PopularMovieItem>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val mBinding: MoviesItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movies_item, parent, false
        )
        return MovieViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(popularMovies[position])
            holder.movieItemBinding.itemParentLayout.setOnClickListener{
                movieItemClick.invoke(popularMovies[position].id)
            }
    }

    fun updatePopularListItems(popularListItems: List<PopularMovieItem>) {
        this.popularMovies = popularListItems
        notifyDataSetChanged()
    }

    class MovieViewHolder( val movieItemBinding: MoviesItemBinding) :
        ViewHolder(movieItemBinding.root) {
        init {
            var mItemBinding:MoviesItemBinding=movieItemBinding
        }
        fun bindData(popularMovie: PopularMovieItem) {
            movieItemBinding.movieItem=popularMovie
            movieItemBinding.moviePoster=popularMovie.poster_path

        }
    }
}