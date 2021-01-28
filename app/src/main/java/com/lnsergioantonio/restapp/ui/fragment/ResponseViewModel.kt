package com.lnsergioantonio.restapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnsergioantonio.restapp.domain.GetResponseUseCase
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.base.UseCase
import com.lnsergioantonio.restapp.domain.model.ResponseEntity

class ResponseViewModel(private val responseUseCase: GetResponseUseCase) : ViewModel() {
    private val responseLiveData = MutableLiveData<State<List<ResponseEntity>>>()

    fun getResponse(){
        responseUseCase.invoke(viewModelScope,UseCase.None()){
            responseLiveData.value = it
        }
    }
}