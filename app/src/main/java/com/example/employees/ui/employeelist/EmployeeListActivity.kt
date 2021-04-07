package com.example.employees.ui.employeelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.employees.R
import com.example.employees.databinding.ActivityEmployeeListBinding
import com.example.employees.databinding.ViewItemEmployeeBinding
import com.example.employees.model.isValid
import com.example.employees.networking.ApiHelper
import com.example.employees.networking.EmployeeWebserviceImpl
import com.example.employees.ui.toBindings
import com.example.employees.utils.RequestStatus

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityEmployeeListBinding

    private lateinit var adapter: EmployeeListAdapter

    private var viewModel: EmployeeListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_employee_list)
        bindings.employeeList.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeListAdapter(listener = listener)
        bindings.employeeList.adapter = adapter
        bindings.employeeButton.setOnClickListener {
            listener.onEmployeeFetchClick()
        }
        bindings.emptyButton.setOnClickListener {
            listener.onEmptyFetchClick()
        }
        bindings.malformedButton.setOnClickListener {
            listener.onMalformedFetchClick()
        }
    }

    private fun setupObserver() {
        viewModel?.getEmployees()?.observe(this, {
            when (it.requestStatus) {
                RequestStatus.SUCCESS -> {
                    bindings.progressBar.visibility = View.GONE
                    val items = it.data
                        ?.filter { employee ->
                            employee.isValid()
                        }?.map { employee ->
                            EmployeeListViewItem.EmployeeItem(
                                employee.id,
                                employee,
                                employee.fullName
                            )
                        }
                    if (items?.isEmpty() == true) {
                        bindings.emptyState.visibility = View.VISIBLE
                        bindings.employeeList.visibility = View.GONE
                    } else {
                        bindings.emptyState.visibility = View.GONE
                        bindings.employeeList.visibility = View.VISIBLE
                    }
                    renderList(items)
                }
                RequestStatus.LOADING -> {
                    bindings.emptyState.visibility = View.GONE
                    bindings.progressBar.visibility = View.VISIBLE
                    bindings.employeeList.visibility = View.GONE
                }
                RequestStatus.ERROR -> {
                    bindings.emptyState.visibility = View.GONE

                    //Handle Error
                    bindings.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(items: List<EmployeeListViewItem.EmployeeItem>?) {
        items?.let {
            adapter.acceptAll(items.sortedBy { it.sortKey })
        } ?: run {
            adapter.acceptAll(emptyList())
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(EmployeeWebserviceImpl()))
        ).get(EmployeeListViewModel::class.java)
    }

    private val listener = object : EmployeeListener {
        override fun onItemClick(employeeId: String) {}

        override fun onEmployeeFetchClick() {
            viewModel?.listRelay?.accept(ListType.EMPLOYEE)
            viewModel?.fetchEmployees(ListType.EMPLOYEE)
        }

        override fun onEmptyFetchClick() {
            viewModel?.listRelay?.accept(ListType.EMPTY)
            viewModel?.fetchEmployees(ListType.EMPTY)
        }

        override fun onMalformedFetchClick() {
            viewModel?.listRelay?.accept(ListType.MALFORMED)
            viewModel?.fetchEmployees(ListType.MALFORMED)
        }
    }
}

interface EmployeeListener {
    fun onItemClick(employeeId: String)
    fun onEmployeeFetchClick()
    fun onEmptyFetchClick()
    fun onMalformedFetchClick()
}

private class EmployeeListAdapter(val listener: EmployeeListener) :
    RecyclerView.Adapter<EmployeeListViewHolder>() {

    val callback = object : SortedListAdapterCallback<EmployeeListViewItem>(this) {
        override fun compare(a: EmployeeListViewItem?, b: EmployeeListViewItem?): Int {
            return 1
        }

        override fun areItemsTheSame(
            item1: EmployeeListViewItem?,
            item2: EmployeeListViewItem?
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: EmployeeListViewItem?,
            newItem: EmployeeListViewItem?
        ): Boolean {
            return true
        }
    }

    val items = SortedList(
        EmployeeListViewItem::class.java,
        callback
    )

    fun acceptAll(viewItems: List<EmployeeListViewItem>) =
        items.replaceAll(viewItems)

    override fun getItemViewType(position: Int) =
        items.get(position).layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.view_item_employee -> {
                val bindings =
                    R.layout.view_item_employee.toBindings<ViewItemEmployeeBinding>(
                        inflater,
                        parent
                    )
                EmployeeListViewHolder.EmployeeItem(bindings)
            }
            else -> throw IllegalArgumentException("Unsupported item type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        val item = items.get(position)
        when (holder) {
            is EmployeeListViewHolder.EmployeeItem -> holder.bind(
                item = item as EmployeeListViewItem.EmployeeItem,
                onClick = { id -> listener.onItemClick(id) }
            )
        }
    }

    override fun getItemCount() =
        items.size()
}
