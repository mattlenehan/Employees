package com.example.employees.model

import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.NonNull
import androidx.core.text.isDigitsOnly
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
    return id != null && fullName != null && hasValidEmail() && team != null && hasValidEmployeeType()
}

fun Employee.hasValidOrEmptyPhoneNumber(): Boolean {
    return if (TextUtils.isEmpty(phoneNumber)) {
        true
    } else {
        Patterns.PHONE.matcher(phoneNumber).matches()
    }
}

fun Employee.hasValidEmail(): Boolean {
    return if (TextUtils.isEmpty(emailAddress)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }
}

fun Employee.hasValidEmployeeType(): Boolean {
    return when (employeeType) {
        EmployeeType.FULL_TIME,
        EmployeeType.PART_TIME,
        EmployeeType.CONTRACTOR -> true
        else -> false
    }
}