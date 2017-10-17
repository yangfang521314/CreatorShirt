# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
###<--1.通用配置-->#########
-printmapping mapping.txt
-useuniqueclassmembernames
-allowaccessmodification

#-ignorewarnings
-keepattributes SourceFile,SourceDir,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepattributes EnclosingMethod
-keepattributes Exceptions, InnerClasses

#不混淆Serializable的子类，由于retrofit反射机制，这里配置和默认不同
-keep class * implements java.io.Serializable {*;}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

-dontnote com.google.vending.licensing.ILicensingService
-dontnote **ILicensingService


-keepattributes Signature
##########################自定义控件不混淆#####################
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keep public class * extends android.widget.* {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keep public class * extends android.webkit.* {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

################自定义数据模型类不混淆（评论不过滤会出错，其他暂未发现）#############################
-keep class com.example.yf.creatorshirt.mvp.model.address.**{*;}
-keep class com.example.yf.creatorshirt.mvp.model.basechoice.**{*;}
-keep class com.example.yf.creatorshirt.mvp.model.address.detaildesign.**{*;}
-keep class com.example.yf.creatorshirt.mvp.model.address.orders.**{*;}
-keep class com.example.yf.creatorshirt.common.cache.**{*;}

############################自定义数据适配器不混淆############################
-keep public class * extends android.widget.BaseAdapter {*;}
-keep public class * extends android.support.v4.view.PagerAdapter {*;}

#event-bus callback
-keepclassmembers class * {
    void onEventMainThread(***);
}

#retrofit
-keep class retrofit2.**{*;}
-dontwarn retrofit2.**
-dontnote retrofit2.**
#-keep class retrofit.**{*;}
#-dontwarn retrofit.**
#okthhp
-keep class org.apache.http.**{*;}
-dontwarn org.apache.http.**
#okthhp
#-keep class com.squareup.okhttp3.**{*;}
#-dontwarn com.squareup.okhttp3.**
#-dontnote com.squareup.okhttp3.**
#-keep class com.squareup.okhttp.**{*;}
#-dontwarn com.squareup.okhttp.**
-keep class okhttp3.**{*;}
-dontwarn okhttp3.**
-dontnote okhttp3.**

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

###########################Umeng Share#########
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**

-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.socialize.view.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep class javax.*
-keep public class android.webkit.* {*;}

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.view.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#####

#####支付宝####
-libraryjars libs/alipaySDK-20170725.jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

###fastjson###
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}

###七牛###
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

###glide###
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
