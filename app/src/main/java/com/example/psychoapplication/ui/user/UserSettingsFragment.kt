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
import com.example.psychoapplication.util.findTopNavController
import com.example.psychoapplication.util.toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSettingsFragment : Fragment() {

    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        viewModel.getSession {
            binding.nameEt.text = it?.name.toString()
            binding.emailEt.text = it?.email.toString()
        }

        binding.changeBtnName.setOnClickListener {
            viewModel.getSession {
                it!!.name = binding.name.text.toString()

                viewModel.updateSession(it) { user ->
                    binding.nameEt.text = user?.name.toString()
                }
            }
        }

        binding.exit.setOnClickListener {
            viewModel.logout {
                findTopNavController().navigate(R.id.LoginFragment, null, navOptions {
                    popUpTo(R.id.tabsFragment) {
                        inclusive = true
                    }
                })
            }
        }

        binding.changeBtn.setOnClickListener {
            updatePassword(binding.password.text.toString(), binding.password1.text.toString())
        }
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        //get current user
        println(oldPassword)
        println(newPassword)
        val user = firebaseAuth.currentUser
        //before changing password re-authenticate the user
        val authCredential = EmailAuthProvider.getCredential(user!!.email!!, oldPassword)
        user.reauthenticate(authCredential)
            .addOnSuccessListener {
                //successfully authenticated, begin update
                user.updatePassword(newPassword)
                    .addOnSuccessListener {
                        //password updated
                        toast("Password Updated...")
                    }
                    .addOnFailureListener { e ->
                        //failed updating password, show reason
                        toast(e.message)
                    }
            }
            .addOnFailureListener { e ->
                //authentication failed, show reason
                toast(e.message)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}