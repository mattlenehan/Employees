package com.example.employees.networking

import io.reactivex.Single

class EmployeeRepository(private val apiHelper: ApiHelper) {
    fun getEmployees(): Single<EmployeeResponse> {
        return apiHelper.getEmployees()
    }
}
