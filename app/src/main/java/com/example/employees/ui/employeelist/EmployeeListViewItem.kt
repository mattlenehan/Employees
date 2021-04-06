package com.example.employees.ui.employeelist

import com.example.employees.R
import com.example.employees.model.Employee

internal sealed class EmployeeListViewItem(
    open val id: String,
    val layoutId: Int
) : Comparable<EmployeeListViewItem> {
    override fun compareTo(other: EmployeeListViewItem): Int {
        val thisOrder = this.type
        val otherOrder = other.type
        if (thisOrder == otherOrder) {
            return this.secondarySort(other)
        } else {
            return thisOrder.compareTo(otherOrder)
        }
    }

    open fun secondarySort(other: EmployeeListViewItem): Int = 0
    abstract val type: EmployeeListViewItemType

    data class EmployeeItem(
        override val id: String,
        val employee: Employee,
        val sortKey: String
    ) : EmployeeListViewItem(id = id, layoutId = R.layout.view_item_employee) {
        override fun secondarySort(other: EmployeeListViewItem): Int {
            val otherItem = other as? EmployeeItem
                ?: throw IllegalStateException("Should not happen, check your compareTo()")
            return this.sortKey.compareTo(otherItem.sortKey)
        }

        override val type = EmployeeListViewItemType.EMPLOYEE
    }
}

enum class EmployeeListViewItemType {
    EMPLOYEE
}