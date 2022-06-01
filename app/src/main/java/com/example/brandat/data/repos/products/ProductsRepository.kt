package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import javax.inject.Inject

//@ActivityRetainedScoped
//@ViewModelScoped
class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
) : IProductsRepository {
    override suspend fun getProductDetails(productId: Long):Response<Product> {
        Log.d("TAG", "getProductDetails: $productId")
        return remoteDataSource.getProductDetails(productId)
    }

}