package com.mou.cloudmusic_md3t.data.datastore

import android.content.Context
import androidx.core.net.toUri
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

private const val SETTINGS_PREFERENCES_NAME = "settings_preferences"

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_PREFERENCES_NAME)


/**
 * 应用配置 Datastore
 * @param context 应用上下文
 */
class SettingsDataStore(context: Context) {
    // API 地址配置
    private val API_ADDRESS = stringPreferencesKey("api_address")
    val apiAddressFlow: Flow<String> = context.settingsDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences()) // 清空 preferences
            } else {
                throw it
            }
        }
        .map {
            it[API_ADDRESS] ?: "https://api.mouz.xyz/cloudmusic/" // 默认值
        }

    /**
     * 设置 API 地址首选项
     * @param address String API地址, 最好是无 scheme,自动保存为 https
     * @param context Context 上下文
     */
    suspend fun setApiAddressToPreferences(address: String, context: Context) {
        val finalAddress = address.toUri().buildUpon().scheme("https").build().toString()
        context.settingsDataStore.edit { it[API_ADDRESS] = finalAddress }
    }


}

