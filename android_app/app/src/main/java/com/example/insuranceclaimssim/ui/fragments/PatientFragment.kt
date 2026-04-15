package com.example.insuranceclaimssim.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.insuranceclaimssim.data.LocalSyncManager
import com.example.insuranceclaimssim.databinding.FragmentPatientBinding
import com.example.insuranceclaimssim.viewmodel.SharedViewModel
import com.example.insuranceclaimssim.viewmodel.SharedViewModelFactory

class PatientFragment : Fragment() {
    private var _binding: FragmentPatientBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(LocalSyncManager(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.extractedBill.observe(viewLifecycleOwner) { bill ->
            binding.bill_status.text = if (bill != null) "Hospital Bill Loaded" else "Waiting for Hospital Bill"
        }

        viewModel.policyText.observe(viewLifecycleOwner) { text ->
            binding.policy_status.text = if (!text.isNullOrBlank()) "Insurance Policy Loaded" else "Waiting for Insurance Policy"
        }

        binding.btn_calculate.setOnClickListener {
            viewModel.adjudicate()
        }

        viewModel.adjudicationResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.results_layout.visibility = View.VISIBLE
                // Here you would update your UI components with the result
                Toast.makeText(context, "Calculation Complete", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
