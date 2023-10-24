package com.amaldz.learnapp.helpers

interface PreferenceHelper {

    fun getFontSize(): Float
    fun setFontSize(font_size: Float)

    fun getLanguage(): String
    fun setLanguage(Language: String)

    fun clearPrefs()
}