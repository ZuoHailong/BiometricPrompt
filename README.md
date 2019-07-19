# BiometricPrompt

生物识别库，目前仅支持指纹识别，计划实现面部识别。

关于指纹识别的详细介绍，请移步博客：[Android 指纹识别，提升APP用户体验，从这里开始](https://blog.csdn.net/hailong0529/article/details/95406183)

` 注意：兼容 Android 6.0 及以上系统，某些 Android 6.0 以下系统的手机自行支持指纹识别的未做兼容 `

## 示例

![验证指纹（Android 6.0 自定义指纹识别框）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/verifyM.png)
![验证指纹（Android 9.0 系统提供的指纹识别框）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/verifyP.png)
![验证失败（指纹识别异常）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/fail.png)

## 功能介绍

- 支持指纹识别，兼容 Android 6.0 和 Android 9.0

- 提供界面友好的指纹识别弹窗，可自定义其样式

- 实现国际化（支持中文和英文）

## Gradle依赖

```
    dependencies {
            implementation 'com.github.ZuoHailong:BiometricPrompt:0.2.3'
	}
	
```
## 指纹识别用法简述
```

    FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(MainActivity.this);
    builder.callback(new FingerprintCallback()).build();
    
```
使用指纹识别功能，只需要关心 FingerprintVerifyManager 和 FingerprintCallback 两个类：

- FingerprintVerifyManager 通过 Builder 初始化并管理指纹识别功能

- FingerprintCallback 指纹识别监听，提供 onSucceeded()、onFailed()、onCancel()、onUsepwd() 、onNoneEnrolled()、onHwUnavailable() 六个回调方法

    当用户取消指纹验证框时，回调 onCancel() 方法；
    
    当用户选择密码验证时，回调 onUsepwd() 方法
    
    当手机上未添加指纹时，回调 onNoneEnrolled() 方法
    
    当硬件模块不可用时，回调 onHwUnavailable() 方法
    
## Builder详细用法

#### 1、实例化 Builder，必需
```
FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(Activity activity)
```
#### 2、设置验证结果监听，必需
```
builder.callback(new FingerprintCallback())
```
#### 3、开始构建，弹出指纹识别框，并拉起指纹扫描器等待扫描指纹，必需
```
builder.build()
```
### 以下方法适用于 Android 6.0 自定义识别框的情况
#### 4、设置验证框中指纹图标的颜色，可选
```
builder.fingerprintColor(@ColorInt int color)
```
#### 5、置取消按钮的文本色，可选
```
builder.cancelTextColor(@ColorInt int color)
```
#### 6、设置密码验证按钮是否显示，默认不显示，可选
```
builder.usepwdVisible(boolean isVisible)
```
#### 7、设置密码验证按钮的文本色，可选
```
builder.usepwdTextColor(@ColorInt int color)
```
### 以下方法适用于 Android 9.0 系统提供识别框的情况
#### 8、在 Android 9.0+ 是否启用系统提供的识别框，默认不启用，可选
```
builder.enableAndroidP(boolean enableAndroidP)
```
#### 9、识别框的主标题，默认“指纹验证”，可选
```
builder.title(String title)
```
#### 10、识别框的副标题，默认不显示，可选
```
builder.subTitle(String subTitle)
```
#### 11、识别框的描述信息，默认不显示，可选
```
builder.description(String description)
```
#### 12、识别框“取消”按钮上的文字，默认“取消”，可选
```
builder.cancelBtnText(String cancelBtnText)
```

* 注意：上述适用于 Android 6.0 的 API 和适用于 Android 9.0 的 API 并不是互斥的。


### 更多功能待续……

