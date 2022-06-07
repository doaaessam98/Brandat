package com.example.brandat.data.source.remote

import android.util.Log
import com.example.brandat.models.Brands
import com.example.brandat.models.Customer
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {
    override suspend fun getProductDetails(productId: Long): Response<Product> {
        Log.d("TAG", "getProductDetails: ${networkService.getProductDetails(productId).body()}")
        Log.d("TAG", "getProductDetails id: $productId")

        return networkService.getProductDetails(productId)
    }

    override suspend fun getBrands(): Response<Brands> {
        return networkService.getBrands()
    }

    override suspend fun getAllProductsById(): Response<Products> {
       return networkService.getProductsById()
    }

    override suspend fun getAllProductsByProductType(product_type: String): Response<Products> {
        return networkService.getProductsBySubCategory(product_type)
    }
    override suspend fun getCategories(productId: Long): Response<Products> {
        return networkService.getCategoryByTag(productId)
    }

    //=====================================================
    override suspend fun registerCustomer(customer: Customer): Response<Customer> {
       return networkService.register(customer)
    }





}

