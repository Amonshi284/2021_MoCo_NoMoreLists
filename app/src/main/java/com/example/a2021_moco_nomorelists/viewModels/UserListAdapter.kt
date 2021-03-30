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
        holder.bind(current.id.toString(), current.name, current.street, current.city, current.zip.toString(), current.phone.toString(), current.email)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idItemView: TextView = itemView.findViewById(R.id.textViewId)
        private val nameItemView: TextView = itemView.findViewById(R.id.textViewName)
        private val streetItemView: TextView = itemView.findViewById(R.id.textViewStreet)
        private val cityItemView: TextView = itemView.findViewById(R.id.textViewCity)
        private val zipItemView: TextView = itemView.findViewById(R.id.textViewZIP)
        private val phoneItemView: TextView = itemView.findViewById(R.id.textViewPhone)
        private val emailItemView: TextView = itemView.findViewById(R.id.textViewEmail)

        fun bind(id: String, name: String?, street: String?, city: String?, zip: String, phone: String, email: String?){
            if (name.isNullOrEmpty() || street.isNullOrEmpty() || city.isNullOrEmpty() || email.isNullOrEmpty()) {
                throw Exception("RecyclerView Null Input!")
            }
            idItemView.text = id
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
            return oldItem.name == newItem.name && oldItem.street == newItem.street && oldItem.city == newItem.city && oldItem.zip == newItem.zip && oldItem.phone == newItem.phone && oldItem.email == newItem.email
        }
    }
}