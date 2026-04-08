package com.greedmitya.albcalculator.i18n

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val bcp47Tag: String,
) {
    ENGLISH("en", "English", "en"),
    SPANISH("es", "Español", "es"),
    PORTUGUESE("pt", "Português", "pt-BR"),
    RUSSIAN("ru", "Русский", "ru"),
    GERMAN("de", "Deutsch", "de"),
    FRENCH("fr", "Français", "fr"),
    CHINESE("zh", "中文", "zh-Hans"),
    KOREAN("ko", "한국어", "ko"),
    JAPANESE("ja", "日本語", "ja"),
    POLISH("pl", "Polski", "pl");

    companion object {
        val DEFAULT = ENGLISH

        fun fromCode(code: String): AppLanguage =
            entries.find { it.code == code } ?: DEFAULT
    }
}
