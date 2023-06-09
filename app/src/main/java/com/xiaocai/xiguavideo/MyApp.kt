package com.xiaocai.xiguavideo

import android.app.Application
import kotlin.random.Random

class MyApp : Application() {

    companion object {
        val ttwid = Random.nextInt(0, Int.MAX_VALUE)
    }

    override fun onCreate() {
        super.onCreate()
    }
}