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
import com.example.psychoapplication.ui.auth.AuthViewModel
import com.example.psychoapplication.util.findTopNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSettingsFragment : Fragment() {

    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //        authViewModel.getSession {
//            binding.helloText.text = activity?.getString(R.string.hello, it?.name.toString())
//            println(it?.id)
//        }
//
//        binding.changeName.setOnClickListener {
//            authViewModel.getSession {
//                it!!.name = binding.name.text.toString()
//                authViewModel.updateUserInfo(it)
//
//
//                authViewModel.updateSession(it) { user ->
//                    binding.helloText.text = activity?.getString(R.string.hello, user?.name.toString())
//                }
//            }
//        }
//        binding.rlCard.setOnClickListener {
////            homeViewModel.getInfo("what_is_rl")
////            observer()
//        }
        binding.exit.setOnClickListener {
            viewModel.logout {
                findTopNavController().navigate(R.id.LoginFragment, null, navOptions {
                    popUpTo(R.id.tabsFragment) {
                        inclusive = true
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}