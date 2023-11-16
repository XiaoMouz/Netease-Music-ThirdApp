package com.mou.cloudmusic_md3t.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_AUTH_PREFERENCES_NAME = "user_auth_preferences"

private val Context.userDataStore : DataStore<Preferences> by preferencesDataStore(name = USER_AUTH_PREFERENCES_NAME)

/**
 * 用户配置信息 Datastore
 */
class UserAuthDataStore(context: Context) {
    // 用户 cookies
    private val COOKIES = stringPreferencesKey("cookies")

    val cookiesFlow : Flow<String> = context.userDataStore.data
        .catch {
            if( it is IOException){
                it.printStackTrace()
                emit(emptyPreferences()) // 清空 preferences
            }else{
                throw it
            }
        }
        .map {
            it[COOKIES]?:"" // 默认值
        }

    /**
     * 设置 Cookies 首选项
     * @param cookies Cookies 内容
     * @param context Context 上下文
     */
    suspend fun setCookiesToPreferences(cookies: String, context: Context){
        context.userDataStore.edit { it[COOKIES] = cookies }
    }
}