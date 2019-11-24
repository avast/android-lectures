package com.avast.android.mff2019lecture2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data_receive.*

class DataReceiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_receive)
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.let {
            txt_first_name_value.text = it.getStringExtra(KEY_FIRST_NAME).orEmpty()
            txt_last_name_value.text = it.getStringExtra(KEY_LAST_NAME).orEmpty()
            txt_phone_number_value.text = it.getStringExtra(KEY_PHONE_NUMBER).orEmpty()
            txt_password_value.text = it.getStringExtra(KEY_PASSWORD).orEmpty()
        }
    }

    companion object {
        const val KEY_FIRST_NAME = "firstname"
        const val KEY_LAST_NAME = "lastname"
        const val KEY_PHONE_NUMBER = "phone_number"
        const val KEY_PASSWORD = "password"
    }
}
