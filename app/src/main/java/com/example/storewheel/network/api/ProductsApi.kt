package com.example.storewheel.network.api
import com.example.storewheel.data.models.ProductsResponse
import com.example.storewheel.network.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {
    @GET(Constants.PRODUCTS_URL)
    suspend fun getProducts(): ProductsResponse
    @GET(Constants.SEARCH_PRODUCTS_URL)
    suspend fun searchProducts(@Query("q") query: String): ProductsResponse
}