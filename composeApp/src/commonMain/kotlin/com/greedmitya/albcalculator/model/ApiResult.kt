package com.greedmitya.albcalculator.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}
