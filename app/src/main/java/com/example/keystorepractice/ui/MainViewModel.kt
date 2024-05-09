package com.example.keystorepractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keystorepractice.domain.AuthRepository
import com.example.keystorepractice.domain.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
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

    fun secureSignIn(id: String, pw: String) {
        viewModelScope.launch(exceptionHandler) {
            _token.update { repository.secureSignIn(id, pw) }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            preferenceRepository.saveToken(token)
        }
    }
}