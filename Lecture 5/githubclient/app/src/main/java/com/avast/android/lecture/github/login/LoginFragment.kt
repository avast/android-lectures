package com.avast.android.lecture.github.login

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.Fragment
import com.avast.android.lecture.github.App
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.Settings
import com.avast.android.lecture.github.user.UserActivity
import com.avast.android.lecture.github.user.UserFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment() {

    private val settings: Settings by lazy {
        Settings.getInstance(requireContext().applicationContext)
    }

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
        txt_username.setText(settings.getLastUsername())

        btn_search.setOnClickListener {
            val login = txt_username.text.toString()
            settings.setLastUsername(login)
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
        val notificationIntent = Intent(requireContext(), UserActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(UserFragment.KEY_USERNAME, login)
        }

        val notificationPendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(notificationIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_MAIN_ID)
            .setSmallIcon(R.drawable.ic_done)
            .setContentTitle(getString(R.string.notification_hello_world_title, login))
            .setContentText(getString(R.string.notification_hello_world_content))
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(requireContext())) {
            notify(App.NOTIFICATION_HELLO_WORLD_ID, notification)
        }
    }
}