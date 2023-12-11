package com.mou.cloudmusic_md3t.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginIntent {
    data class InputUsernameState(val value: String) : LoginIntent()
    data class InputPhoneState(val value: String) : LoginIntent()
    data class InputPasswordState(val value: String) : LoginIntent()
    data class ChangeLoginMethod(val value: LoginMethod) : LoginIntent()
    data class SendSMS(val context: Context,val phone: String) : LoginIntent()
    data class SubmitLogin(val phone: String, val code: String, val isSMS: Boolean) : LoginIntent()
}

class LoginViewModel : ViewModel() {
    private val _inputState = MutableStateFlow(InputState())
    val inputState = _inputState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun handleIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.InputUsernameState -> _inputState.apply {
                value = value.copy(username = intent.value)
            }
            is LoginIntent.InputPasswordState -> _inputState.apply {
                value = value.copy(password = intent.value)
            }
            is LoginIntent.InputPhoneState -> _inputState.apply {
                value = value.copy(phone = intent.value)
            }

            is LoginIntent.ChangeLoginMethod -> _loginState.apply {
                value = value.copy(loginMethod = intent.value)
            }

            is LoginIntent.SendSMS -> {
                _loginState.apply {
                    if(value.smsTimer>0){
                        return
                    }
                    if(value.smsSentState!=SMSSentState.IDLE){
                        return
                    }
                    startCDTimer(60)
                }
                sendSMS(context = intent.context, phone = intent.phone)
            }
            is LoginIntent.SubmitLogin -> {

            }
        }
    }
    private fun startCDTimer(cd: Int = 60) {
        _loginState.apply {
            value = value.copy(smsSentState = SMSSentState.SENT)
            value = value.copy(smsTimer = cd)

            viewModelScope.launch(Dispatchers.IO) {
                while (value.smsTimer > 0) {
                    delay(1000)
                    value = value.copy(smsTimer = value.smsTimer - 1)
                }
                value = value.copy(smsSentState =  SMSSentState.IDLE)
            }
        }
    }

    private fun sendSMS(context: Context, phone: String) {

        Toast.makeText(context, "SMS Code sent buy not yet", Toast.LENGTH_SHORT).show()
    }

    private fun login(phone: String, code: String, isSMS: Boolean) {
        Toast.makeText(null, "Login? Not enable LMAO", Toast.LENGTH_SHORT).show()
    }
}