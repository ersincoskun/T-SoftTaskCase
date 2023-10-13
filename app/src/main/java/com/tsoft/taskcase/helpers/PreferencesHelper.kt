package com.tsoft.taskcase.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tsoft.taskcase.utils.Constants.PREF_NAME
import com.tsoft.taskcase.utils.printErrorLog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PreferencesHelper @Inject constructor(@ApplicationContext var context: Context) {

    companion object {
        const val FAVORITE_LIST = "FAVORITE_LIST"
    }

    private var sharedPreferences = createSharedPreferences(context)

    private fun createSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun getSharedPrefsValue(
        key: String,
        type: Class<*>,
        defaultLong: Long = 0,
        defaultString: String = "",
        defaultInt: Int = 0,
        defaultFloat: Float = 0f,
        defaultBoolean: Boolean = false
    ): Any? {
        return when (type) {
            String::class.java -> sharedPreferences.getString(key, defaultString)
            Long::class.java -> sharedPreferences.getLong(key, defaultLong)
            Int::class.java -> sharedPreferences.getInt(key, defaultInt)
            Float::class.java -> sharedPreferences.getFloat(key, defaultFloat)
            Boolean::class.java -> sharedPreferences.getBoolean(key, defaultBoolean)
            Set::class.java -> sharedPreferences.getStringSet(key, emptySet())
            else -> {
                printErrorLog("out of type")
                printErrorLog("shared preferences out of type")
                null
            }
        }
    }

    private fun savePrefValue(key: String, value: Any?) {
        val editor = sharedPreferences.edit()
        editor.apply {
            if (value == null) {
                remove(key)
                apply()
                return
            }
            when (value) {
                is String -> putString(key, value)
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is Set<*> -> putStringSet(key, value as Set<String>)
            }
            apply()
        }

    }

    fun removeValue(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key).apply()
    }

    //favori listesi set collection'ınna koyularak depolanır
    var favoriteList: Set<String>?
        get() = getSharedPrefsValue(
            FAVORITE_LIST,
            Set::class.java
        ) as? Set<String>
        set(value) = savePrefValue(FAVORITE_LIST, value)
}