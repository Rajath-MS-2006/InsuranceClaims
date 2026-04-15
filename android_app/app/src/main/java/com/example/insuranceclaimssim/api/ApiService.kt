package com.example.insuranceclaimssim.api

import com.example.insuranceclaimssim.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/")
    suspend fun checkHealth(): Response<Map<String, Any>>

    @Multipart
    @POST("/api/extract_bill")
    suspend fun extractBill(
        @Part file: MultipartBody.Part
    ): Response<ExtractedBill>

    @POST("/api/parse_policy")
    @FormUrlEncoded
    suspend fun parsePolicy(
        @Field("policy_text") policyText: String
    ): Response<List<PolicyRule>>

    @POST("/api/adjudicate")
    suspend fun adjudicate(
        @Body request: AdjudicateRequest
    ): Response<AdjudicateResponse>
}
