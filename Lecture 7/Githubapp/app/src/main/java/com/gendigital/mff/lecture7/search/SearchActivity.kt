package com.gendigital.mff.lecture7.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gendigital.mff.lecture7.R

class SearchActivity : AppCompatActivity() {

    companion object {
        private val TAG = SearchActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: $this")
        setContentView(R.layout.activity_search)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: $this")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: $this")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: $this")
    }
}