package com.example.employees.model

import com.example.employees.R

enum class EmployeeType {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
}

fun EmployeeType.getDisplayName(): String {
    return when (this) {
        EmployeeType.FULL_TIME -> "FULL"
        EmployeeType.PART_TIME -> "PART"
        EmployeeType.CONTRACTOR -> "CONTRACTOR"
    }
}

fun EmployeeType.getBackground(): Int {
    return when (this) {
        EmployeeType.FULL_TIME -> R.drawable.rounded_tile_full
        EmployeeType.PART_TIME -> R.drawable.rounded_tile_part
        EmployeeType.CONTRACTOR -> R.drawable.rounded_tile_contractor
    }
}

fun EmployeeType.getTextColor(): String {
    return when (this) {
        EmployeeType.FULL_TIME -> "#000000"
        EmployeeType.PART_TIME -> "#000000"
        EmployeeType.CONTRACTOR -> "#FFFFFF"
    }
}