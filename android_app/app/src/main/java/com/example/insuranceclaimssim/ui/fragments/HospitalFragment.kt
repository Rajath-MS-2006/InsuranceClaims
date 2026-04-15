package com.example.insuranceclaimssim.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.insuranceclaimssim.data.LocalSyncManager
import com.example.insuranceclaimssim.data.model.BillItem
import com.example.insuranceclaimssim.data.model.ExtractedBill
import com.example.insuranceclaimssim.databinding.FragmentHospitalBinding
import com.example.insuranceclaimssim.viewmodel.SharedViewModel
import com.example.insuranceclaimssim.viewmodel.SharedViewModelFactory

class HospitalFragment : Fragment() {
    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(LocalSyncManager(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn_upload_bill.setOnClickListener {
            // Mocking bill extraction for simulation
            val mockBill = ExtractedBill(
                hospitalName = "Apollo City Hospital",
                patientName = "John Doe",
                dateOfAdmission = "2023-10-15",
                items = listOf(
                    BillItem("Private Room Rent (2 days)", 12000.0),
                    BillItem("Appendectomy Surgery", 45000.0)
                ),
                totalBilled = 57000.0
            )
            viewModel.syncBill(mockBill)
            binding.upload_status.text = "Mock Bill Loaded & Synced"
            binding.extraction_preview.text = mockBill.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
