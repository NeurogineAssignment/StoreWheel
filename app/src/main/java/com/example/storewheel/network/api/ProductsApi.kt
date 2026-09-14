package com.example.storewheel.network.api
import com.example.storewheel.data.models.ProductDetailsResponse
import com.example.storewheel.data.models.ProductsResponse
import com.example.storewheel.network.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {
    @GET(Constants.PRODUCTS_URL)
    suspend fun getProducts(): ProductsResponse
    @GET(Constants.SEARCH_PRODUCTS_URL)
    suspend fun searchProducts(@Query("q") query: String): ProductsResponse
    @GET(Constants.PRODUCT_DETAILS_URL)
    suspend fun getProductById(@Path("id") id: Int): ProductDetailsResponse
}