package com.impressico.moviesapp.presentation.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.impressico.recipesapp.R


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailedItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailedItem : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detailed_item, container, false)
    }


}