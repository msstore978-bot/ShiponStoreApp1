package com.shiponstore.customermanagement.ui.test

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shiponstore.customermanagement.R
import com.shiponstore.customermanagement.ShiponStoreApp
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import com.shiponstore.customermanagement.databinding.ActivityOfflineTestBinding

/**
 * Developer / offline test screen (PART 6 / PART 7).
 *
 * Everything on this screen reads and writes only through Room. There is
 * no network call anywhere in this file, so every one of the checks below
 * works identically with Wi-Fi/mobile data fully turned off:
 *
 *  - Customer locally save হচ্ছে কি না         -> btnSave -> addCustomer()
 *  - Customer locally retrieve হচ্ছে কি না       -> customers LiveData -> RecyclerView
 *  - Customer search করা যাচ্ছে কি না            -> inputSearch -> onSearchQueryChanged()
 *  - Data update করা যাচ্ছে কি না                 -> btnEdit -> edit mode -> btnSave
 *  - Data delete করার architecture আছে কি না      -> btnDelete -> deleteCustomer()
 *  - App restart করার পর data থাকে কি না          -> Room persists to disk automatically
 *  - Internet বন্ধ থাকলেও data access করা যায় কি না -> textNetworkStatus banner + all of the above
 */
class OfflineTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineTestBinding
    private lateinit var viewModel: CustomerTestViewModel
    private lateinit var adapter: CustomerTestAdapter

    /** Non-null while the form is editing an existing row instead of creating a new one. */
    private var editingCustomer: CustomerEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as ShiponStoreApp
        viewModel = ViewModelProvider(
            this,
            CustomerTestViewModel.Factory(app.customerRepository)
        )[CustomerTestViewModel::class.java]

        setupList()
        setupForm()
        updateNetworkBanner()

        viewModel.customers.observe(this) { customers ->
            adapter.submitList(customers)
            binding.textEmpty.visibility = if (customers.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            binding.textCount.text = "Locally stored: ${customers.size} customer(s) — restart the app and reopen this screen to confirm they are still here."
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-check on resume so toggling airplane mode while this screen is
        // open (PART 7) is reflected immediately, without touching any data.
        updateNetworkBanner()
    }

    private fun setupList() {
        adapter = CustomerTestAdapter(
            onEdit = { customer -> enterEditMode(customer) },
            onDelete = { customer -> viewModel.deleteCustomer(customer) }
        )
        binding.recyclerCustomers.adapter = adapter
        binding.recyclerCustomers.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)
    }

    private fun setupForm() {
        binding.btnSave.setOnClickListener { onSaveClicked() }
        binding.btnCancelEdit.setOnClickListener { exitEditMode() }

        binding.inputSearch.addTextChangedListener(afterTextChanged = { text ->
            viewModel.onSearchQueryChanged(text?.toString().orEmpty())
        })
    }

    private fun onSaveClicked() {
        val name = binding.inputName.text?.toString()?.trim().orEmpty()
        val shop = binding.inputShop.text?.toString()?.trim().orEmpty()
        val phone = binding.inputPhone.text?.toString()?.trim().orEmpty()
        val due = binding.inputDue.text?.toString()?.toDoubleOrNull() ?: 0.0

        if (name.isEmpty()) {
            binding.inputName.error = getString(R.string.test_name_hint)
            return
        }

        val current = editingCustomer
        if (current == null) {
            viewModel.addCustomer(name, shop, phone, due)
        } else {
            viewModel.updateCustomer(current, name, shop, phone, due)
        }
        exitEditMode()
    }

    private fun enterEditMode(customer: CustomerEntity) {
        editingCustomer = customer
        binding.inputName.setText(customer.name)
        binding.inputShop.setText(customer.shopName)
        binding.inputPhone.setText(customer.phone)
        binding.inputDue.setText(customer.currentDue.toString())
        binding.btnSave.setText(R.string.test_save)
        binding.btnCancelEdit.visibility = android.view.View.VISIBLE
    }

    private fun exitEditMode() {
        editingCustomer = null
        binding.inputName.text?.clear()
        binding.inputShop.text?.clear()
        binding.inputPhone.text?.clear()
        binding.inputDue.text?.clear()
        binding.btnSave.setText(R.string.test_add_sample)
        binding.btnCancelEdit.visibility = android.view.View.GONE
    }

    /**
     * Purely informational banner (PART 7) - it never gates or changes any
     * database operation. Its only job is to make it obvious, while you are
     * manually testing with the network off, that the app noticed.
     */
    private fun updateNetworkBanner() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = network?.let { cm.getNetworkCapabilities(it) }
        val isOnline = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        binding.textNetworkStatus.setText(
            if (isOnline) R.string.test_status_online else R.string.test_status_offline
        )
    }
}

/**
 * Small local helper so we don't need to pull in a whole extensions library
 * just for one TextWatcher callback.
 */
private fun com.google.android.material.textfield.TextInputEditText.addTextChangedListener(
    afterTextChanged: (CharSequence?) -> Unit
) {
    this.addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: android.text.Editable?) {
            afterTextChanged(s?.toString())
        }
    })
}
