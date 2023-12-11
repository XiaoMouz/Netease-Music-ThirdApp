package com.mou.cloudmusic_md3t.ui.screen.login

data class InputState (
    val username: String = "",
    val phone: String = "",
    val password: String = ""
)

data class LoginState (
    val isLoading: Boolean = false,
    val loginMessage: String="",
    val loginMethod: LoginMethod = LoginMethod.PASSWORD,
    val smsSentState: SMSSentState = SMSSentState.IDLE,
    val smsTimer:Int = 0
)

enum class SMSSentState{
    IDLE, SENT
}

enum class LoginMethod{
    SMS, PASSWORD
}