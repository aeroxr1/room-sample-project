package com.testcomponent.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testcomponent.room.dao.MyTestDao
import com.testcomponent.room.entities.MyTestTable

@Database(entities = [MyTestTable::class], version = 1, exportSchema = false)
abstract class MyTestDb : RoomDatabase() {

    abstract fun myTestDao(): MyTestDao

    companion object {
        private var INSTANCE: MyTestDb ?= null

        fun getInstance(context: Context): MyTestDb {
            var instanceConfigurator = INSTANCE
            if (instanceConfigurator == null) {
                synchronized(MyTestDb::class) {
                    val path = context.getDatabasePath("dbTest.db").absolutePath
                    instanceConfigurator = Room.databaseBuilder(
                        context.applicationContext,
                        MyTestDb::class.java,
                        path)
                        .build()
                }
            }
            return instanceConfigurator!!
        }

        fun setInstance(myTestDb: MyTestDb) {
            synchronized(MyTestDb::class) {
                INSTANCE = myTestDb
            }
        }

        fun destroyInstance() {
            if (INSTANCE != null && INSTANCE!!.isOpen) {
                INSTANCE?.close()
            }
            INSTANCE = null
        }
    }
}