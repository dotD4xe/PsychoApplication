package com.example.psychoapplication.ui.auth

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

    override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToHomeNavigation()
            this.findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()
        binding.textRegistration.setOnClickListener {
            binding.editTextEmail.error = null
            binding.editTextPassword.error = null
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            this.findNavController().navigate(action)
        }


        binding.loginButton.setOnClickListener {
            binding.editTextEmail.error = null
            binding.editTextPassword.error = null
            if(checkEmail() && checkPassword()){
                val email= binding.editTextEmail.editText?.text.toString()
                val password= binding.editTextPassword.editText?.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val action = LoginFragmentDirections.actionLoginFragmentToHomeNavigation()
                            this.findNavController().navigate(action)
                        } else {
                            binding.editTextEmail.error = "invalid username or password"
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}