package com.example.a2021_moco_nomorelists.models

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity
data class User(
        @PrimaryKey(autoGenerate = true) val id: Int?,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "street") val street: String?,
        @ColumnInfo(name = "city") val city: String?,
        @ColumnInfo(name = "zip") val zip: Int?,
        @ColumnInfo(name = "phone") val phone: Long,
        @ColumnInfo(name = "email") val email: String?
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id IS (:userId)")
    fun getById(userId: Int): User

    @Insert
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
public abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                )
                    .addCallback(UserRoomDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class UserRoomDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            userDao.deleteAll()

            val user = User(null, "Tobias Marcus", "Wiesenauel 25", "Overath", 51491, 15782243814, "tobtob.marcuc@web.de")
            userDao.insert(user)
        }
    }


}