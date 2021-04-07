package com.example.employees.utils

data class ApiResult<out T>(val requestStatus: RequestStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(RequestStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ApiResult<T> {
            return ApiResult(RequestStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ApiResult<T> {
            return ApiResult(RequestStatus.LOADING, data, null)
        }
    }
}