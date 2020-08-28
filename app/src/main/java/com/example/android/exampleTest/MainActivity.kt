package com.example.android.exampleTest

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
      //  contactViewModel = ViewModelProvider(this).get()

        requestContactPermission()
    }


    private fun setAllData() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MainAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        contactViewModel.allContacts.observe(this, Observer { contact ->
            contact?.let { adapter.setWords(it) }
            if (contact.isNullOrEmpty())
                contactViewModel.getContactList()
        })
    }

    fun requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.READ_CONTACTS)) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Read Contacts permission")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Please enable access to contacts.")
                    builder.setOnDismissListener({ requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_PERMISSIONS_REQUEST_CODE) })
                    builder.show()
                } else {
                    requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),
                            REQUEST_PERMISSIONS_REQUEST_CODE)
                }
            } else {
                setAllData()
            }
        } else {
            setAllData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.size > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setAllData()
                } else {
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}


