package com.androidgt.todoapp.addtasks.data

import androidx.room.PrimaryKey

data class TaskEntity(
    @PrimaryKey
    var id: Int,
    var task: String,
    var selected: Boolean = false
    )

