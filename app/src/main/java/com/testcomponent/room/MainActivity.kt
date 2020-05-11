package com.testcomponent.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.*

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
            val rows = MyTestDb.getInstance(this.applicationContext).myTestDao().getAllRow()
            Log.d("MainActivity","found ${rows.size} rows")
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
