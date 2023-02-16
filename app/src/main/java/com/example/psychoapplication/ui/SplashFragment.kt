package com.example.psychoapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.postDelayed({
            val mAuth = FirebaseAuth.getInstance()
            if (mAuth.currentUser != null) {
                val action = SplashFragmentDirections.actionSplashFragmentToTestFragment()
                this.findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionSplashFragmentToRegistrationFragment()
                this.findNavController().navigate(action)
            }
        }, 3000)

    }

}