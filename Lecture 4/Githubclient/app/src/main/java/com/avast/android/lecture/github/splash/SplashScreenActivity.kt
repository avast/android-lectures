package com.avast.android.lecture.github.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Simple splash screen with timeout, which shows how to use [Handler].
 */
class SplashScreenActivity: AppCompatActivity() {

    val mainHandler = Handler(Looper.getMainLooper(), Handler.Callback { msg: Message ->
        txt_timer.text = msg.arg1.toString()
        return@Callback true
    })

    lateinit var thread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        thread = object: Thread() {
            override fun run() {
                var i = TIMER_DELAY_COUNT
                do {
                    try {
                        Thread.sleep(1000L)
                    } catch (exception: InterruptedException) {
                        Log.e(this.javaClass.simpleName, "Thread is interrupted")
                        return
                    }
                    val message = mainHandler.obtainMessage()
                    message.arg1 = i
                    mainHandler.sendMessage(message)
                    i--
                } while(i != 0)

                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }

        }
        thread.start()

        btn_show_toast.setOnClickListener {
            Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val TIMER_DELAY_COUNT = 5
    }
}