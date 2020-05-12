package com.testcomponent.room.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.testcomponent.room.entities.MyTestTable

@Dao
interface MyTestDao {

    data class Result(val code:Int,val value:String)

    @RawQuery
    fun getAllRow(query: SupportSQLiteQuery): Array<Result>

}