package com.androidgt.todoapp.addtasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor(): ViewModel() {


    //esta es la variable que se modifica desde la vista
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    // esta funcion cierra el dialog
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        // al momento de crear la tarea se oculta el dialog
        _showDialog.value = false
        Log.i("Task", "Se creo la tarea $task")
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }
}