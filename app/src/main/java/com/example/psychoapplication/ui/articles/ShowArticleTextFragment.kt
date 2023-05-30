package com.example.psychoapplication.ui.articles

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.databinding.FragmentShowArticleTextBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class ShowArticleTextFragment : Fragment() {

    private var _binding: FragmentShowArticleTextBinding? = null
    private val binding get() = _binding!!

    private var objArticle: Article? = null

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

        objArticle?.let {
            binding.label.text = it.title
            binding.content.text = it.content
            binding.date.text = getDateTime(it.date)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Date): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        return sdf.format(s)
    }
}