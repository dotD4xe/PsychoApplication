package com.example.psychoapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            if(checking()) {
                val email= binding.editTextEmail.editText?.text.toString()
                val password= binding.editTextPassword.editText?.text.toString()
                val name= binding.editTextName.editText?.text.toString()
                val user= hashMapOf(
                    "Name" to name,
                    "email" to email
                )
                val users = db.collection("USERS")
                val query = users.whereEqualTo("email",email).get()
                    .addOnSuccessListener { tasks->
                        if(tasks.isEmpty) {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(requireActivity()){ task->
                                    if(task.isSuccessful) {
                                        users.document(email).set(user)
                                        view.post {
                                            val action = RegistrationFragmentDirections.actionRegistrationFragmentToTestFragment(email)
                                            this.findNavController().navigate(action)
                                        }
                                    }
                                    else {
                                        Toast.makeText(requireContext(),"Authentication Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            Toast.makeText(requireContext(),"User Already Registered", Toast.LENGTH_LONG).show()
                            view.post {
                                val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
                                this.findNavController().navigate(action)
                            }
                        }
                    }
            } else{
                Toast.makeText(requireContext(),"Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking():Boolean{
        if(binding.editTextName.editText?.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.editTextEmail.editText?.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.editTextPassword.editText?.text.toString().trim{it<=' '}.isNotEmpty()
        )
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