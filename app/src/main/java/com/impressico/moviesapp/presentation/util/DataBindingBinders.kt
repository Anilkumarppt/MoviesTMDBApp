package com.impressico.moviesapp.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object DataBindingBinders {

    @BindingAdapter("moviePoster")
    fun ImageView.loadImage(moviePoster:String){
        Glide.with(this.context).load(moviePoster).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }

}