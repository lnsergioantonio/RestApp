package com.lnsergioantonio.restapp.ui.home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnsergioantonio.restapp.App
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.di.ResponseContainer
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import com.lnsergioantonio.restapp.ext.launchActivity
import com.lnsergioantonio.restapp.ui.home.fragment.adapter.ResponseAdapter
import com.lnsergioantonio.restapp.ui.home.fragment.adapter.toItems
import com.lnsergioantonio.restapp.ui.response.ARG_RESPONSE_BODY
import com.lnsergioantonio.restapp.ui.response.ARG_RESPONSE_HEADERS
import com.lnsergioantonio.restapp.ui.response.ResponseDetailActivity
import kotlinx.android.synthetic.main.fragment_response.*

class ResponseFragment : Fragment() {

    private lateinit var viewModel: ResponseViewModel
    private lateinit var adapter: ResponseAdapter

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
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initViewModel() {
        val appContainer = (requireActivity().application as App).appContainer
        viewModel = ResponseContainer(appContainer.getResponseRepository).viewModel
    }

    private fun initRecyclerview() {
        adapter = ResponseAdapter()
        adapter.setOnClickItem { item ->
            requireContext().launchActivity<ResponseDetailActivity> {
                putExtra(ARG_RESPONSE_BODY,item.body)
                //putExtra(ARG_RESPONSE_HEADERS,item.)
            }
        }

        responseList.adapter = adapter
        responseList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = responseList.layoutManager as LinearLayoutManager
                val lastPosition = manager.findLastVisibleItemPosition()
                if (lastPosition == (adapter.itemCount - 1)){
                    viewModel.getResponse()
                }
            }
        })
    }

    private fun initObservers() {
        viewModel.responseState.observe(viewLifecycleOwner, {
            onChangeResponseList(it)
        })
    }

    private fun initData(){
        viewModel.getResponse()
    }

    private fun onChangeResponseList(stateResponseList: State<List<ResponseEntity>>?) {
        stateResponseList?.let {noNullStateResponseList ->
            when(noNullStateResponseList){
                is State.Success -> adapter.submitList(noNullStateResponseList.data.toItems())
                is State.Failure -> Log.e("ERROR -> ", noNullStateResponseList.message)
                is State.Progress -> Log.e("Progress -> ", "... ")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResponseFragment()
    }
}