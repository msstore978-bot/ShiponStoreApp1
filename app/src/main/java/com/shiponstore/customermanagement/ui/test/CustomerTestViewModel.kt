package com.shiponstore.customermanagement.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import com.shiponstore.customermanagement.data.repository.CustomerRepository
import kotlinx.coroutines.launch

/**
 * Presentation-layer state holder (Architecture: Presentation -> ViewModel
 * -> Repository -> Room). All operations here are local/offline only.
 */
class CustomerTestViewModel(private val repository: CustomerRepository) : ViewModel() {

    val customers = repository.observeCustomers().asLiveData()

    fun onSearchQueryChanged(query: String) {
        repository.setSearchQuery(query)
    }

    fun addCustomer(name: String, shop: String, phone: String, due: Double) {
        viewModelScope.launch {
            repository.addCustomer(name, shop, phone, due)
        }
    }

    fun updateCustomer(customer: CustomerEntity, name: String, shop: String, phone: String, due: Double) {
        viewModelScope.launch {
            repository.updateCustomer(
                customer.copy(name = name, shopName = shop, phone = phone, currentDue = due)
            )
        }
    }

    fun deleteCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)
        }
    }

    class Factory(private val repository: CustomerRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CustomerTestViewModel(repository) as T
        }
    }
}
