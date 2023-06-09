package com.impressico.moviesapp.presentation.popularmovies


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.recipesapp.R
import com.impressico.recipesapp.databinding.FragmentMovieDetailedItemBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailedItem.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieDetailedItem : Fragment() {

    // TODO: Rename and change types of parameters

    private lateinit var mBinding:FragmentMovieDetailedItemBinding
    private  var movieId:Int=0
    private val args:MovieDetailedItemArgs by navArgs()
    private  val TAG = "MovieDetailedItem"
    private val viewModel:PopularMovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId=args.movieId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieDetailedItemBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetails(movieId)
        viewLifecycleOwner.lifecycleScope.launch(){
            repeatOnLifecycle(state = Lifecycle.State.STARTED){
                viewModel.popularMovieItem.collect{movie->
                    when(movie){
                        is UIState.Error -> {
                            Log.e(TAG, "onViewCreated: Error ${movie.errorMsg}")
                        }
                        is UIState.Exception -> {
                            Log.e(TAG, "onViewCreated: ${movie.message}")
                        }
                        UIState.Ideal -> {}
                        UIState.Loading -> {}
                        UIState.NoInternet ->{}
                        is UIState.SUCCESS -> {
                            try {
                                val movieData: Movie= movie.data as Movie
                                mBinding.movieItem=movieData
                                bindData(movieData)
                            }
                            catch (exception:Exception){
                                Log.e(TAG, "onViewCreated: ${exception.toString()}")
                            }

                            Log.d(TAG, "onViewCreated: Success ${movie.data.toString()}")
                        }
                    }

                }
            }
        }
    }

    private fun bindData(movie: Movie) {
        //setBackgroundPathImg(movie)
        val backdrop= NetworkConstants.BACKGROUND_BASE_URL+movie.backdrop_path
        Log.d(TAG, "bindData: $backdrop")
        val posterURL=NetworkConstants.POSTER_BASE_URL+movie.poster_path
        loadImageFromUrl(posterURL,mBinding.moviePoster)
        loadImageFromUrl(backdrop,mBinding.movieBackdrop)
        mBinding.movieRating.rating=movie.vote_average.toFloat()/2
    }
    private fun loadImageFromUrl(url:String, view:ImageView){
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions).
            load(url).error(R.drawable.error_image)
            .centerCrop()
            .into(view)

    }
}