package com.testcomponent.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import java.io.*
import java.lang.Exception

/*
    poc with databaseRoomVersion = '2.2.5' with error "Pre-packaged database has an invalid schema"
    ***** databaseRoomVersion '2.1.0' no errors *****
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{
            extractAssetToDatabaseDirectory("dbTest.db")
            val query = "SELECT json_extract(Test, '$.code') as code,json_extract(Test, '$.value') as value from MyTestTable"
            try {
                val simpleQuery = SimpleSQLiteQuery(query)
                val rows =
                    MyTestDb.getInstance(this.applicationContext).myTestDao().getAllRow(simpleQuery)
                Log.d("MainActivity","found ${rows.size} rows")
            }catch (e:Exception){
                Log.e("MainActivity","errore",e)
            }

        }.start()
    }

    @Throws(IOException::class)
    fun extractAssetToDatabaseDirectory(fileName: String) {
        var length: Int
        val sourceDatabase: InputStream = assets.open(fileName)
        val destinationPath: File = getDatabasePath(fileName)
        val destination: OutputStream = FileOutputStream(destinationPath)
        val buffer = ByteArray(4096)
        while (sourceDatabase.read(buffer).also { length = it } > 0) {
            destination.write(buffer, 0, length)
        }
        sourceDatabase.close()
        destination.flush()
        destination.close()
    }
}
