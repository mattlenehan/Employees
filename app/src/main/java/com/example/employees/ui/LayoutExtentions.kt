package com.example.employees.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding?> Int.toBindings(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean = false
) = DataBindingUtil.inflate<T>(inflater, this, parent, attachToParent)