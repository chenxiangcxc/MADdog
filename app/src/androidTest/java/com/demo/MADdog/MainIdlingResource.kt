package com.demo.MADdog

import android.widget.TextView
import androidx.test.espresso.IdlingResource

class MainIdlingResource(private val tvResult: TextView,
                         private val idleValue: String) : IdlingResource {
    private var mCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return "MainIdlingResource"
    }

    override fun isIdleNow(): Boolean {
        return if (mCallback != null) {

            if (!tvResult.text.equals(idleValue)) {
                mCallback!!.onTransitionToIdle()  // This step is important!
                true
            } else {
                false
            }
        } else {
            true
        }
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        mCallback = callback
    }
}