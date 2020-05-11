package com.testcomponent.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.testcomponent.room.entities.MyTestTable

@Dao
interface MyTestDao {

    @Query("SELECT * from MyTestTable")
    fun getAllRow(): Array<MyTestTable>

}