package com.kundalik.todoapp.data.reoporitory

import androidx.lifecycle.LiveData
import com.kundalik.todoapp.data.ToDoDao
import com.kundalik.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

}