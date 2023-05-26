package com.example.psychoapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.R
import com.example.psychoapplication.databinding.FragmentForgotPasswordBinding
import com.example.psychoapplication.util.UiState
import com.example.psychoapplication.util.hide
import com.example.psychoapplication.util.show
import com.example.psychoapplication.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.sendBtn.setOnClickListener{
            viewModel.forgotPassword(binding.emailEt.text.toString())
        }
        binding.back.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun validation(){TODO("сделать валидацию а то крашит")}

    private fun observer(){
        viewModel.forgotPassword.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.sendBtn.text = ""
                    binding.sendProgress.show()
                }
                is UiState.Failure -> {
                    binding.sendBtn.text = getString(R.string.send)
                    binding.sendProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.sendBtn.text = getString(R.string.send)
                    binding.sendProgress.hide()
                    toast(state.data)
                }
                else -> {  }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}