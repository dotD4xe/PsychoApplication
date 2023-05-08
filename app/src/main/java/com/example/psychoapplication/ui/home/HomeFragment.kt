package com.example.psychoapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.psychoapplication.R
import com.example.psychoapplication.databinding.FragmentHomeBinding
import com.example.psychoapplication.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlCard.setOnClickListener {
            findTopNavController().navigate(
                R.id.showTextFragment,
                bundleOf("type" to "what_is_rl")
            )
        }
        binding.dCard.setOnClickListener {
            findTopNavController().navigate(
                R.id.showTextFragment,
                bundleOf("type" to "diagnostics")
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}