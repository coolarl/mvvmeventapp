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
import com.aruel.mvvmnewsapp.databinding.FragmentPastEventsBinding
import com.aruel.mvvmnewsapp.ui.EventViewModel
import com.aruel.mvvmnewsapp.util.Resource
import androidx.fragment.app.activityViewModels

class PastEventsFragment : Fragment(R.layout.fragment_past_events) {

    private var _binding: FragmentPastEventsBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels to get the shared ViewModel instance from the activity
    private val viewModel: EventViewModel by activityViewModels()

    private lateinit var eventAdapter: EventAdapter

    private val TAG = "PastEventsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPastEventsBinding.bind(view)

        setupRecyclerView()

        // Item click listener to navigate to details
        eventAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Events", it)
            }
            findNavController().navigate(
                R.id.action_pastEventsFragment_to_articleFragment, // Use appropriate action
                bundle
            )
        }

        // Observe past events data
        viewModel.pastEvents.observe(viewLifecycleOwner, Observer { response ->
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

        // Fetch past events
        viewModel.getPastEvents()
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvPastEvents.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
