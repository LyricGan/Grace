##---------------Begin: proguard configuration for Gson  ----------
-dontwarn sun.misc.**
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe {*;}
-keep class com.google.gson.** {*;}

##---------------End: proguard configuration for Gson  ----------