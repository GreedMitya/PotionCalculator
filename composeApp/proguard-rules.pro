# General
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Ktor & Kotlinx Serialization
-dontwarn io.ktor.**
-keep class kotlinx.serialization.json.** { *; }
-keep @kotlinx.serialization.Serializable class ** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}

# Keep our data models for safe parsing (Model and Domain)
-keep class com.greedmitya.albcalculator.model.** { *; }
-keep class com.greedmitya.albcalculator.domain.** { *; }

# Koin & ViewModels (needed for reflection-based DSL like viewModelOf)
# Keep constructors for all injected classes
-keep class com.greedmitya.albcalculator.**ViewModel { *; }
-keepclassmembers class com.greedmitya.albcalculator.**ViewModel {
    public <init>(...);
}
-keep class com.greedmitya.albcalculator.domain.**UseCase { *; }
-keepclassmembers class com.greedmitya.albcalculator.domain.**UseCase {
    public <init>(...);
}
-keep class com.greedmitya.albcalculator.network.**Impl { *; }
-keepclassmembers class com.greedmitya.albcalculator.network.**Impl {
    public <init>(...);
}

-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Google Play Billing
-keep class com.android.billingclient.** { *; }

# Napier Logging
-keep class io.github.aakira.napier.** { *; }
-dontwarn io.github.aakira.napier.**

# SLF4J (from Ktor)
-dontwarn org.slf4j.impl.StaticLoggerBinder