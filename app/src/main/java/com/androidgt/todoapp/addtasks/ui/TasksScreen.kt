package com.androidgt.todoapp.addtasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.androidgt.todoapp.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(taskViewModel: TasksViewModel) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // accedemos a la variable showDialog desde la vista.
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)

    val uiState by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            taskViewModel.uiState.collect{value = it}
        }
    }

    when (uiState){
        is TasksUiState.Error -> {}
        TasksUiState.Loading -> {
            CircularProgressIndicator()
        }
        is TasksUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .background(color = Color.LightGray)
            )
            {
                AddTaskDialog(
                    show = showDialog,
                    onDismiss = { taskViewModel.onDialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) })
                FabDialog(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp), taskViewModel
                )
                TasksList((uiState as TasksUiState.Success).tasks, taskViewModel)
            }
        }
    }


}

@Composable
fun TasksList(tasks: List<TaskModel>, taskViewModel: TasksViewModel) {

    //declaramos una lista de tareas.
//    val myTasks:List<TaskModel> = taskViewModel.tasks
    LazyColumn {
        items(tasks, key = {it.id}){
            ItemTask(it, taskViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TasksViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp).pointerInput(Unit){
                //podemos controlar varios tipos de clicks.
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = taskModel.task, modifier = Modifier.padding(start = 12.dp))
            Checkbox(checked = taskModel.selected, onCheckedChange = {
                taskViewModel.onCheckBoxSelected(taskModel)
            })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TasksViewModel) {
    FloatingActionButton(onClick = {
        taskViewModel.onShowDialogClick()
    }, modifier = modifier) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTaskDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onTaskAdded: (String) -> Unit
) {

    var myTask by remember { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {

            Surface(
                shape = RoundedCornerShape(10.dp), // Esquinas redondeadas
                color = Color.White,
                tonalElevation = 4.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Agrega una tarea",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(value = myTask, onValueChange = { myTask = it }, singleLine = true)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        onTaskAdded(myTask)
                        myTask = ""
                    }, modifier = Modifier.fillMaxWidth(0.4f)) {
                        Text(text = "Agregar")
                    }
                }
            }
        }
    }
}