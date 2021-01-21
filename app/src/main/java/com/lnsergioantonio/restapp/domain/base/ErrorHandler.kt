package com.lnsergioantonio.restapp.domain.base

import android.content.Context
import android.view.View
import android.widget.Toast
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

object ErrorHandler {

    private const val EMPTY_RESPONSE = "Server returned empty response."
    const val NETWORK_ERROR_MESSAGE = "Please check your internet connectivity and try again!"
    const val NO_SUCH_DATA = "Data not found in the database"
    const val UNKNOWN_ERROR = "An unknown error occurred!"

    fun handleError(
            view: View? = null,
            throwable: State.Failure,
            shouldToast: Boolean = false,
            shouldShowSnackBar: Boolean = false,
            refreshAction: () -> Unit = {}
    ) {
        if (shouldShowSnackBar && view != null) {
            showSnackBar(view, message = throwable.message, refresh = refreshAction)
        } else {
            if (shouldToast && view != null) {
                showLongToast(view.context, throwable.message)
            }
        }
        when (throwable.exception) {
            is IOException -> Timber.e(NETWORK_ERROR_MESSAGE)
            is HttpException -> Timber.e(
                "HTTP Exception: ${throwable.exception.code()}"
            )
            is NoResponseException -> Timber.e(EMPTY_RESPONSE)
            is NoDataException -> Timber.e(NO_SUCH_DATA)
            else -> Timber.e(throwable.exception)
        }
    }

    private fun showSnackBar(view: View, message: String, refresh: () -> Unit = {}) {
        //IndefiniteSnackbar.show(view, message, refresh)
    }

    private fun showLongToast(context: Context, message: String) = Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()

    inline fun <reified T> parseError(responseBody: ResponseBody?): T? {
        val moshi = Moshi.Builder().build() // mover esto a application con koin
        val parser = moshi.adapter(T::class.java)
        val response = responseBody?.string()
        if (response != null)
            try {
                return parser.fromJson(response)
            } catch (e: JsonDataException) {
                e.printStackTrace()
            }
        return null
    }
}