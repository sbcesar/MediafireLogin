package com.example.mediafirelogin.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona la lógica de negocio y el estado para la pantalla de inicio de sesión.
 *
 * Contiene las propiedades observables para los datos de entrada del usuario,
 * el estado de visibilidad de la contraseña, y el estado de habilitación del botón de inicio de sesión.
 * También incluye constantes para URLs utilizadas en las acciones de autenticación con terceros.
 */
class LoginViewModel : ViewModel() {

    // Constantes de las Urls
    companion object {
        /** URL para cerrar sesión en Google. */
        const val GOOGLE_LOGOUT_URL = "https://accounts.google.com/Logout"

        /** URL para registrarse en Google. */
        const val GOOGLE_REGISTER_URL = "https://accounts.google.com/signup"

        /** URL para iniciar sesión en Twitter (X). */
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

    /**
     * Actualiza el correo electrónico ingresado por el usuario.
     *
     * @param newEmail El nuevo valor del campo de correo electrónico.
     */
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        validateLogin()
    }

    /**
     * Actualiza la contraseña ingresada por el usuario.
     *
     * @param newPassword El nuevo valor del campo de contraseña.
     */
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validateLogin()
    }

    /**
     * Alterna la visibilidad de la contraseña entre visible y oculta.
     */
    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    /**
     * Alterna el estado de la casilla "Keep Me Logged In".
     */
    fun toggleKeepMeLoggedIn() {
        _isChecked.value = !(_isChecked.value ?: false)
    }

    /**
     * Valida los datos ingresados (correo electrónico y contraseña)
     * y actualiza el estado de habilitación del botón de inicio de sesión.
     */
    private fun validateLogin() {
        val emailValue = _email.value ?: ""
        val passwordValue = _password.value ?: ""
        _loginEnabled.value = isValidEmail(emailValue) && isValidPassword(passwordValue)
    }

    /**
     * Verifica si un correo electrónico es válido.
     *
     * @param email El correo electrónico a validar.
     * @return `true` si el correo electrónico tiene un formato válido, de lo contrario `false`.
     */
    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * Verifica si una contraseña es válida.
     *
     * @param password La contraseña a validar.
     * @return `true` si la contraseña tiene más de 6 caracteres, de lo contrario `false`.
     */
    private fun isValidPassword(password: String): Boolean = password.length > 6

    /**
     * Ejecuta una acción después de un retraso y actualiza el estado de carga mientras tanto.
     *
     * @param action Acción a ejecutar después del retraso.
     */
    fun delayed(action: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            delay(4000) // 4 segundos
            isLoading.value = false
            action()
        }
    }
}