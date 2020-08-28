package com.example.android.exampleTest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact_table")
data class Model(@PrimaryKey @ColumnInfo(name = "id")
                 var id: Int? = null,
                 @ColumnInfo(name = "name")
                 var name: String? = null,
                 @ColumnInfo(name = "number")
                 var number: String? = null)
