package com.kundalik.todoapp.data.reoporitory

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.kundalik.todoapp.data.ToDoDao
import com.kundalik.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }
}