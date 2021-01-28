package com.lnsergioantonio.restapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.domain.*

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
        /*val database = provideDatabase(requireActivity().application)
        val dao = provideRequestResponseDao(database)*/

        val repository = GetResponseRepositoryImpl()
        val useCase = GetResponseUseCase(repository)

        viewModel = ResponseViewModel(useCase)
    }

    private fun initRecyclerview() {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResponseFragment()
    }
}