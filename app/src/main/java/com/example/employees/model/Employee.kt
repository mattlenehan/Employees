package com.example.employees.model

import androidx.annotation.NonNull
import com.example.employees.constants.Constants.BIOGRAPHY
import com.example.employees.constants.Constants.EMAIL_ADDRESS
import com.example.employees.constants.Constants.EMPLOYEE_TYPE
import com.example.employees.constants.Constants.FULL_NAME
import com.example.employees.constants.Constants.PHONE_NUMBER
import com.example.employees.constants.Constants.PHOTO_URL_LARGE
import com.example.employees.constants.Constants.PHOTO_URL_SMALL
import com.example.employees.constants.Constants.TEAM
import com.example.employees.constants.Constants.UUID
import com.google.gson.annotations.SerializedName

data class Employee(
    @NonNull
    @SerializedName(UUID) val id: String,

    @NonNull
    @SerializedName(FULL_NAME) val fullName: String,

    @SerializedName(PHONE_NUMBER) val phoneNumber: String?,

    @NonNull
    @SerializedName(EMAIL_ADDRESS) val emailAddress: String,

    @SerializedName(BIOGRAPHY) val biography: String?,

    @SerializedName(PHOTO_URL_SMALL) val photoUrlSmall: String?,

    @SerializedName(PHOTO_URL_LARGE) val photoUrlLarge: String?,

    @NonNull
    @SerializedName(TEAM) val team: String,

    @NonNull
    @SerializedName(EMPLOYEE_TYPE) val employeeType: EmployeeType
)

fun Employee.isValid(): Boolean {
    return id != null && fullName != null && emailAddress != null && team != null && employeeType != null
}