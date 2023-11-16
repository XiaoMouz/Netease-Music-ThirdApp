package com.mou.cloudmusic_md3t.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var _smsSendCD = MutableStateFlow(0)
    val smsSendCD: StateFlow<Int> = _smsSendCD

    private var _useSMS = MutableStateFlow(false)
    val useSMS: StateFlow<Boolean> = _useSMS

    private fun startCDTimer(cd: Int = 60) {
        _smsSendCD.value = cd
        viewModelScope.launch(Dispatchers.IO) {
            while (_smsSendCD.value > 0) {
                delay(1000)
                _smsSendCD.value--
            }
        }
    }

    fun sendSMS(context: Context) {
        if (smsSendCD.value > 0) {
            Toast.makeText(context, "SMS Code sended buy not yet (not)", Toast.LENGTH_SHORT).show()
        } else {
            startCDTimer()
            Toast.makeText(context, "SMS Code sended buy not yet", Toast.LENGTH_SHORT).show()
        }
    }

    fun toggleSMS() {
        _useSMS.value = !useSMS.value
    }

    fun login(phone: String, code: String, isSMS: Boolean) {
        Toast.makeText(null, "Login? Not enable LMAO", Toast.LENGTH_SHORT).show()
    }
}