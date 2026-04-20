package com.greedmitya.albcalculator.i18n

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val bcp47Tag: String,
    val isAvailable: Boolean = false,
) {
    ENGLISH("en", "English", "en", isAvailable = true),
    RUSSIAN("ru", "Русский", "ru", isAvailable = true),
    SPANISH("es", "Español", "es", isAvailable = true),
    PORTUGUESE("pt", "Português", "pt-BR", isAvailable = true),
    GERMAN("de", "Deutsch", "de", isAvailable = true),
    FRENCH("fr", "Français", "fr", isAvailable = true),
    CHINESE("zh", "中文", "zh-Hans", isAvailable = true),
    KOREAN("ko", "한국어", "ko", isAvailable = true),
    JAPANESE("ja", "日本語", "ja", isAvailable = true),
    POLISH("pl", "Polski", "pl", isAvailable = true);

    companion object {
        val DEFAULT = ENGLISH
        val available get() = entries.filter { it.isAvailable }

        fun fromCode(code: String): AppLanguage =
            entries.find { it.code == code } ?: DEFAULT
    }
}
