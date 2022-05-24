package com.badger.familyorgfe.data.source.products

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.data.source.products.json.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductsApi {
    companion object {
        private const val API = "/products"
        private const val GET_PRODUCTS = "$API/getProducts"
        private const val ADD_PRODUCTS = "$API/addProducts"
        private const val UPDATE_PRODUCTS = "$API/updateProducts"
        private const val DELETE_PRODUCTS = "$API/deleteProduct"
        private const val GET_PRODUCTS_BY_CODE = "$API/getProductsByCode"
    }

    @POST(GET_PRODUCTS)
    suspend fun getProducts(
        @Body form: GetProductsJson.Form
    ): BaseResponse<GetProductsJson.Response>

    @POST(ADD_PRODUCTS)
    suspend fun addProducts(
        @Body form: AddProductsJson.Form
    ): BaseResponse<AddProductsJson.Response>

    @POST(UPDATE_PRODUCTS)
    suspend fun updateProducts(
        @Body form: UpdateProductsJson.Form
    ): BaseResponse<UpdateProductsJson.Response>

    @POST(DELETE_PRODUCTS)
    suspend fun deleteProducts(
        @Body form: DeleteProductsJson.Form
    ): BaseResponse<DeleteProductsJson.Response>

    @POST(GET_PRODUCTS_BY_CODE)
    suspend fun getProductsByCode(
        @Body form: GetProductsByCodeJson.Form
    ): BaseResponse<GetProductsJson.Response>
}