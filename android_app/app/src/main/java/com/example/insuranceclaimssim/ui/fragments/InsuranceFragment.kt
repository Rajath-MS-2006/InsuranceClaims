package com.example.insuranceclaimssim.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.insuranceclaimssim.data.LocalSyncManager
import com.example.insuranceclaimssim.databinding.FragmentInsuranceBinding
import com.example.insuranceclaimssim.viewmodel.SharedViewModel
import com.example.insuranceclaimssim.viewmodel.SharedViewModelFactory

class InsuranceFragment : Fragment() {
    private var _binding: FragmentInsuranceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(LocalSyncManager(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInsuranceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn_parse_policy.setOnClickListener {
            val text = binding.et_policy_text.text.toString()
            if (text.isNotBlank()) {
                viewModel.syncPolicy(text)
                // In a real app, you'd also call the backend to parse rules into SQLite
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
