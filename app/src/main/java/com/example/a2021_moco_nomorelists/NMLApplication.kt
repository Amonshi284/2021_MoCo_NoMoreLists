package com.example.a2021_moco_nomorelists

import android.app.Application
import com.example.a2021_moco_nomorelists.models.UserRepository
import com.example.a2021_moco_nomorelists.models.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NMLApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}