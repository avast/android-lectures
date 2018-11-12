package com.avast.helloworld

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_second.txt_second_activity

class SecondActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Log message to logcat
        Log.d("TAG", "onCreate of SecondActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Getting passed data from intent
        val intent = intent
        val text = intent.getStringExtra(KEY_TEXT)

        /**
          Thanks to the kotlin android extensions is possible to access views in layout directly
          without calling [findViewById]. The variable name is the same as the view id in layout XML.
        **/
        txt_second_activity.text = text
    }

    companion object {
        // Key for the passed data in intent
        val KEY_TEXT = "text"
    }
}