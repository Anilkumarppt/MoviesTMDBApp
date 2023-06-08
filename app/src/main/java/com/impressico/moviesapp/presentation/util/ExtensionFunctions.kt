package com.impressico.moviesapp.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.impressico.recipesapp.R

fun ImageView.loadPosterImage(url:String){
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.error_image)
        .error(R.drawable.error_image)
        .into(this)
}