package com.impressico.moviesapp.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.impressico.recipesapp.R

object DataBindingBinders {

    @JvmStatic
    @BindingAdapter("moviePoster")
    fun ImageView.loadImage(moviePoster:String){
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(this.context)
            .applyDefaultRequestOptions(requestOptions).
                load(moviePoster).error(R.drawable.error_image).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
    }

}