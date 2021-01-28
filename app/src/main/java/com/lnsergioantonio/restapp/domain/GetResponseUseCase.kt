package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.base.UseCase
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.flow.Flow

class GetResponseUseCase(private val repository: GetResponseRepository): UseCase<List<ResponseEntity>, UseCase.None>(){
    override fun run(params: None): Flow<State<List<ResponseEntity>>> {
        return repository.fetchResponses()
    }
}