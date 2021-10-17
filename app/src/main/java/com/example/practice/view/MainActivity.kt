package com.example.practice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.practice.R
import com.example.practice.viewmodel.DogViewModel
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.repeatOnLifecycle
import com.example.practice.repo.AppDatabase

class MainActivity : AppCompatActivity() {

    private val vm: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Activity里可用以下代码进行导航
        // val nav = findNavController(R.id.fragmentContainerView)
        // nav.navigate(R.id.action_fragment1_to_fragment2)
        // Toast.makeText(this, "I am activity", Toast.LENGTH_SHORT).show()

// Suspend fun call
        val tvSuspend = findViewById<TextView>(R.id.textViewSuspend)

        findViewById<Button>(R.id.buttonSuspend).setOnClickListener {
            tvSuspend.text = "Suspend: Loading..."
            vm.requestDogListSuspend()
        }

        vm.dogList.observe(this) {
            if (it.isFailure) {
                tvSuspend.text = "Suspend: Failed!"
            } else {
                tvSuspend.text = "Suspend: " +it.getOrNull()?.message.toString()
            }
        }

// State Flow
        val tvFlow = findViewById<TextView>(R.id.textViewFlow)

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {  // This API Only available from 2.4.0rc1
                vm.dogListFlow.collect {
                    if (it.isFailure) {
                        tvFlow.text = "Flow: Failed!"
                    } else {
                        tvFlow.text = "Flow: " + it.getOrNull()?.message.toString()
                    }
                }
            }
        }

        vm.requestDogListFlow(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()

// Live Data
        val tvLiveData = findViewById<TextView>(R.id.textViewLiveData)
        vm.logListLiveData.observe(this) {
            if (it.isFailure) {
                tvLiveData.text = "LiveData: Failed!"
            } else {
                tvLiveData.text = "LiveData: " + it.getOrNull()?.message.toString()
            }
        }
    }
}