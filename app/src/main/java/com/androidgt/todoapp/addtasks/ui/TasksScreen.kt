package com.androidgt.todoapp.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TasksScreen(taskViewModel: TasksViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    )
    {
        FabDialog(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp))

    }
}

@Composable
fun FabDialog(modifier: Modifier) {
    FloatingActionButton(onClick = {}, modifier = modifier) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTaskDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {

    var myTask by remember { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Agrega tu tarea")
                TextField(value = myTask, onValueChange = {})
            }
        }
    }
}