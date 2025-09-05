package com.hiutin.jetpackcomposetodo.ui.features.task.add_task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiutin.jetpackcomposetodo.domain.models.SubTask
import com.hiutin.jetpackcomposetodo.domain.models.Task
import com.hiutin.jetpackcomposetodo.domain.repositories.ReminderScheduler
import com.hiutin.jetpackcomposetodo.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddTaskState>(AddTaskState.Idle)
    val uiState: StateFlow<AddTaskState> = _uiState.asStateFlow()

    var taskText by mutableStateOf(TextFieldValue())
        private set
    var date by mutableStateOf<LocalDate?>(null)
        private set
    var time by mutableStateOf<LocalTime?>(null)
        private set
    var needRemind by mutableStateOf(false)

    private val _subtasks = MutableStateFlow<List<SubTask>>(emptyList())
    val subtasks: StateFlow<List<SubTask>> = _subtasks

    fun onTextChange(newText: TextFieldValue) {
        taskText = newText
    }

    fun setDateTime(newDate: LocalDate?, newTime: LocalTime?) {
        date = newDate
        time = newTime
    }

    fun changeNeedRemind(value: Boolean) {
        needRemind = value
    }

    fun addSubTask(subtaskContent: String) {
        val id = UUID.randomUUID().toString()
        val newSubTask = SubTask(id, subtaskContent)
        _subtasks.value = _subtasks.value + newSubTask
    }


    fun saveTask() {
        if (_uiState.value is AddTaskState.Saving) return

        if (taskText.text.isBlank()) {
            AddTaskState.Error("Task cannot be empty").also { _uiState.value = it }
            return
        }

        viewModelScope.launch {
            _uiState.value = AddTaskState.Saving
            try {
                val id = UUID.randomUUID().toString()
                val newTask = Task(
                    id,
                    taskText.text,
                    description = "",
                    date,
                    time,
                    _subtasks.value
                )
                if (date != null && time != null && needRemind) {
                    reminderScheduler.schedule(newTask)
                }
                repository.saveTask(
                    newTask
                )
                _uiState.value = AddTaskState.Success
            } catch (e: Exception) {
                _uiState.value = AddTaskState.Error("Failed to save task: ${e.message}")
                Log.e("AddTaskViewModel", "saveTaskError: $e")
            }
        }
    }

    fun resetState() {
        _uiState.value = AddTaskState.Idle
    }
}