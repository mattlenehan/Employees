package com.example.employees.ui.employeelist

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.employees.R
import com.example.employees.databinding.ViewItemEmployeeBinding
import com.example.employees.model.getBackground
import com.example.employees.model.getDisplayName
import com.example.employees.model.getTextColor

internal sealed class EmployeeListViewHolder(bindings: ViewBinding) :
    RecyclerView.ViewHolder(bindings.root) {
    val context = bindings.root.context

    class EmployeeItem(val bindings: ViewItemEmployeeBinding) : EmployeeListViewHolder(bindings) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: EmployeeListViewItem.EmployeeItem, onClick: (String) -> Unit) {
            bindings.name.text = item.employee.fullName
            bindings.email.text = item.employee.emailAddress
            bindings.phone.text = item.employee.phoneNumber
            bindings.bio.text = item.employee.biography
            bindings.team.text = item.employee.team

            bindings.type.text = item.employee.employeeType.getDisplayName()
            bindings.type.background =
                context.resources.getDrawable(item.employee.employeeType.getBackground())
            bindings.type.setTextColor(Color.parseColor(item.employee.employeeType.getTextColor()))

            bindings.root.setOnClickListener {
                onClick(item.id)
            }

            Glide.with(context)
                .load(item.employee.photoUrlLarge)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(bindings.image)
        }
    }
}