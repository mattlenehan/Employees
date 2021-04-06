package com.example.employees.ui.employeelist

import androidx.lifecycle.*
import com.example.employees.model.Employee
import com.example.employees.networking.ApiHelper
import com.example.employees.networking.EmployeeRepository
import com.example.employees.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

internal class EmployeeListViewModel(private val repository: EmployeeRepository) : ViewModel() {

    private val items = MutableLiveData<Resource<List<Employee>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchEmployees()
    }

    private fun fetchEmployees() {
        items.postValue(Resource.loading(null))
        compositeDisposable.add(
            repository.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    items.postValue(Resource.success(it.items))
                }, {
                    items.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getEmployees(): LiveData<Resource<List<Employee>>> {
        return items
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeListViewModel::class.java)) {
            return EmployeeListViewModel(EmployeeRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}