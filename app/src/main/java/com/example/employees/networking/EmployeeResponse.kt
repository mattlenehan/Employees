package com.example.employees.networking

import com.example.employees.constants.Constants
import com.example.employees.model.Employee
import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName(Constants.EMPLOYEES) val items: List<Employee>
)