package com.testcomponent.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "MyTestTable", primaryKeys= [ "Id", "Params" ])
data class MyTestTable (  @ColumnInfo(name = "Id") var Id: Int,
                          @ColumnInfo(name = "Params") var Params: String,
                          @ColumnInfo(name = "Rank") var Rank: Int?,
                          @ColumnInfo(name="Date") var Date: String?
)