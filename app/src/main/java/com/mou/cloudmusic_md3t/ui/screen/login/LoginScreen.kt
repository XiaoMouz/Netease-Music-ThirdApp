package com.mou.cloudmusic_md3t.ui.screen.login

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
import androidx.compose.material.icons.filled.AccessibleForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mou.cloudmusic_md3t.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    allowGuestLogin: Boolean = true,
) {
    val loginViewModel: LoginViewModel = viewModel()
    val context = LocalContext.current
    var passwordInputHidden by rememberSaveable { mutableStateOf(true) }
    val loginState = loginViewModel.loginState.collectAsState().value
    val inputState = loginViewModel.inputState.collectAsState().value
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = modifier
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = inputState.username,
                    onValueChange = { loginViewModel.handleIntent(LoginIntent.InputUsernameState(it)) },
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
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (loginState.loginMethod == LoginMethod.SMS) {
                    OutlinedTextField(
                        value = inputState.password,
                        onValueChange = { loginViewModel.handleIntent(LoginIntent.InputPasswordState(it)) },
                        label = { Text(text = stringResource(id = R.string.sms_string)) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Mail,
                                contentDescription = null
                            )
                        },
                        modifier = modifier.fillMaxWidth()
                    )
                } else {
                    OutlinedTextField(
                        value = inputState.password,
                        onValueChange = { loginViewModel.handleIntent(LoginIntent.InputPasswordState(it)) },
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

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                ElevatedFilterChip(
                    selected = loginState.loginMethod == LoginMethod.SMS,
                    onClick =
                    {
                        if(loginState.loginMethod == LoginMethod.SMS){
                            loginViewModel.handleIntent(LoginIntent.ChangeLoginMethod(LoginMethod.PASSWORD))
                        }else{
                            loginViewModel.handleIntent(LoginIntent.ChangeLoginMethod(LoginMethod.SMS))
                        }
                    },
                    label = { Text(stringResource(id = R.string.use_sms_code)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    },
                    modifier = Modifier.animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                )
                AnimatedVisibility(
                    visible = loginState.loginMethod == LoginMethod.SMS,
                    enter = expandVertically(expandFrom = Alignment.Top) +
                            fadeIn(initialAlpha = 0.15f),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top) +
                            fadeOut(targetAlpha = 0.15f)
                ) {
                    SuggestionChip(
                        onClick = {
                            loginViewModel.handleIntent(LoginIntent.SendSMS(context, inputState.phone))
                        },
                        enabled = loginState.smsSentState == SMSSentState.IDLE,
                        label = {
                            if (loginState.smsSentState == SMSSentState.IDLE) {
                                Text(text = stringResource(id = R.string.sms_code_resent))
                            } else {
                                Text(
                                    text = stringResource(
                                        id = R.string.sms_cd_timer,
                                        loginState.smsTimer
                                    )
                                )
                            }
                        },
                        icon = {
                            if (loginState.smsSentState == SMSSentState.IDLE) {
                                Icon(
                                    imageVector = Icons.Filled.Send,
                                    contentDescription = ""
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.LockClock,
                                    contentDescription = ""
                                )
                            }
                        },
                        border = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (allowGuestLogin) {
                        FilledTonalButton(
                            onClick = onLoginSuccess
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.AccessibleForward,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = stringResource(id = R.string.guest_login))
                        }
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

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}