package com.example.monday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.monday.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        binding.btnLivedata.setOnClickListener {
            viewModel.setTvLiveData("Live Data")
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }

        binding.btnSharedflow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }

        binding.btnStateflow.setOnClickListener {
            viewModel.setTvStateFlow("State Flow")
        }

        viewModel.tvLiveData.observe(this) { text ->
            binding.tvLivedata.text = text
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tvStateFlow.collectLatest {
                binding.tvStateflow.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tvSharedFlow.collectLatest {
                binding.tvSharedflow.text = it
            }
        }
    }
}