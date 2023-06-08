package com.impressico.moviesapp.data.repositoryImpl

import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.PopularRemoteDataSource
import com.impressico.moviesapp.data.remote.model.PopularTVItem
import com.impressico.moviesapp.data.remote.model.PopularTVResult
import com.impressico.moviesapp.domain.repository.PopularTVShowRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularTVShowRepoImpl @Inject constructor(private val remoteDataSource: PopularRemoteDataSource) :PopularTVShowRepo {
    override suspend fun getPopularTVShows(): Flow<NetworkResult<PopularTVResult>> {
        return flow {
            emit(remoteDataSource.getPopularTVShows(1))
        }
    }

    override suspend fun getPopularTVShowDetails(tvshowId: Int): Flow<NetworkResult<PopularTVItem>> {
        return flow {
            emit(remoteDataSource.getPopularTVShowDetails(tvshowId))
        }
    }
}