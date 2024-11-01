package com.aruel.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aruel.mvvmnewsapp.R
import com.aruel.mvvmnewsapp.adapters.EventAdapter
import com.aruel.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.aruel.mvvmnewsapp.ui.EventViewModel
import com.aruel.mvvmnewsapp.ui.NewsActivity
import com.aruel.mvvmnewsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.aruel.mvvmnewsapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var viewModel: EventViewModel
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private val TAG = "SearchNewsFragment"
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false) // Inflate binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        eventAdapter.setOnItemClickListener { event ->
            val bundle = Bundle().apply {
                putSerializable("Events", event)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { eventResponse ->
                        eventAdapter.differ.submitList(eventResponse.listEvents)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvSearchNews.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
