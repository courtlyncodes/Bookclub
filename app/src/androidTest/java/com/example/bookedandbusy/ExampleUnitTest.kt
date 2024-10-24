package com.example.bookedandbusy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.*
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.bookedandbusy.data.WorkManagerBookRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class WorkManagerBookRepositoryTest {

    private lateinit var context: Context
    private lateinit var workManager: WorkManager
    private lateinit var repository: WorkManagerBookRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        WorkManagerTestInitHelper.initializeTestWorkManager(context)

        workManager = WorkManager.getInstance(context)
        repository = WorkManagerBookRepository(context)
    }

    @Test
    fun scheduleReadReminder_schedulesCorrectWork() {
        // Arrange
        val duration = 1L
        val unit = TimeUnit.DAYS
        val bookName = "Test Book"

        // Act
        repository.scheduleReadReminder(duration, unit, bookName)

        // Assert
        workManager.getWorkInfosForUniqueWorkLiveData("$bookName$duration").value?.firstOrNull()?.id?.let {
            workManager.getWorkInfoByIdLiveData(
                it
            )
        }?.observeForever { info ->
            // Check if work is scheduled
            assertEquals(info?.state, WorkInfo.State.ENQUEUED)
        }
    }
}
