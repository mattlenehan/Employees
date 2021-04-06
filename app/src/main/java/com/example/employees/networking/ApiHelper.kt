package com.example.employees.networking

class ApiHelper(private val apiService: EmployeeWebservice) {
    fun getEmployees() = apiService.getEmployees()
}