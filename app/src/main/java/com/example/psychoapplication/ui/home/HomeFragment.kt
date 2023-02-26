package com.example.psychoapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.R
import com.example.psychoapplication.databinding.FragmentTestBinding
import com.example.psychoapplication.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
            authViewModel.logout {
                val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                this.findNavController().navigate(action)
            }
        }
        authViewModel.getSession {
            binding.helloText.text = activity?.getString(R.string.hello, it?.name.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}