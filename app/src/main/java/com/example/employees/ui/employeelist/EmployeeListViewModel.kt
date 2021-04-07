package com.example.employees.ui.employeelist

import androidx.lifecycle.*
import com.example.employees.model.Employee
import com.example.employees.networking.ApiHelper
import com.example.employees.networking.EmployeeRepository
import com.example.employees.networking.EmployeeResponse
import com.example.employees.utils.ApiResult
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

internal class EmployeeListViewModel(private val repository: EmployeeRepository) : ViewModel() {

    private val itemsLiveData = MutableLiveData<ApiResult<List<Employee>>>()
    private val subscriptions = CompositeDisposable()
    val listRelay = BehaviorRelay.create<ListType>().apply {
        accept(ListType.EMPLOYEE)
    }

    init {
        fetchEmployees(listRelay.value ?: ListType.EMPLOYEE)
    }

    fun fetchEmployees(listType: ListType) {
        itemsLiveData.postValue(ApiResult.loading(null))
        var employeeResponse: Single<EmployeeResponse>? = when (listType) {
            ListType.EMPLOYEE -> repository.getEmployees()
            ListType.EMPTY -> repository.getEmptyEmployees()
            ListType.MALFORMED -> repository.getMalformedEmployees()
        }
        employeeResponse?.let {
            it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemsLiveData.postValue(ApiResult.success(it.items))
                }, {
                    itemsLiveData.postValue(ApiResult.error(it.message ?: "Something went wrong", null))
                })
                .addTo(subscriptions)
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    fun getEmployees(): LiveData<ApiResult<List<Employee>>> {
        return itemsLiveData
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