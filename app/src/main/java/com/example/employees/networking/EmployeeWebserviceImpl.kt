package com.example.employees.networking

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class EmployeeWebserviceImpl : EmployeeWebservice {
    override fun getEmployees(): Single<EmployeeResponse> {
        return Rx2AndroidNetworking.get("https://s3.amazonaws.com/sq-mobile-interview/employees.json")
            .build()
            .getObjectSingle(EmployeeResponse::class.java)
    }

    override fun getMalformedEmployees(): Single<EmployeeResponse> {
        return Rx2AndroidNetworking.get("https://s3.amazonaws.com/sq-mobile-interview/employees_malformed.json")
            .build()
            .getObjectSingle(EmployeeResponse::class.java)
    }

    override fun getEmptyEmployees(): Single<EmployeeResponse> {
        return Rx2AndroidNetworking.get("https://s3.amazonaws.com/sq-mobile-interview/employees_empty.json")
            .build()
            .getObjectSingle(EmployeeResponse::class.java)
    }
}