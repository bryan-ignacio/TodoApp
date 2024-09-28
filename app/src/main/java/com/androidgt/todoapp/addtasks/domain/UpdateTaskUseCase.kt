package com.androidgt.todoapp.addtasks.domain

import com.androidgt.todoapp.addtasks.data.TaskRepository
import com.androidgt.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.update(taskModel)
    }

}