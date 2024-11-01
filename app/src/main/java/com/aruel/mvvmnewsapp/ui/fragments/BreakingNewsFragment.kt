package com.aruel.mvvmnewsapp.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aruel.mvvmnewsapp.R
import com.aruel.mvvmnewsapp.adapters.EventAdapter
import com.aruel.mvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.aruel.mvvmnewsapp.ui.EventViewModel
import com.aruel.mvvmnewsapp.util.Resource

import androidx.fragment.app.activityViewModels

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels to get the shared ViewModel instance from the activity
    private val viewModel: EventViewModel by activityViewModels()

    private lateinit var eventAdapter: EventAdapter

    private val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBreakingNewsBinding.bind(view)

        setupRecyclerView()

        eventAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Events", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }


        // Observe breaking news data
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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
        binding.rvBreakingNews.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
