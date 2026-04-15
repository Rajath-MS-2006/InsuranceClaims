package com.example.insuranceclaimssim.data

import android.content.Context
import android.content.SharedPreferences
import com.example.insuranceclaimssim.data.model.ExtractedBill
import com.google.gson.Gson

class LocalSyncManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveBill(bill: ExtractedBill) {
        prefs.edit().putString("last_bill", gson.toJson(bill)).apply()
    }

    fun getBill(): ExtractedBill? {
        val json = prefs.getString("last_bill", null) ?: return null
        return gson.fromJson(json, ExtractedBill::class.java)
    }

    fun savePolicyText(text: String) {
        prefs.edit().putString("last_policy", text).apply()
    }

    fun getPolicyText(): String? {
        return prefs.getString("last_policy", null)
    }
    
    fun clearSync() {
        prefs.edit().clear().apply()
    }
}
