package com.impressico.moviesapp.presentation.compose.screen

sealed class MovieAppScreen(val route:String){

    object MainScreen:MovieAppScreen("main_screen")
    object DetailsScreen:MovieAppScreen("details_screen")
    object TVShowDetailsScreen:MovieAppScreen("tvshow_screen")
    fun withArgs(vararg args:Int):String{

        return buildString {
            append(route)
            args.forEach {
                arg->append("/$arg")
            }

        }
    }
    fun withArgs( args: Int, isMovie: Boolean): String {
        return buildString {
            append(route)
            append("/$args")
            append("/$isMovie")
        }
    }
}