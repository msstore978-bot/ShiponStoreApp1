package com.shiponstore.customermanagement.data.repository

import com.shiponstore.customermanagement.data.local.dao.CustomerDao
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import com.shiponstore.customermanagement.data.local.entity.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Repository layer (Architecture: ViewModel -> Repository -> Room).
 *
 * Every write here is local-only and always succeeds without a network
 * connection, per PART 4 (Offline-first Principle): User Action -> Local
 * Database -> UI immediately updated -> (later) Sync Engine -> Sheets.
 */
class CustomerRepository(private val customerDao: CustomerDao) {

    private val searchQuery = MutableStateFlow("")

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun observeCustomers(): Flow<List<CustomerEntity>> =
        searchQuery.flatMapLatest { query ->
            if (query.isBlank()) customerDao.observeAll() else customerDao.search(query)
        }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    suspend fun addCustomer(
        name: String,
        shopName: String,
        phone: String,
        openingDue: Double
    ): Long {
        val serverId = "CUST-" + System.currentTimeMillis()
        val entity = CustomerEntity(
            serverId = serverId,
            name = name,
            shopName = shopName,
            phone = phone,
            openingDue = openingDue,
            currentDue = openingDue,
            syncStatus = SyncStatus.PENDING
        )
        return customerDao.insert(entity)
    }

    suspend fun updateCustomer(customer: CustomerEntity) {
        customerDao.update(
            customer.copy(
                updatedAt = System.currentTimeMillis(),
                syncStatus = SyncStatus.PENDING
            )
        )
    }

    suspend fun deleteCustomer(customer: CustomerEntity) {
        customerDao.delete(customer)
    }

    suspend fun getById(localId: Long): CustomerEntity? = customerDao.getById(localId)

    suspend fun count(): Int = customerDao.count()
}
