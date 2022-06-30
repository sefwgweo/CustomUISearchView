package com.example.customsearchview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.customsearchview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel = SearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        initObserver()
    }

    private fun initObserver() {
        viewModel.searchData.observe(this, Observer {
            val fragment = CustomSearchFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putString(SearchListViewModel.KEY_SEARCH, it)
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        })
    }
}