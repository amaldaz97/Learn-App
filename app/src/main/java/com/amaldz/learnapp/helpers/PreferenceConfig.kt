package com.amaldz.learnapp.helpers

import android.content.Context
import android.content.SharedPreferences

open class PreferenceConfig(context: Context) : PreferenceHelper {
    private val PREFS_NAME = "LearnApp"
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setFontSize(font_size: Float) {
        preferences["font_size"] = font_size
    }

    override fun getFontSize(): Float {
        return preferences["font_size"] ?: 1.0f
    }

    override fun getLanguage(): String {
        return preferences["Language"] ?: "en"
    }

    override fun setLanguage(Language: String) {
        preferences["Language"] = Language
    }

    override fun clearPrefs() {
        preferences.edit().clear().apply()
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

private operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

private inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}
