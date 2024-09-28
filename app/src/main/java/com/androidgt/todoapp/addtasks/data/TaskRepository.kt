package com.androidgt.todoapp.addtasks.data

import com.androidgt.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    //devuelve una lista de taskEntity lo que queremos es una lista de taskModel.
    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map {items -> items.map{ TaskModel(it.id, it.task, it.selected)}}

    //necesitamos el dao para hacer las consoltas a la database.
    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(taskModel.toData())
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toData())
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.delete(taskModel.toData())
    }

}


fun TaskModel.toData():TaskEntity{
    return TaskEntity(this.id, this.task, this.selected)
}