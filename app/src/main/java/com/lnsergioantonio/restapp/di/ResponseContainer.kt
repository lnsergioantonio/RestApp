package com.lnsergioantonio.restapp.di

import com.lnsergioantonio.restapp.domain.GetResponseRepository
import com.lnsergioantonio.restapp.domain.GetResponseUseCase
import com.lnsergioantonio.restapp.ui.fragment.ResponseViewModel

class ResponseContainer(private val repository: GetResponseRepository) {
    private val useCase = GetResponseUseCase(repository)
    val viewModel = ResponseViewModel(useCase)
}