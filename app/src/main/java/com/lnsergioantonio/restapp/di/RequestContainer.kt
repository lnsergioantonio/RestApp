package com.lnsergioantonio.restapp.di

import com.lnsergioantonio.restapp.domain.SendRequestRepository
import com.lnsergioantonio.restapp.domain.SendRequestUseCase
import com.lnsergioantonio.restapp.ui.home.fragment.RequestViewModel

class RequestContainer(repository: SendRequestRepository) {
    private val useCase = SendRequestUseCase(repository)
    val viewModel = RequestViewModel(useCase)
}