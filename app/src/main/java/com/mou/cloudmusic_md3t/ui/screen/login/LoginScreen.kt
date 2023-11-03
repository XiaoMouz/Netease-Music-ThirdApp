package com.mou.cloudmusic_md3t.ui.screen.login

import android.os.CountDownTimer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ScheduleSend
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mou.cloudmusic_md3t.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(onLoginSuccess: ()->Unit, modifier: Modifier = Modifier) {
    LoginForm(modifier.fillMaxWidth())
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    smsSend:()->Unit = {},
    loginButtonClick:()->Unit = {},
    onLoginSuccess: ()->Unit = {},
    allowGuestLogin:Boolean = true,
    guestLogin: ()->Unit = {}
    ){
    var loginInput by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    var passwordInputHidden by rememberSaveable { mutableStateOf(false) }
    var useSMSLogin by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()) {
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = loginInput,
                    onValueChange = { it: String -> loginInput = it },
                    label = { Text(text = stringResource(id = R.string.login_input)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { it: String -> passwordInput = it },
                    label = { Text(text = stringResource(id = R.string.password_input)) },
                    visualTransformation =
                    if (passwordInputHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { passwordInputHidden = !passwordInputHidden }) {
                            val visibilityIcon =
                                if (passwordInputHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordInputHidden) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp), verticalAlignment = Alignment.Bottom
            ) {
                ElevatedFilterChip(
                    selected = useSMSLogin,
                    onClick = { useSMSLogin = !useSMSLogin },
                    label = { Text(stringResource(id = R.string.use_sms_code)) },
                    leadingIcon = {
                        AnimatedVisibility(
                            visible = !useSMSLogin,
                            enter = expandVertically(expandFrom = Alignment.Top) +
                                    fadeIn(initialAlpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                        AnimatedVisibility(
                            visible = useSMSLogin,
                            enter = expandVertically(expandFrom = Alignment.Top) +
                                    fadeIn(initialAlpha = 0.15f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ScheduleSend,
                                contentDescription = "",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    },
                    modifier = Modifier.animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FilledTonalButton(
                        onClick = { /*TODO*/ },

                        ) {
                        Icon(imageVector = Icons.Default.Face, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = R.string.guest_login))
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = R.string.login))
                    }
                }
            }
        }
    }
}

fun startCountdown(sec: Int, onTick: (millis: Long) -> Unit, onFinish: () -> Unit) {
    val timer = object : CountDownTimer((sec*1000).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            onTick(millisUntilFinished)
        }
        override fun onFinish() {
            onFinish()
        }
    }
    timer.start()
}


@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun LoginFormPreview(){
    LoginForm()
}