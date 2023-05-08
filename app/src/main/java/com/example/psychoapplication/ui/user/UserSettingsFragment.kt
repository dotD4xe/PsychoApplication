package com.example.psychoapplication.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psychoapplication.databinding.FragmentUserSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSettingsFragment : Fragment() {

    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}