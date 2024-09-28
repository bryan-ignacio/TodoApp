package com.androidgt.todoapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidgt.todoapp.addtasks.domain.AddTaskUseCase
import com.androidgt.todoapp.addtasks.domain.GetTasksUseCase
import com.androidgt.todoapp.addtasks.domain.UpdateTaskUseCase
import com.androidgt.todoapp.addtasks.ui.TasksUiState.Success
import com.androidgt.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> =
        getTasksUseCase().map(::Success).catch { TasksUiState.Error(it) }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            TasksUiState.Loading
        )


    //esta es la variable que se modifica desde la vista
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    // listado de tareas
//    private val _tasks = mutableStateListOf<TaskModel>()
//    val tasks: List<TaskModel> = _tasks

    // esta funcion cierra el dialog
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        // al momento de crear la tarea se oculta el dialog
        _showDialog.value = false
//        Log.i("Task", "Se creo la tarea $task")
//        _tasks.add(TaskModel(task = task))
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        // buscamos el index.
//        val index = _tasks.indexOf(taskModel)
//        // el elemento con el indice encontrado se actualiza con la funcion copy y cambiamos el valor de selected.
//        _tasks[index] = _tasks[index].let {
//            it.copy(selected = !it.selected)
//        }
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        // eliminamos la tarea
//        val task = _tasks.find { it.id == taskModel.id }
//        _tasks.remove(task)

    }
}