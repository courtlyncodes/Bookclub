package com.example.bookedandbusy.data

import com.example.bookedandbusy.model.Book
import java.util.concurrent.TimeUnit

interface BookRepository {
    fun scheduleReadReminder(duration: Long, unit: TimeUnit, bookName: String)
    val books: List<Book>
}