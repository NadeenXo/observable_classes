package com.example.monday

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.monday.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by lazy { ViewModelProvider(this)[MyViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initListeners()
        initObservables()
    }

    private fun initObservables() {
        viewModel.tvLiveData.observe(this) { text -> binding.tvLivedata.text = text }

        lifecycleScope.launchWhenStarted {
            viewModel.tvStateFlow.collectLatest { binding.tvStateflow.text = it }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tvSharedFlow.collectLatest { binding.tvSharedflow.text = it }
        }
    }

    private fun initListeners() {
        binding.btnLivedata.setOnClickListener {
            viewModel.setTvLiveData("Live Data")
            Toast.makeText(this, "LiveData", Toast.LENGTH_SHORT).show()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                    Toast.makeText(this@MainActivity, "Flow $it", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSharedflow.setOnClickListener {
            viewModel.triggerSharedFlow()
            Toast.makeText(this@MainActivity, "SharedFlow", Toast.LENGTH_SHORT).show()
        }

        binding.btnStateflow.setOnClickListener {
            viewModel.setTvStateFlow("State Flow")
            Toast.makeText(this@MainActivity, "StateFlow", Toast.LENGTH_SHORT).show()
        }
    }
}