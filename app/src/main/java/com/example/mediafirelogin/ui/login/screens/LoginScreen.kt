package com.example.mediafirelogin.ui.login.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediafirelogin.R
import com.example.mediafirelogin.navigation.AppScreen
import com.example.mediafirelogin.ui.login.viewmodel.LoginViewModel
import com.example.mediafirelogin.ui.theme.BrilliantGray
import com.example.mediafirelogin.ui.theme.SkyBlue
import com.example.mediafirelogin.ui.theme.SoftBlue
import com.example.mediafirelogin.ui.theme.StrongBlue

/**
 * Pantalla principal de inicio de sesión que contiene los campos de entrada, botones
 * y opciones de inicio de sesión.
 *
 * @param loginViewModel [LoginViewModel] que gestiona el estado y la lógica de la pantalla.
 * @param navController [NavController] para gestionar la navegación entre pantallas.
 */
@Composable
fun LoginContent(loginViewModel: LoginViewModel, navController: NavController) {
    LoginScreen(loginViewModel, navController)
}
/**
 * Composable principal para el diseño de la pantalla de inicio de sesión.
 *
 * @param loginViewModel [LoginViewModel] para observar y manejar el estado de la pantalla.
 * @param navController [NavController] para la navegación.
 */
@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {

    val context = LocalContext.current

    // Estados del viewmodel
    val emailText by loginViewModel.email.observeAsState("")
    val passwordText by loginViewModel.password.observeAsState("")
    val isChecked by loginViewModel.isChecked.observeAsState(false)
    val isPasswordVisible by loginViewModel.isPasswordVisible.observeAsState(false)
    val isLoginEnabled by loginViewModel.loginEnabled.observeAsState(false)
    val isLoading by loginViewModel.isLoading.observeAsState(false)

    // Intents para manejar enlaces externos
    val logoutIntent = Intent(Intent.ACTION_VIEW, Uri.parse(LoginViewModel.GOOGLE_LOGOUT_URL))
    val registerIntent = Intent(Intent.ACTION_VIEW, Uri.parse(LoginViewModel.GOOGLE_REGISTER_URL))
    val twitterIntent = Intent(Intent.ACTION_VIEW, Uri.parse(LoginViewModel.TWITTER_LOGOUT_URL))

    // Contenedor principal de la pantalla
    Surface(
        color = Color.White
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            MediafireLogo()
            Spacer(modifier = Modifier.height(70.dp))
            EmailField(
                value = emailText,
                onChange = { loginViewModel.updateEmail(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(
                value = passwordText,
                onChange = { loginViewModel.updatePassword(it) },
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = isPasswordVisible,
                onClick = { loginViewModel.togglePasswordVisibility() }
            )
            RememberAndForgot(isChecked = isChecked, onCheckedChange = {loginViewModel.toggleKeepMeLoggedIn()})
            Spacer(modifier = Modifier.padding(13.dp))
            LoginButton(
                text = "LOG IN",
                onClick = {
                    loginViewModel.delayed {
                        navController.navigate(
                            route = AppScreen.SecondScreen.route + "/" + emailText + " " + passwordText
                        )
                    }
                },
                containerColor = SkyBlue,
                isEnabled = isLoginEnabled
            )
            Spacer(modifier = Modifier.padding(10.dp))
            LoginDivider()
            Spacer(modifier = Modifier.padding(10.dp))
            LoginButtonWithImage(
                text = "LOG IN WITH GOOGLE",
                onClick = {
                    loginViewModel.delayed {
                        context.startActivity(logoutIntent)
                    }
                },
                containerColor = StrongBlue,
                icon = painterResource(R.drawable.google_icon))
            Spacer(modifier = Modifier.padding(10.dp))
            LoginButtonWithImage(
                text = "LOG IN WITH X",
                onClick = {
                    loginViewModel.delayed {
                        context.startActivity(twitterIntent)
                    }
                },
                containerColor = StrongBlue,
                icon = painterResource(R.drawable.x_icon))
            Spacer(modifier = Modifier.padding(32.dp))
            CreateAccount(
                onLinkClick = {
                    loginViewModel.delayed {
                        context.startActivity(registerIntent)
                    }
                }
            )
        }

    }

    // Indicador de carga
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

/**
 * Muestra el logotipo de Mediafire en la parte superior de la pantalla.
 */
@Composable
fun MediafireLogo() {
    Image(
        painter = painterResource(id = R.drawable.mediafire_wordmark),
        contentDescription = "Mediafire Logo",
        modifier = Modifier.fillMaxWidth().scale(0.75f).padding(end = 20.dp)
    )
}

/**
 * Campo de entrada de texto para el correo electrónico.
 *
 * @param value El texto actual en el campo.
 * @param onChange Acción a ejecutar al cambiar el texto.
 * @param modifier Modificadores aplicados al campo.
 * @param placeholder Texto de marcador de posición.
 */
@OptIn(ExperimentalMaterial3Api::class) // Para poder usar textFieldColors
@Composable
fun EmailField(value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier, placeholder: String = "Email Address") {

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = colorResource(R.color.black),
            unfocusedTextColor = Color.Black,
            containerColor = Color.White,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            focusedIndicatorColor = SkyBlue,
            cursorColor = SkyBlue,
            selectionColors = TextSelectionColors(
                handleColor = SkyBlue,
                backgroundColor = SoftBlue
            )
        )
    )

}

/**
 * Campo de entrada de texto para la contraseña con opción de visibilidad.
 *
 * @param value El texto actual en el campo.
 * @param onChange Acción a ejecutar al cambiar el texto.
 * @param modifier Modificadores aplicados al campo.
 * @param placeholder Texto de marcador de posición.
 * @param onClick Acción a ejecutar al cambiar la visibilidad de la contraseña.
 * @param isPasswordVisible Estado que indica si la contraseña es visible.
 */
@OptIn(ExperimentalMaterial3Api::class) // Para poder usar textFieldColors
@Composable
fun PasswordField(value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier, placeholder: String = "Password", onClick: () -> Unit, isPasswordVisible: Boolean) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = colorResource(R.color.black),
            unfocusedTextColor = Color.Black,
            containerColor = Color.White,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            focusedIndicatorColor = SkyBlue,
            cursorColor = SkyBlue,
            selectionColors = TextSelectionColors(
                handleColor = SkyBlue,
                backgroundColor = SoftBlue
            )
        ),
        trailingIcon = {
            val image = if (isPasswordVisible) painterResource(R.drawable.visibility_off) else painterResource(R.drawable.visibility_on)
            val description = if (isPasswordVisible) "Contraseña Oculta" else "Contraseña visible"
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    painter = image,
                    contentDescription = description
                )
            }
        }
    )
}

/**
 * Fila que contiene la opción de mantener sesión iniciada y un enlace a "Olvidó su contraseña".
 *
 * @param isChecked Estado de la casilla de "Mantener sesión iniciada".
 * @param onCheckedChange Acción a ejecutar al cambiar el estado de la casilla.
 */
@Composable
fun RememberAndForgot(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top= 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        KeepMeLoggedIn(isChecked, onCheckedChange)
        ForgotPassword()
    }
}

/**
 * Componente para la casilla de verificación "Mantener sesión iniciada".
 *
 * @param isChecked Estado de la casilla.
 * @param onCheckedChange Acción a ejecutar al cambiar el estado.
 */
@Composable
fun KeepMeLoggedIn(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.scale(0.8f)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = true,
                colors = CheckboxDefaults.colors(
                    checkedColor = SkyBlue,
                    uncheckedColor = Color.Gray
                )
            )
        }
        Text(
            text = "Keep me logged in",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.offset(x = (-12).dp)
        )
    }
}

/**
 * Texto que representa un enlace a "¿Olvidaste tu contraseña?".
 */
@Composable
fun ForgotPassword() {
    Text(
        text = "Forgot Password?",
        color = SkyBlue,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Botón reutilizable para acciones de inicio de sesión.
 *
 * @param text Texto a mostrar en el botón.
 * @param onClick Acción a ejecutar al presionar el botón.
 * @param contentColor Color del texto del botón.
 * @param containerColor Color de fondo del botón.
 * @param isEnabled Estado habilitado/deshabilitado del botón.
 */
@Composable
fun LoginButton(text: String, onClick: () -> Unit, contentColor: Color = Color.White, containerColor: Color, isEnabled: Boolean) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = containerColor,
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled
    ) {
        Text(text = text)
    }
}

/**
 * Botón con imagen reutilizable para acciones de inicio de sesión con terceros.
 *
 * @param text Texto a mostrar en el botón.
 * @param onClick Acción a ejecutar al presionar el botón.
 * @param containerColor Color de fondo del botón.
 * @param icon Imagen a mostrar junto al texto.
 */
@Composable
fun LoginButtonWithImage(text: String, onClick: () -> Unit, containerColor: Color, icon: Painter) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = containerColor,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
            )
            // Texto centrado
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * Separador horizontal con texto "OR" para dividir las opciones de inicio de sesión.
 */
@Composable
fun LoginDivider() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .background(Color.White)
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
        HorizontalDivider(
            modifier = Modifier
                .background(Color.White)
                .height(1.dp)
                .weight(1f)
        )
    }
}

/**
 * Enlace que permite crear una cuenta nueva.
 *
 * @param onLinkClick Acción a ejecutar al presionar el enlace.
 */
@Composable
fun CreateAccount(onLinkClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .background(color = BrilliantGray, shape = RoundedCornerShape(0.dp))
            .padding(vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Create an account",
                fontSize = 12.sp,
                color = SkyBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onLinkClick() }
            )
        }
    }
}
