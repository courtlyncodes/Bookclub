package com.example.bookedandbusy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bookedandbusy.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "BookReminderWorker"

// Worker to schedule book reminder
class ReadReminderWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val bookName = inputData.getString(nameKey)

        makeStatusNotification(applicationContext.resources.getString(R.string.it_s_time_to_read, bookName), applicationContext)
        return withContext(Dispatchers.IO) {
            try {
                Result.success()
            } catch (err: Throwable) {
                Log.e(TAG, "Error making book reminder", err)
                Result.failure()
            }
        }
    }

    companion object {
        const val nameKey = "NAME"
    }

}