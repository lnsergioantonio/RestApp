package com.lnsergioantonio.restapp.data.local.dao

import androidx.room.*
import com.lnsergioantonio.restapp.data.local.entitues.RequestResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: RequestResponseEntity): Long

    @Transaction
    @Query("""
        UPDATE request_response SET
        requestAt = :requestAt,
         responseAt = :responseAt,
        time = :time,
        statusCode = :statusCode,
        size = :size,
        responseBody = :responseBody,
        isSuccessful = :isSuccessful,
        error = :error
        WHERE rowid = :id
    """)
    suspend fun update(id: Long,
                       requestAt: String,
                       responseAt: String,
                       time: String,
                       statusCode: Int,
                       size: Long,
                       responseBody: String,
                       isSuccessful: Int,
                       error: String)

    @Transaction
    @Query("SELECT * FROM request_response LIMIT 20")
    fun getAllFlow(): Flow<List<RequestResponseEntity>?>

}