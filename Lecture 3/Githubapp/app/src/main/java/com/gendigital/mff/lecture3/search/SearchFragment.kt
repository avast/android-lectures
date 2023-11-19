package com.gendigital.mff.lecture3.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gendigital.mff.lecture3.R
import com.gendigital.mff.lecture3.databinding.FragmentSearchBinding
import com.gendigital.mff.lecture3.user.UserActivity
import com.google.android.material.color.MaterialColors

class SearchFragment: Fragment(R.layout.fragment_search) {

    companion object {
        const val KEY_USERNAME = "username"
    }

    private var _binding: FragmentSearchBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        binding.appBarLayout.setStatusBarForegroundColor(
            MaterialColors.getColor(binding.appBarLayout, com.google.android.material.R.attr.colorSurfaceVariant))

        /**
         * Restore the UI state after configuration change.
         */
        savedInstanceState?.let {
            binding.txtUsername.editText?.setText(it.getString(KEY_USERNAME, ""))
        }

        binding.btnSubmit.setOnClickListener {
            // Pass values to the secondary activity.
            val intent = Intent(requireActivity(), UserActivity::class.java).apply {
                putExtra(KEY_USERNAME, binding.txtUsername.editText?.text.toString())
            }
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, binding.txtUsername.editText?.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}