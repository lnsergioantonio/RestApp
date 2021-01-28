package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.data.local.dao.RequestResponseDao
import com.lnsergioantonio.restapp.data.local.entitues.RequestResponseEntity
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

interface GetResponseRepository {
    fun fetchResponses() : Flow<State<List<ResponseEntity>>>
}

class GetResponseRepositoryImpl(private val dao: RequestResponseDao) : GetResponseRepository{
    override fun fetchResponses(): Flow<State<List<ResponseEntity>>> {
        return dao.getAllFlow().map {nullableResponseList->
            val result = nullableResponseList?.map { requestResponseEntity ->
                requestResponseEntity.toDomain()
            }?: emptyList()
            State.Success(result)
        }
    }
}

private fun RequestResponseEntity.toDomain():ResponseEntity =
    ResponseEntity(
        id = id,
        description = description,
        method = method,
        url = url,
        time = time,
        statusCode = statusCode,
        size = size,
        requestBody = requestBody,
        responseBody = responseBody,
        isSuccessful = isSuccessful,
        error = error
    )
