package com.example.bookedandbusy.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bookedandbusy.model.Book
import com.example.bookedandbusy.workers.ReadReminderWorker
import java.util.concurrent.TimeUnit

class WorkManagerBookRepository(context: Context) : BookRepository {

    private val workManager = WorkManager.getInstance(context)

    override val books: List<Book>
        get() = BookDataSource.books

    override fun scheduleReadReminder(duration: Long, unit: TimeUnit, bookName: String) {

        val data = Data.Builder()
        data.putString(ReadReminderWorker.nameKey, bookName)

        val workRequestBuilder = OneTimeWorkRequestBuilder<ReadReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(bookName + duration, ExistingWorkPolicy.REPLACE, workRequestBuilder)
    }
}