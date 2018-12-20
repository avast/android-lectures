package com.avast.android.lecture.github.login

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.avast.android.lecture.github.App
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.user.UserActivity
import com.avast.android.lecture.github.user.UserFragment

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment() {

    lateinit var txtLogin: EditText
    lateinit var btnLogin: Button

    /**
     * Inflate view hierarchy.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, true)
    }

    /**
     * Setup view actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txtLogin = view.findViewById(R.id.txt_username)
        btnLogin = view.findViewById(R.id.btn_search)
        btnLogin.setOnClickListener {
            val login = txtLogin.text.toString()
            val intent = Intent(activity, UserActivity::class.java)
            intent.putExtra(UserFragment.KEY_USERNAME, login)

            fireNotification(login)

            startActivity(intent)
        }
    }

    /**
     * Fire notification.
     */
    private fun fireNotification(login: String) {
        val intent = Intent(activity, UserActivity::class.java).apply {
            putExtra(UserFragment.KEY_USERNAME, login)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        activity?.let {
            val pendingIntent = TaskStackBuilder.create(it).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val notification = NotificationCompat.Builder(it, App.NOTIFICATION_CHANNEL_MAIN_ID)
                    .setSmallIcon(R.drawable.leak_canary_notification)
                    // Check the [notification_hello_world_title] string resource how formatting in resources is done.
                    .setContentTitle(it.resources.getString(R.string.notification_hello_world_title, login))
                    .setContentText(it.resources.getString(R.string.notification_hello_world_content))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

            NotificationManagerCompat.from(it).notify(App.NOTIFICATION_HELLO_WORLD_ID, notification)
        }
    }
}