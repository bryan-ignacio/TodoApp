package com.androidgt.todoapp.addtasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TasksScreen(taskViewModel: TasksViewModel) {

    // accedemos a la variable showDialog desde la vista.
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)

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
        TasksList(taskViewModel)
    }
}

@Composable
fun TasksList(taskViewModel: TasksViewModel) {

}

@Composable
fun ItemTask() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = "Primera Tarea ", modifier = Modifier.padding(start = 12.dp))
            Checkbox(checked = true, onCheckedChange = {})
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
                    }, modifier = Modifier.fillMaxWidth(0.4f)) {
                        Text(text = "Agregar")
                    }
                }
            }
        }
    }
}