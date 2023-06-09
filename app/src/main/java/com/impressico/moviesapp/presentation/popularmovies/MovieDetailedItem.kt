package com.impressico.moviesapp.presentation.popularmovies


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
import com.bumptech.glide.request.RequestOptions
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.data.remote.model.PopularMovieItem
import com.impressico.moviesapp.data.remote.model.PopularTVItem
import com.impressico.moviesapp.data.remote.model.PopularTVShow
import com.impressico.moviesapp.domain.model.PopularListDto
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel
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
    private val tvShowViewModel:PopularTVShowViewModel by viewModels()
    private  var isMovieDetail:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId=args.movieId
        isMovieDetail=args.detailType
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
        if(isMovieDetail) {
            viewModel.getMovieDetails(movieId)
            collectMovieData()
        }
        else {
            tvShowViewModel.getTvShowDetail(movieId)
            Log.d(TAG, "onViewCreated: TVShow id $movieId")
            collectTVShowData()
        }

    }

    private fun collectTVShowData() {
        viewLifecycleOwner.lifecycleScope.launch(){
            repeatOnLifecycle(state = Lifecycle.State.STARTED){
                tvShowViewModel.tvShowDetail.collect{tvShow->
                    when(tvShow){
                        is UIState.Error -> {
                            Log.e(TAG, "onViewCreated: Error ${tvShow.errorMsg}")
                        }
                        is UIState.Exception -> {
                            Log.e(TAG, "onViewCreated: ${tvShow.message}")
                        }
                        UIState.Ideal -> {}
                        UIState.Loading -> {}
                        UIState.NoInternet ->{}
                        is UIState.SUCCESS -> {
                            try {
                                val tvShowData: PopularTVItem= tvShow.data as PopularTVItem
                                //mBinding.movieItem=movieData
                                bindData(tvShowData.toPopularListDto())
                            }
                            catch (exception:Exception){
                                Log.e(TAG, "onViewCreated: ${exception.toString()}")
                            }

                            Log.d(TAG, "onViewCreated: Success ${tvShow.data.toString()}")
                        }
                    }

                }
            }
        }
    }

    private fun collectMovieData(){
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
                                //mBinding.movieItem=movieData
                                bindData(movieData.toPopularListDto())
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
    private fun bindData(movie: PopularListDto) {
        //setBackgroundPathImg(movie)
        val backdrop= NetworkConstants.BACKGROUND_BASE_URL+movie.backdrop_path
        Log.d(TAG, "bindData: $backdrop")
        val posterURL=NetworkConstants.POSTER_BASE_URL+movie.poster_path
        loadImageFromUrl(posterURL,mBinding.moviePoster)
        loadImageFromUrl(backdrop,mBinding.movieBackdrop)
        mBinding.movieRating.rating=movie.vote_average.toFloat()/2
        if(movie.overview.isNullOrBlank()){
            mBinding.movieOverview.text="No OverView Available"
        }
        else
            mBinding.movieOverview.text=movie.overview
        Log.d(TAG, "bindData: ${movie.overview}")
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