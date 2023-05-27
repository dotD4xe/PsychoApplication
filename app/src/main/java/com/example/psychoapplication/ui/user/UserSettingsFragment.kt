package com.example.psychoapplication.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navOptions
import com.example.psychoapplication.R
import com.example.psychoapplication.databinding.FragmentUserSettingsBinding
import com.example.psychoapplication.util.UiState
import com.example.psychoapplication.util.findTopNavController
import com.example.psychoapplication.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSettingsFragment : Fragment() {

    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerName()
        observerPassword()
        viewModel.getSession {
            binding.nameEt.text = it?.name.toString()
            binding.emailEt.text = it?.email.toString()
        }

        binding.changeBtnName.setOnClickListener {

            if (nameValidation()) viewModel.updateUserInfo(binding.name.text.toString())
        }

        binding.exit.setOnClickListener { logout() }

        binding.changeBtn.setOnClickListener {
            if (passwordValidation()) viewModel.updatePassword(binding.emailEt.text.toString(), binding.password.text.toString(), binding.password1.text.toString())
        }
    }

    private fun logout() {
        viewModel.logout {
            findTopNavController().navigate(R.id.LoginFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }
    }

    private fun passwordValidation(): Boolean {
        var isValid = true
        binding.passwordField.error = null
        binding.passwordField1.error = null
        binding.passwordField2.error = null
        if (binding.password.text.toString().isBlank()) {
            isValid = false
            binding.passwordField.error = "Введите пароль"
        }
        if (binding.password1.text.isBlank()){
            isValid = false
            binding.passwordField1.error = "Введите пароль"
        }else{
            if (binding.password1.text.toString().length < 6){
                isValid = false
                binding.passwordField1.error = "в пароле должно быть больше 6 символов"
            }
        }
        if (binding.password2.text.isBlank()){
            isValid = false
            binding.passwordField2.error = "Введите пароль"
        }else{
            if (binding.password2.text.toString().length < 6){
                isValid = false
                binding.passwordField2.error = "в пароле должно быть больше 6 символов"
            }
        }
        if (binding.password1.text.toString() != binding.password2.text.toString()) {
            binding.passwordField2.error = "Пароли не совпадают"
        }
        return isValid
    }

    private fun nameValidation(): Boolean {
        binding.nameField.error = null
        binding.nameField.clearFocus()
        var isValid = true

        if (binding.name.text.isBlank()){
            isValid = false
            binding.nameField.error = "Введите имя"
        }
        return isValid
    }

    private fun observerPassword() {
        viewModel.updatePassword.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                }
                else -> {  }
            }
        }
    }

    private fun observerName(){
        viewModel.updateUserInfo.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.nameEt.text = state.data.name
                    toast(state.data.name)
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