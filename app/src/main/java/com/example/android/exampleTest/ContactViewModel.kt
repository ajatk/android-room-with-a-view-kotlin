package com.example.android.exampleTest

import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepoRepository
    val allContacts: LiveData<List<Model>>
    init {
        val contactDao = ContactRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = MainRepoRepository(contactDao)
        allContacts = repository.allContacts
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(model: Model) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(model)
    }

    fun getContactList() = viewModelScope.launch(Dispatchers.IO) {
        val cr = getApplication<Application>().contentResolver
        val cur = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
        )
        if (cur?.count ?: 0 > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name = cur.getString(
                        cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME
                        )
                )
                if (cur.getInt(
                                cur.getColumnIndex(
                                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                                )
                        ) > 0
                ) {
                    val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                    )
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur.getString(
                                pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                        )

                        val model = Model(name = name, number = phoneNo)
                        insert(model)

                    }
                    pCur.close()
                }
            }
        }
        cur?.close()


    }
}
