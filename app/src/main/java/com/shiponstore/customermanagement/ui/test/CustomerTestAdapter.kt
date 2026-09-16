package com.shiponstore.customermanagement.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import com.shiponstore.customermanagement.databinding.ItemCustomerBinding

class CustomerTestAdapter(
    private val onEdit: (CustomerEntity) -> Unit,
    private val onDelete: (CustomerEntity) -> Unit
) : ListAdapter<CustomerEntity, CustomerTestAdapter.VH>(DIFF) {

    inner class VH(val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val customer = getItem(position)
        holder.binding.textName.text = customer.name
        holder.binding.textShopPhone.text = "${customer.shopName} • ${customer.phone}"
        holder.binding.textDue.text = "বর্তমান বকেয়া: ৳ ${customer.currentDue}"
        holder.binding.textSyncStatus.text = "${customer.syncStatus} (local id ${customer.localId})"
        holder.binding.btnEdit.setOnClickListener { onEdit(customer) }
        holder.binding.btnDelete.setOnClickListener { onDelete(customer) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CustomerEntity>() {
            override fun areItemsTheSame(oldItem: CustomerEntity, newItem: CustomerEntity) =
                oldItem.localId == newItem.localId

            override fun areContentsTheSame(oldItem: CustomerEntity, newItem: CustomerEntity) =
                oldItem == newItem
        }
    }
}
