package com.example.psychoapplication.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        binding.confirmButton.setOnClickListener {
            binding.editTextEmail.error = null
            binding.editTextPassword.error = null
            binding.editTextName.error = null
            binding.errorText.text = ""
            if(checkName() && checkEmail() && checkPassword()) {
                val email= binding.editTextEmail.editText?.text.toString()
                val password= binding.editTextPassword.editText?.text.toString()
                val name= binding.editTextName.editText?.text.toString()
                val user= hashMapOf(
                    "Name" to name,
                    "email" to email
                )
                val users = db.collection("USERS")
                users.whereEqualTo("email",email).get()
                    .addOnSuccessListener { tasks->
                        if(tasks.isEmpty) {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(requireActivity()){ task->
                                    if(task.isSuccessful) {
                                        users.document(email).set(user)
                                        val action = RegistrationFragmentDirections.actionRegisterFragmentToHomeNavigation()
                                        this.findNavController().navigate(action)
                                    }
                                    else {
                                        binding.errorText.text = "Authentication Failed"
                                    }
                                }
                        } else {
                            binding.errorText.text = "User Already Registered"
                        }
                    }
            }
        }
    }

    private fun checkEmail(): Boolean {
        return if (
            binding.editTextEmail.editText?.text.toString().isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.editText?.text.toString()).matches()
        ) true
        else {
            binding.editTextEmail.error = "Email is not correct"
            false
        }
    }

    private fun checkName(): Boolean {
        return if (binding.editTextName.editText?.text.toString().trim{it<=' '}.isNotEmpty()) true
        else {
            binding.editTextName.error = "Enter a name"
            false
        }
    }

    private fun checkPassword():Boolean {
        return if(
            binding.editTextPassword.editText?.text.toString().trim{it<=' '}.isNotEmpty() &&
            binding.editTextPassword.editText?.text.toString().count() >= 6
        )  true
        else {
            binding.editTextPassword.error = "Password is not correct. at least 6 characters "
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}