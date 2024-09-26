package com.androidgt.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidgt.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() : ViewModel() {


    //esta es la variable que se modifica desde la vista
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    // listado de tareas
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    // esta funcion cierra el dialog
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        // al momento de crear la tarea se oculta el dialog
        _showDialog.value = false
//        Log.i("Task", "Se creo la tarea $task")
        _tasks.add(TaskModel(task = task))
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        // buscamos el index.
        val index = _tasks.indexOf(taskModel)
        // el elemento con el indice encontrado se actualiza con la funcion copy y cambiamos el valor de selected.
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        // eliminamos la tarea
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)

    }
}