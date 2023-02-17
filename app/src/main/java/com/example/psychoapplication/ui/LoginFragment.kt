package com.example.psychoapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            if(checking()){
                val email= binding.editTextEmail.editText?.text.toString()
                val password= binding.editTextPassword.editText?.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            view.post {
                                val action = LoginFragmentDirections.actionLoginFragmentToTestFragment(email)
                                this.findNavController().navigate(action)
                            }
                        } else {
//                            Toast.makeText(requireContext(), "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(requireContext(),"Enter the Details",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking():Boolean
    {
        if(binding.editTextEmail.editText?.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.editTextPassword.editText?.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}