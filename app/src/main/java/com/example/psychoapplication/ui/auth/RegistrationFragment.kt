package com.example.psychoapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.data.model.User
import com.example.psychoapplication.databinding.FragmentRegistrationBinding
import com.example.psychoapplication.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.registerBtn.setOnClickListener {
            if (validation()){
                viewModel.register(
                    email = binding.emailEt.text.toString(),
                    password = binding.passEt.text.toString(),
                    user = getUserObj()
                )
            }
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.registerBtn.text = ""
                    binding.registerProgress.show()
                }
                is UiState.Failure -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    toast(state.data)
                    val action = RegistrationFragmentDirections.actionRegistrationFragmentToTabsFragment()
                    this.findNavController().navigate(action)
                }
                else -> { }
            }
        }
    }

    private fun getUserObj(): User {
        return User(
            id = "",
            name = binding.firstNameEt.text.toString(),
            email = binding.emailEt.text.toString(),
        )
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.firstNameEt.text.isNullOrEmpty()){
            isValid = false
            toast("enter name")
        }

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            toast("enter email")
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}