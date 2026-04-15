package com.example.insuranceclaimssim.viewmodel

import androidx.lifecycle.*
import com.example.insuranceclaimssim.api.RetrofitClient
import com.example.insuranceclaimssim.data.LocalSyncManager
import com.example.insuranceclaimssim.data.model.*
import kotlinx.coroutines.launch

class SharedViewModel(private val syncManager: LocalSyncManager) : ViewModel() {

    private val _extractedBill = MutableLiveData<ExtractedBill?>(syncManager.getBill())
    val extractedBill: LiveData<ExtractedBill?> = _extractedBill

    private val _policyText = MutableLiveData<String?>(syncManager.getPolicyText())
    val policyText: LiveData<String?> = _policyText

    private val _adjudicationResult = MutableLiveData<AdjudicateResponse?>()
    val adjudicationResult: LiveData<AdjudicateResponse?> = _adjudicationResult

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun syncBill(bill: ExtractedBill) {
        _extractedBill.value = bill
        syncManager.saveBill(bill)
    }

    fun syncPolicy(text: String) {
        _policyText.value = text
        syncManager.savePolicyText(text)
    }

    fun adjudicate() {
        val bill = _extractedBill.value
        val policy = _policyText.value

        if (bill == null || policy == null) {
            _error.value = "Bill and Policy must be loaded first."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.adjudicate(AdjudicateRequest(bill, policy))
                if (response.isSuccessful) {
                    _adjudicationResult.value = response.body()
                } else {
                    _error.value = "Adjudication failed: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class SharedViewModelFactory(private val syncManager: LocalSyncManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(syncManager) as T
    }
}
