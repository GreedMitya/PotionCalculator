package com.greedmitya.albcalculator.i18n

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val bcp47Tag: String,
    val isAvailable: Boolean = false,
) {
    ENGLISH("en", "English", "en", isAvailable = true),
    RUSSIAN("ru", "Русский", "ru", isAvailable = true),
    SPANISH("es", "Español", "es"),
    PORTUGUESE("pt", "Português", "pt-BR"),
    GERMAN("de", "Deutsch", "de"),
    FRENCH("fr", "Français", "fr"),
    CHINESE("zh", "中文", "zh-Hans"),
    KOREAN("ko", "한국어", "ko"),
    JAPANESE("ja", "日本語", "ja"),
    POLISH("pl", "Polski", "pl");

    companion object {
        val DEFAULT = ENGLISH
        val available get() = entries.filter { it.isAvailable }

        fun fromCode(code: String): AppLanguage =
            entries.find { it.code == code } ?: DEFAULT
    }
}
