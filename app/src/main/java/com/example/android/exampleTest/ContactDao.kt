
package com.example.android.exampleTest

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;




@Dao
interface ContactDao {


    @Query("SELECT * FROM contact_table")
    fun getAlllContacts(): LiveData<List<Model>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(model: Model)


}
