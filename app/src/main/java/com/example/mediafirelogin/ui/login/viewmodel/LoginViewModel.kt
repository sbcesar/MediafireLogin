package com.example.mediafirelogin.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    // Constantes de las Urls
    companion object {
        const val GOOGLE_LOGOUT_URL = "https://accounts.google.com/Logout"
        const val GOOGLE_REGISTER_URL = "https://accounts.google.com/signup"
        const val TWITTER_LOGOUT_URL = "https://x.com/i/flow/login"
    }

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean> = _isChecked

    private val _isPasswordVisible = MutableLiveData(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    private val _loginEnabled = MutableLiveData(false)
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        validateLogin()
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validateLogin()
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    fun toggleKeepMeLoggedIn() {
        _isChecked.value = !(_isChecked.value ?: false)
    }

    private fun validateLogin() {
        val emailValue = _email.value ?: ""
        val passwordValue = _password.value ?: ""
        _loginEnabled.value = isValidEmail(emailValue) && isValidPassword(passwordValue)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    suspend fun delayed() {
        isLoading.value = true
        delay(4000)
        isLoading.value = false
    }
}