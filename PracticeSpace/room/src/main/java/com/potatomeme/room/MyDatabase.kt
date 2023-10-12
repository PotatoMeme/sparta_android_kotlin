package com.potatomeme.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Student::class],
    exportSchema = false ,
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getMyDao() : MyDAO

    companion object{
        private var INSTANCE : MyDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getDatabsae(context: Context) : MyDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = MyDatabase::class.java,
                    name = "school_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
            }
            return INSTANCE as MyDatabase
        }
    }

}