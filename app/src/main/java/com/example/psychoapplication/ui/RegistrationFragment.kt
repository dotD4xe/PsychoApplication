package com.example.psychoapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.psychoapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import me.ibrahimsn.lib.PhoneNumberKit
import java.util.concurrent.TimeUnit


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var number: String

    private lateinit var mAuth: FirebaseAuth

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


        val phoneNumberKit = PhoneNumberKit.Builder(requireContext())
            .setIconEnabled(true)
            .build()

        phoneNumberKit.attachToInput(binding.textField, "ru")

        phoneNumberKit.setupCountryPicker(
            activity = requireContext() as AppCompatActivity,
            searchEnabled = true
        )

        binding.continueBtn.setOnClickListener {
            val parsedNumber = phoneNumberKit.parsePhoneNumber(
                number = binding.editText.text.toString(),
                defaultRegion = null
            )
            parsedNumber?.nationalNumber
            number = "+"+ parsedNumber?.countryCode.toString() + parsedNumber?.nationalNumber.toString()
            if (number.count() != 12) {
                binding.editText.error = "This field can not be blank"
            } else {
                authUser()
            }
        }
    }

    private fun authUser() {
        mAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this.requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(
                verificationId: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToOTPFragment(
                    itemId = verificationId,
                    number = number)
                this@RegistrationFragment.findNavController().navigate(action)
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
                        val action = RegistrationFragmentDirections.actionRegistrationFragmentToTestFragment()
                        this@RegistrationFragment.findNavController().navigate(action)
                    } else Log.d("awash", task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("awash", e.toString())
            }
        }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}