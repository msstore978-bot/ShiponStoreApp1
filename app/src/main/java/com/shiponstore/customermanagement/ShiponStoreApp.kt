package com.shiponstore.customermanagement

import android.app.Application
import com.shiponstore.customermanagement.data.local.database.AppDatabase
import com.shiponstore.customermanagement.data.repository.CustomerRepository

/**
 * Simple manual-DI application class (no Hilt/Dagger in V1, to keep the
 * foundation easy to build and reason about). Exposes the Room database
 * and the Customer repository as lazy singletons.
 */
class ShiponStoreApp : Application() {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    val customerRepository: CustomerRepository by lazy {
        CustomerRepository(database.customerDao())
    }
}
