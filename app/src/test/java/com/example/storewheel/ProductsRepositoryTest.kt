package com.example.storewheel
import com.example.storewheel.data.models.ProductResponse
import com.example.storewheel.data.models.ProductsResponse
import com.example.storewheel.data.repositories.ProductsRepository
import com.example.storewheel.domain.models.ProductModel
import com.example.storewheel.network.api.ProductsApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductsRepositoryTest {
    private val productsApi = mockk<ProductsApi>()
    private val productsRepository = ProductsRepository(productsApi)

    @Test
    fun getProductsListSuccess() = runTest {
        val mockResult = ProductsResponse(
            listOf(ProductResponse(1, "title", "image", 10.0)))
        // arrangement
        coEvery { productsApi.getProducts(skip = 0) } returns mockResult
        // action
        val result = productsRepository.getProductsList(0)
        // assertion
        assert(result.isSuccess)
        // verification
        coVerify (exactly = 1){ productsApi.getProducts(skip = 0) }

        confirmVerified(productsApi)
    }

    @Test
    fun getProductsListSuccessEmpty() = runTest {
        val mockResult = ProductsResponse(
            emptyList())
        coEvery { productsApi.getProducts(skip = 0) } returns mockResult
        val result = productsRepository.getProductsList(0)
        assert(result.isSuccess)
        assertEquals(Result.success(emptyList<ProductModel>()), result)
        coVerify (exactly = 1){ productsApi.getProducts(skip = 0) }

        confirmVerified(productsApi)
    }
}