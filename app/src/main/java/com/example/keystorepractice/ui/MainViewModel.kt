package com.example.keystorepractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keystorepractice.data.repository.AuthRepositoryImpl
import com.example.keystorepractice.domain.AuthRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl(),
) : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            _token.update { null }
        }

    fun signIn(id: String, pw: String) {
        viewModelScope.launch(exceptionHandler) {
            _token.update { repository.signIn(id, pw) }
        }
    }
}