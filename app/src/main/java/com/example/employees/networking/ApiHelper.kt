package com.example.employees.networking

class ApiHelper(private val apiService: EmployeeWebservice) {
    fun getEmployees() = apiService.getEmployees()
    fun getEmptyEmployees() = apiService.getEmptyEmployees()
    fun getMalformedEmployees() = apiService.getMalformedEmployees()
}