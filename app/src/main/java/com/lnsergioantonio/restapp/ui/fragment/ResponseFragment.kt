package com.lnsergioantonio.restapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.App
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.di.ResponseContainer

class ResponseFragment : Fragment() {

    private lateinit var viewModel: ResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_response, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerview()
    }

    private fun initViewModel() {
        val appContainer = (requireActivity().application as App).appContainer
        viewModel = ResponseContainer(appContainer.getResponseRepository).viewModel
    }

    private fun initRecyclerview() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ResponseFragment()
    }
}