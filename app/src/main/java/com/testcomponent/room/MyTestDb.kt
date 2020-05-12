package com.testcomponent.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testcomponent.room.dao.MyTestDao
import com.testcomponent.room.entities.MyTestTable
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory

@Database(entities = [MyTestTable::class], version = 1, exportSchema = false)
abstract class MyTestDb : RoomDatabase() {

    abstract fun myTestDao(): MyTestDao

    companion object {

        fun wrapHook(hook: SQLiteDatabaseHook?): SQLiteDatabaseHook? {
            return if (hook == null) {
                keyHook
            } else object : SQLiteDatabaseHook {
                override fun preKey(database: SQLiteDatabase) {
                    keyHook.preKey(database)
                    hook.preKey(database)
                }

                override fun postKey(database: SQLiteDatabase) {
                    keyHook.postKey(database)
                    hook.preKey(database)
                }
            }
        }

        var keyHook: SQLiteDatabaseHook = object : SQLiteDatabaseHook {
            override fun preKey(database: SQLiteDatabase) {
            }

            override fun postKey(database: SQLiteDatabase) {}
        }

        private var INSTANCE: MyTestDb ?= null

        fun getInstance(context: Context): MyTestDb {
            var instanceConfigurator = INSTANCE
            if (instanceConfigurator == null) {
                synchronized(MyTestDb::class) {
                    val path = context.getDatabasePath("dbTest.db").absolutePath
                    val passphrase = SQLiteDatabase.getBytes("".toCharArray())
                    val factory = SupportFactory(passphrase, wrapHook(null))
                    instanceConfigurator = Room.databaseBuilder(
                        context.applicationContext,
                        MyTestDb::class.java,
                        path)
                        .openHelperFactory(factory)
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