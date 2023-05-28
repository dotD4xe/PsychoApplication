package com.example.psychoapplication.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychoapplication.R
import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.databinding.FragmentArticlesBinding
import com.example.psychoapplication.util.findTopNavController

class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val adapter by lazy {
        ArticlesAdapter(
            onItemClicked = { pos, item ->
                findTopNavController().navigate(R.id.showArticleTextFragment,Bundle().apply {
                    putParcelable("article",item)
                })
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articles = listOf(Article("test", 1, "test"))
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.submitList(articles)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}