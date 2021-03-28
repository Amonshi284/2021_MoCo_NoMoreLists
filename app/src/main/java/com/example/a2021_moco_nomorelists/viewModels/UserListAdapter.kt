package com.example.a2021_moco_nomorelists.viewModels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021_moco_nomorelists.R
import com.example.a2021_moco_nomorelists.models.User

class UserListAdapter : ListAdapter<User, UserListAdapter.UserViewHolder>(UsersComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.street, current.city, current.zip.toString(), current.phoneNumber.toString(), current.email)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.editTextName)
        private val streetItemView: TextView = itemView.findViewById(R.id.editTextStreet)
        private val cityItemView: TextView = itemView.findViewById(R.id.editTextCity)
        private val zipItemView: TextView = itemView.findViewById(R.id.editTextZIP)
        private val phoneItemView: TextView = itemView.findViewById(R.id.editTextPhone)
        private val emailItemView: TextView = itemView.findViewById(R.id.editTextEmailAddress)

        fun bind(name: String?, street: String?, city: String?, zip: String, phone: String, email: String?){
            if (name.isNullOrEmpty() || street.isNullOrEmpty() || city.isNullOrEmpty() || email.isNullOrEmpty()) {
                throw Exception("RecyclerView Null Input!")
            }
            nameItemView.text = name
            streetItemView.text = street
            cityItemView.text = city
            zipItemView.text = zip
            phoneItemView.text = phone
            emailItemView.text = email
        }

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return UserViewHolder(view)
            }
        }
    }

    class UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name && oldItem.street == newItem.street && oldItem.city == newItem.city && oldItem.zip == newItem.zip && oldItem.phoneNumber == newItem.phoneNumber && oldItem.email == newItem.email
        }
    }
}