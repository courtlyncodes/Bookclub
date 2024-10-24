package com.example.bookedandbusy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.example.bookedandbusy.workers.ReadReminderWorker
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

class WorkerInstrumentationTest {
    private lateinit var context: Context

   @Before
   fun setUp() {
       context = ApplicationProvider.getApplicationContext()
   }

    @Test
    fun readReminderWorker_doWork_shouldReturnSuccess() {
        val worker = TestListenableWorkerBuilder<ReadReminderWorker>(context).build()
        runBlocking {
            val result = worker.doWork()
            assertTrue(result is ListenableWorker.Result.Success)
        }
    }
}