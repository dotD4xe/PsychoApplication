package com.example.psychoapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentLoginBinding
import com.example.psychoapplication.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.loginBtn.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.emailEt.text.toString(),
                    password = binding.passEt.text.toString()
                )
            }
        }

        binding.registerLabel.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            this.findNavController().navigate(action)
        }
    }

    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.loginBtn.text = ""
                    binding.loginProgress.show()
                }
                is UiState.Failure -> {
                    binding.loginBtn.text = "Login"
                    binding.loginProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.loginBtn.text = "Login"
                    binding.loginProgress.hide()
                    toast(state.data)
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeNavigation()
                    this.findNavController().navigate(action)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            toast("enter mail")
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
                toast("invalid email")
            }
        }
        if (binding.passEt.text.isNullOrEmpty()){
            isValid = false
            toast("enter password")
        }else{
            if (binding.passEt.text.toString().length < 6){
                isValid = false
                toast("invalid password")
            }
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSession { user ->
            if (user != null){
                val action = LoginFragmentDirections.actionLoginFragmentToHomeNavigation()
                this.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}