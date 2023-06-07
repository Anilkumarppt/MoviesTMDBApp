package com.impressico.moviesapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.impressico.moviesapp.presentation.states.UIState

import com.impressico.recipesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding
    private val viewModel:PopularMovieViewModel by viewModels()
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        viewModel.getPopularMovies()
        mBinding.btnFetch.setOnClickListener{
            viewModel.getPopularMovies()
        }
        lifecycleScope.launch(){
        repeatOnLifecycle(state = Lifecycle.State.STARTED){
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
                        Log.d(TAG, "onCreate: Success")}
                }
            }
        }    
        }
        
    }
}