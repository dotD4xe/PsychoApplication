package com.example.psychoapplication.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.psychoapplication.R
import com.example.psychoapplication.databinding.FragmentTestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TestFragment : Fragment() {

    private val navigationArgs: TestFragmentArgs by navArgs()

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharedPref.getString("Email","1")
        binding.logout.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            val action = TestFragmentDirections.actionTestFragmentToLoginFragment()
            this.findNavController().navigate(action)
        }
        if(isLogin=="1") {
            val email = navigationArgs.email
            if(email != "1") {
                setText(email)
                with(sharedPref.edit()) {
                    putString("Email",email)
                    apply()
                }
            } else{
                Log.d("vayash", "немыслемая хуйня")
            }
        } else setText(isLogin)

    }

    private fun setText(email:String?) {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { tasks->
                    binding.helloText.text= activity?.getString(R.string.hello, tasks.get("Name").toString())
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}