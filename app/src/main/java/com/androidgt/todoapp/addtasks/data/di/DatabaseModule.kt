package com.androidgt.todoapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.androidgt.todoapp.addtasks.data.TaskDao
import com.androidgt.todoapp.addtasks.data.TodoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTaskDao(todoDatabase: TodoDataBase):TaskDao{
        return todoDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDataBase {
        //creamos la base de datos.
        return Room.databaseBuilder(appContext, TodoDataBase::class.java, "TaskDatabase").build()
    }
}