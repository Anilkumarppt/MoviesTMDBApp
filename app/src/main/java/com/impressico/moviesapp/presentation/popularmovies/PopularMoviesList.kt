package com.impressico.moviesapp.presentation.popularmovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.impressico.moviesapp.data.remote.model.PopularMovie
import com.impressico.moviesapp.data.remote.model.PopularMovieItem
import com.impressico.moviesapp.presentation.PopularMovieViewModel
import com.impressico.moviesapp.presentation.adapters.PopularMovieListAdapter
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.recipesapp.R
import com.impressico.recipesapp.databinding.FragmentPopularMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [PopularMoviesList.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PopularMoviesList : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var mBinding:FragmentPopularMoviesListBinding
    private val viewModel:PopularMovieViewModel by viewModels()
    private  val TAG = "PopularMoviesList"
    private  var mAdapter:PopularMovieListAdapter = PopularMovieListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentPopularMoviesListBinding.inflate(inflater, container, false)
        mBinding.popularMoviesList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        mBinding.popularMoviesList.adapter=mAdapter

        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies()
        viewLifecycleOwner.lifecycleScope.launch(){
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.popularMovieList.collect{
                    viewModel.popularMovieList.collect{resultUIState->
                        when(resultUIState){
                            is UIState.Error -> {
                                Log.e(TAG, "onCreate: error", )
                            }
                            is UIState.Exception -> {
                                Log.e(TAG, "onCreate: Exception", )}
                            UIState.Ideal -> {}
                            UIState.Loading -> {}
                            UIState.NoInternet -> {}
                            is UIState.SUCCESS -> {
                                try {
                                    val result=resultUIState.data as List<PopularMovieItem>
                                    bindData(result)
                                } catch (e: Exception) {
                                    Log.e(TAG, "onViewCreated: ${e.message}", )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindData(popularMovies: List<PopularMovieItem>) {
            mAdapter.updatePopularListItems(popularMovies)
    }
}