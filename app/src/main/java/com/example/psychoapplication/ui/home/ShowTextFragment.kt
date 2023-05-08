package com.example.psychoapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentShowTextBinding
import com.example.psychoapplication.util.UiState
import com.example.psychoapplication.util.hide
import com.example.psychoapplication.util.show
import com.example.psychoapplication.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTextFragment : Fragment() {

    private var _binding: FragmentShowTextBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = requireArguments().getString("type", " ")
        observer()
        homeViewModel.getInfo(type)
        binding.back.setOnClickListener { findNavController().navigateUp() }
    }

    private fun observer(){
        homeViewModel.informations.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progress.show()
                }
                is UiState.Failure -> {
                    binding.progress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progress.hide()
                    binding.label.text = state.data?.label
                    binding.content.text = state.data?.content
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}