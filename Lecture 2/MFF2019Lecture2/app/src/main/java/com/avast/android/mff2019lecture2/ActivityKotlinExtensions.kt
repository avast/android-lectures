package com.avast.android.mff2019lecture2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin_extensions.*

class ActivityKotlinExtensions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_extensions)

        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_open_screen.setOnClickListener {
            val intent = Intent(this, DataReceiveActivity::class.java).apply {
                putExtra(DataReceiveActivity.KEY_FIRST_NAME, et_username.string())
                putExtra(DataReceiveActivity.KEY_LAST_NAME, et_surname.string())
                putExtra(DataReceiveActivity.KEY_PHONE_NUMBER, et_phone_number.string())
                putExtra(DataReceiveActivity.KEY_PASSWORD, et_password.string())
            }
            startActivity(intent)
        }
    }


    fun EditText.string() = text.toString().trim()

}