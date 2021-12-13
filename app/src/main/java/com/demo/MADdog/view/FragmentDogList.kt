
package com.demo.MADdog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.MADdog.viewmodel.DogViewModel
import com.demo.MADdog.R

class FragmentDogList : Fragment() {
    private val viewModel: DogViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getDogNameList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dog_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DogAdapter(::onItemClickListener)
        val rv = view.findViewById<RecyclerView>(R.id.dog_rv)
        val tv = view.findViewById<TextView>(R.id.dog_count)

        tv.text = getString(R.string.loading)
        rv?.layoutManager = LinearLayoutManager(context)
        rv?.adapter = adapter
        rv?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        viewModel.dogNameList.observe(viewLifecycleOwner) {
            tv.text = getString(R.string.dog_count, it.size)
            adapter.submitList(it)
        }

        viewModel.dogNameListError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                tv.text = it
            }
        }
    }

    private fun onItemClickListener(dogName: String) {
        findNavController().navigate(R.id.action_fragment_doglist_to_fragment_dogimage,
                                    bundleOf(FragmentDogImage.PARAM_NAME to dogName))
    }
}