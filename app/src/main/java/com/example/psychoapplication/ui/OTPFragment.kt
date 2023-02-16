package com.example.psychoapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.psychoapplication.databinding.FragmentOTPBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OTPFragment : Fragment() {

    private var _binding: FragmentOTPBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: OTPFragmentArgs by navArgs()

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding.otpView.setOtpCompletionListener { otp ->
            val credential = PhoneAuthProvider.getCredential(navigationArgs.itemId, otp)
            mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
                    val action = OTPFragmentDirections.actionOTPFragmentToTestFragment()
                    this@OTPFragment.findNavController().navigate(action)
                }
            }
        }
    }


}