# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/taolin/Library/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# common config
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontpreverify
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

# keep the jni method
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep the enum
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# keep the Creator of Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# keep the Serializable
-keepclassmembers class * implements java.io.Serializable { *; }

# keep resouces id
-keepclassmembers class **.R$* { *; }

# keep the set and get method of views
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# for umeng analyze
-dontwarn com.umeng.**
-keepnames class com.umeng.** { *; }
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-dontwarn java.nio.file.**
-keepnames class java.nio.file.** { *; }

-dontwarn org.codehaus.**
-keepnames class org.codehaus.** { *; }

# resolve the gson data, cannot be changed
-dontwarn app.taolin.one.models.**
-keepclassmembernames class app.taolin.one.models.** { *; }

-dontwarn org.greenrobot.greendao.**
-keepnames class org.greenrobot.greendao.** { *; }

-dontwarn app.taolin.one.dao.**
-keep class app.taolin.one.dao.** { *; }

-dontwarn org.greenrobot.greendao.**
-keepnames class org.greenrobot.greendao.** { *; }

-dontwarn android.databinding.**
-keepnames class android.databinding.** { *; }

-dontwarn com.android.volley.**
-keepnames class com.android.volley.** { *; }

-dontwarn com.google.gson.**
-keepnames class com.google.gson.** { *; }

-dontwarn retrofit2.**
-keepnames class retrofit2.** { *; }