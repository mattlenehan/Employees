package com.example.employees.networking

import io.reactivex.Single

interface EmployeeWebservice {
    fun getEmployees(): Single<EmployeeResponse>
    fun getMalformedEmployees(): Single<EmployeeResponse>
    fun getEmptyEmployees(): Single<EmployeeResponse>
}