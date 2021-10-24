package com.demo.MADdog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingRegistry
import com.demo.MADdog.view.FragmentDogList
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UITest {

    lateinit var idlingResource: IdlingResource
    lateinit var scenaro: FragmentScenario<FragmentDogList>

    @Before
    fun setUp() {
        // Importance: can not use launchFragment here!
        scenaro = launchFragmentInContainer<FragmentDogList>().onFragment {
            val tv = it.view!!.findViewById<TextView>(R.id.dog_count)
            val initStr = it.getString(R.string.loading)
            idlingResource = MainIdlingResource(tv, initStr)
        }

        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test
    fun test_DogCount() {
        onView(withId(R.id.dog_count)).check(matches(withText("Dog count: 23")))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
