# General
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Ktor & Kotlinx Serialization
-dontwarn io.ktor.**
-keep class kotlinx.serialization.json.** { *; }
-keep @kotlinx.serialization.Serializable class ** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}

# Keep our data models for safe parsing
-keep class com.greedmitya.albcalculator.model.** { *; }

# Koin & ViewModels (needed for reflection-based DSL like viewModelOf)
-keep class com.greedmitya.albcalculator.CraftViewModel { *; }
-keepclassmembers class com.greedmitya.albcalculator.CraftViewModel {
    public <init>(...);
}
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Google Play Billing
-keep class com.android.billingclient.** { *; }

# Napier Logging
-dontwarn io.github.aakira.napier.**

# SLF4J (from Ktor)
-dontwarn org.slf4j.impl.StaticLoggerBinder