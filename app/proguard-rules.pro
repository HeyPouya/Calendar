# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\SDK/tools/proguard/proguard-android.txt
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
-keep class com.google.** { *;}
-keep interface com.google.** { *;}
-dontwarn com.google.**
-keep class cz.msebera.android.httpclient.** { *; }
-keep class com.loopj.android.http.** { *; }
-keepattributes EnclosingMethod
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-keepattributes *Annotation*,Signature
-keep class * extends com.google.api.client.json.GenericJson {
*;
}
-keep class com.google.api.services.drive.** {
*;
}
-keep public class ir.adad.client.** {
   *;
}
-optimizationpasses 3
-overloadaggressively
-repackageclasses ''
-allowaccessmodification