package com.lnsergioantonio.restapp.di

import com.lnsergioantonio.restapp.domain.network.SendRequestRepository
import com.lnsergioantonio.restapp.domain.network.SendRequestUseCase
import com.lnsergioantonio.restapp.ui.home.fragment.RequestViewModel

class RequestContainer(repository: SendRequestRepository) {
    private val useCase = SendRequestUseCase(repository)
    val viewModel = RequestViewModel(useCase)
}