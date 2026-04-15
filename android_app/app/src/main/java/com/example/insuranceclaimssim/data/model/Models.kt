package com.example.insuranceclaimssim.data.model

import com.google.gson.annotations.SerializedName

data class BillItem(
    val description: String,
    val amount: Double,
    val quantity: Int? = 1,
    val date: String? = null
)

data class ExtractedBill(
    @SerializedName("hospital_name") val hospitalName: String?,
    @SerializedName("patient_name") val patientName: String?,
    @SerializedName("date_of_admission") val dateOfAdmission: String?,
    val items: List<BillItem>,
    @SerializedName("total_billed") val totalBilled: Double
)

data class AdjudicateRequest(
    val bill: ExtractedBill,
    @SerializedName("policy_text") val policyText: String
)

data class AdjudicateResponse(
    val result: ClaimResult,
    val explanation: String
)

data class ClaimResult(
    @SerializedName("total_billed") val totalBilled: Double,
    @SerializedName("total_covered") val totalCovered: Double,
    @SerializedName("total_rejected") val totalRejected: Double,
    val traces: List<AdjudicationTrace>
)

data class AdjudicationTrace(
    @SerializedName("item_description") val itemDescription: String,
    @SerializedName("original_amount") val originalAmount: Double,
    @SerializedName("ontology_category") val ontologyCategory: String,
    @SerializedName("amount_covered") val amountCovered: Double,
    @SerializedName("amount_rejected") val amountRejected: Double,
    @SerializedName("rejection_reason") val rejectionReason: String,
    @SerializedName("rule_applied") val ruleApplied: String
)

data class PolicyRule(
    val category: String,
    @SerializedName("cap_amount") val capAmount: Double?,
    @SerializedName("copay_percentage") val copayPercentage: Double?,
    @SerializedName("is_excluded") val isExcluded: Boolean,
    @SerializedName("raw_clause") val rawClause: String
)
