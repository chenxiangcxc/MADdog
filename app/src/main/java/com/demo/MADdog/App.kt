package com.demo.MADdog

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private var context: Application? = null

        fun getContext(): Context {
            return context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}