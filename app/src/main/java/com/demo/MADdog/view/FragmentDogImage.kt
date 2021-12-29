package com.demo.MADdog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.demo.MADdog.R
import com.demo.MADdog.viewmodel.DogViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FragmentDogImage : Fragment() {

    private val viewModel: DogViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(PARAM_NAME)?.let {
            viewModel.getDogImageUrl(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dog_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.dog_image)
        //image.setImageResource(0)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogImageUrl.collect {
                    image.load(it)
                }
            }
        }
    }

    companion object {
        const val PARAM_NAME = "dog_name"
    }
}