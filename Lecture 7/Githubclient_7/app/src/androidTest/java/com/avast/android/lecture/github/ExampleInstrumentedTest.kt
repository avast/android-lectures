package com.avast.android.lecture.github

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.hasSibling
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject2
import android.support.test.uiautomator.Until
import com.avast.android.lecture.github.login.LoginActivity
import com.avast.android.lecture.github.user.RepositoryAdapter
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.startsWith
import org.junit.After
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val invalidUsername = "test:invalid-user"
    private val validUsername = "square"

    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @After
    fun tearDown() {
        if (isNotificationDrawerOpened()) uiDevice.pressBack()
    }

    /* TESTS */

    @Test
    fun verifyAppIsLaunched() {
        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyInvalidLoginShowsErrorMessage() {

        onView(withId(R.id.txt_status)).check(matches(not(isDisplayed())))
        onView(withId(R.id.txt_username)).check(matches(isDisplayed()))

        // search empty string
        onView(withId(R.id.btn_search)).check(matches(isDisplayed())).perform(click())

        // error shown, input field cleared
        onView(withId(R.id.txt_status)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_username)).check(matches(withText("")))

        // fill in invalid user text and search it
        onView(withId(R.id.txt_username)).perform(typeText(invalidUsername))
        onView(withId(R.id.btn_search)).perform(click())

        // error shown, input field cleared
        onView(withId(R.id.txt_status)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_username)).check(matches(withText("")))
    }

    @Test
    fun verifyValidUserRepoInfo() {

        onView(withId(R.id.txt_username)).check(matches(isDisplayed())).perform(typeText(validUsername))
        onView(withId(R.id.btn_search)).check(matches(isDisplayed())).perform(click())

        // verify user fragment is shown
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))

        // verify user data
        onView(withId(R.id.txt_username_value)).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText(validUsername)
                )
            )
        )

        onView(withId(R.id.txt_url_value)).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("https://api.github.com/users/$validUsername")
                )
            )
        )

        onView(withId(R.id.rv_repositories)).perform(scrollToPosition<RepositoryAdapter.RepositoryViewHolder>(12))

        onView(
            allOf(
                withId(R.id.txt_repository_name),
                withText("$validUsername/async_task")
            )
        ).perform().check(
            matches(
                allOf(
                    isDisplayed(),
                    hasSibling(
                        allOf(
                            withId(R.id.txt_repository_description),
                            withText(startsWith("Lightweight, asynchronous, and database-backed"))
                        )
                    )
                )
            )
        )
    }

    @Test
    fun verifyNotificationShown() {
        onView(withId(R.id.txt_username)).check(matches(isDisplayed())).perform(typeText(validUsername))
        onView(withId(R.id.btn_search)).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))

        // switch to UiAutomator
        uiDevice.openNotification()

        val notificationContainer: UiObject2? = uiDevice.wait(Until.findObject(
            By.res("com.android.systemui:id/notification_stack_scroller")), TimeUnit.SECONDS.toMillis(10))
        assertNotNull("Notification stack scroller is not visible!", notificationContainer)

        val notificationTitle: UiObject2? = notificationContainer?.findObject(
            By.res("android:id/app_name_text").text("Github client"))
        assertNotNull("Notification with app title was not found!", notificationTitle)

        val notificationContentRoot: UiObject2? = notificationTitle?.parent?.parent
        assertNotNull("Notification content root was not found!", notificationContentRoot)

        val notificationContentTitle = notificationContentRoot?.findObject(By.res("android:id/title"))
        assertNotNull("Notification content title was not found!", notificationContentTitle)
        assertEquals("Notification content title does not match!",
            "Login $validUsername", notificationContentTitle?.text)

        val notificationContentText = notificationContentRoot?.findObject(By.res("android:id/text"))
        assertNotNull("Notification content text was not found!", notificationContentText)
        assertEquals("Notification content text does not match!", "Hello world", notificationContentText?.text)
    }

    /* PRIVATE HELPER METHODS */

    private fun isNotificationDrawerOpened(): Boolean {
        val notificationContainer: UiObject2? = uiDevice.wait(Until.findObject(
            By.res("com.android.systemui:id/notification_stack_scroller")), 5_000)
        return notificationContainer != null
    }
}
