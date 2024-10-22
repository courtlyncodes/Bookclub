package com.example.bookedandbusy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookedandbusy.BookedAndBusyApplication
import com.example.bookedandbusy.data.BookRepository
import com.example.bookedandbusy.data.Reminder

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    internal val books = bookRepository.books

    fun scheduleReadReminder(reminder: Reminder) {
        bookRepository.scheduleReadReminder(reminder.duration, reminder.unit, reminder.bookName)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val bookRepository =
                    (this[APPLICATION_KEY] as BookedAndBusyApplication).container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}