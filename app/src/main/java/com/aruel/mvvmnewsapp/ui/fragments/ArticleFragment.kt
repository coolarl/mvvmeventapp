package com.aruel.mvvmnewsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.net.Uri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aruel.mvvmnewsapp.R
import com.aruel.mvvmnewsapp.databinding.FragmentArticleBinding
import com.aruel.mvvmnewsapp.ui.EventViewModel
import com.aruel.mvvmnewsapp.ui.NewsActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: EventViewModel
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        _binding = FragmentArticleBinding.bind(view)

        val article = args.Events

        // Load the image using Glide
        Glide.with(this).load(article.mediaCover).into(binding.ivArticleImage)

        // Bind the event details to the TextViews
        binding.tvTitle.text = article.name
        binding.tvOwnerName.text = getString(R.string.event_owner_format, article.ownerName)
        binding.tvRemainingQuota.text = getString(R.string.event_quota_format, article.quota - article.registrants)
        binding.tvDescription.text = HtmlCompat.fromHtml(
            article.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.tvDate.text = getString(R.string.event_time_format, article.beginTime)

        // Set up the FloatingActionButton to save the article
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnEventLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.link))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
