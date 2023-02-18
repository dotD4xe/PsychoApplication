package com.example.psychoapplication.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()
        binding.textRegistration.setOnClickListener {
            view.post {
                val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
                this.findNavController().navigate(action)
            }
        }


        binding.loginButton.setOnClickListener {
            binding.editTextEmail.error = null
            binding.editTextPassword.error = null
            if(checkEmail() && checkPassword()){
                val email= binding.editTextEmail.editText?.text.toString()
                val password= binding.editTextPassword.editText?.text.toString()
                if (isAttachedToActivity()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                view.post {
                                    val action =
                                        LoginFragmentDirections.actionLoginFragmentToTestFragment(
                                            email
                                        )
                                    this.findNavController().navigate(action)
                                }
                            } else {
                                binding.editTextEmail.error = "invalid username or password"
                            }
                        }
                }
            }
        }
    }

    private fun checkEmail(): Boolean {
        return if (
            binding.editTextEmail.editText?.text.toString().trim{it<=' '}.isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.editText?.text.toString()).matches()
        ) true
        else {
            binding.editTextEmail.error = "email is not correct"
            false
        }
    }

    private fun checkPassword():Boolean {
        return if(
            binding.editTextPassword.editText?.text.toString().trim{it<=' '}.isNotEmpty() &&
            binding.editTextPassword.editText?.text.toString().count() >= 6
        )  true
        else {
            binding.editTextPassword.error = "Password is not correct"
            false
        }
    }

    fun isAttachedToActivity(): Boolean {
        return isVisible
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}