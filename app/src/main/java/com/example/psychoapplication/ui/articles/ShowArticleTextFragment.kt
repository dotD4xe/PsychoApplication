package com.example.psychoapplication.ui.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.databinding.FragmentShowArticleTextBinding

class ShowArticleTextFragment : Fragment() {

    private var _binding: FragmentShowArticleTextBinding? = null
    private val binding get() = _binding!!

    var objArticle: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowArticleTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        objArticle = arguments?.getParcelable("article")
        Log.d("ayash", objArticle?.title.toString())
    }
}