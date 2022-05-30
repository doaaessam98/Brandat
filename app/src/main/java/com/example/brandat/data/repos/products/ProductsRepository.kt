package com.example.brandat.data.repos.products

import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ProductsRepository @Inject constructor(
    var localDataSource: ILocalDataSource,
     var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository {



}