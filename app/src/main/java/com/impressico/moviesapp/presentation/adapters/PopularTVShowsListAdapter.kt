package com.impressico.moviesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.PopularTVShow
import com.impressico.moviesapp.presentation.util.loadPosterImage
import com.impressico.recipesapp.R
import com.impressico.recipesapp.databinding.TvshowItemBinding

class PopularTVShowsListAdapter(private val movieItemClick: (Int) -> Unit) : RecyclerView.Adapter<PopularTVShowsListAdapter.TVShowItemViewHolder>() {


    private var popularTVshowsList: List<PopularTVShow> =
        emptyList<PopularTVShow>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowItemViewHolder {
        val mBinding: TvshowItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.tvshow_item, parent, false
        )
        return TVShowItemViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return popularTVshowsList.size
    }

    override fun onBindViewHolder(holder: TVShowItemViewHolder, position: Int) {
        holder.bindData(popularTVshowsList[position])
            holder.tvshow.itemParentLayout.setOnClickListener{
                movieItemClick.invoke(popularTVshowsList[position].id)
            }
    }

    fun updateTVShowList(popularListItems: List<PopularTVShow>) {
        this.popularTVshowsList = popularListItems
        notifyDataSetChanged()
    }

    class TVShowItemViewHolder(val tvshow: TvshowItemBinding) :
        ViewHolder(tvshow.root) {
        init {
            var mItemBinding:TvshowItemBinding=tvshow
        }
        fun bindData(popularTVshow: PopularTVShow) {

            tvshow.moviePosterImg.loadPosterImage(NetworkConstants.POSTER_BASE_URL+popularTVshow.poster_path)
            tvshow.tvShowTitle.text=popularTVshow.name
            tvshow.releaseDate.text=popularTVshow.first_air_date
            tvshow.voteAverage.text=  (popularTVshow.vote_average/2).toString()
        }
    }
}