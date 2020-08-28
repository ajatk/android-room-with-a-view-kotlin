package com.example.android.exampleTest

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData



class MainRepoRepository(private val contactDao: ContactDao) {

    val allContacts: LiveData<List<Model>> = contactDao.getAlllContacts()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(model: Model) {
        contactDao.insert(model)
    }
}
