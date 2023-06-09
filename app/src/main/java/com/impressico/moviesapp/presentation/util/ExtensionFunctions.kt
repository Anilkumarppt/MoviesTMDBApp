package com.impressico.moviesapp.presentation.util

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.impressico.recipesapp.R

fun ImageView.loadPosterImage(url:String){
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.error_image)
        .error(R.drawable.error_image)
        .into(this)
}

fun customSnackBar(view: View, message:String, isError: Boolean){
    val snackbar = Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_LONG
    )
    snackbar.setActionTextColor(view.resources.getColor(android.R.color.white))

    if (isError){
        snackbar.setBackgroundTint(Color.RED)
    }
    else
        snackbar.setBackgroundTint(Color.GREEN)

    snackbar.show()
}