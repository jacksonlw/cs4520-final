package com.cs4520.brainflex

import androidx.work.Configuration
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkManagerTest {

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setExecutor(SynchronousExecutor()) // Executes work synchronously for testing
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }
}