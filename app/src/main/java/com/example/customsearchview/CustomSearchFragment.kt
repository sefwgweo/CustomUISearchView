package com.example.customsearchview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customsearchview.databinding.FragmentSearchBinding

class CustomSearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        fun newInstance() = CustomSearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSearchBinding.inflate(inflater, container, false).root
    }
}