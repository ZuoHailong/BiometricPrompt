# BiometricPrompt

生物识别库，目前仅支持指纹识别，计划实现面部识别

` 注意：兼容 Android 6.0 及以上系统，某些 Android 6.0 以下系统的手机自行支持指纹验证的未做兼容 `

## 示例

![添加指纹（图片加载失败了）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/add.png)
![验证指纹（图片加载失败了）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/verify.png)
![验证失败（图片加载失败了）](https://raw.githubusercontent.com/ZuoHailong/BiometricPrompt/master/example/file/fail.png)

## 功能介绍

- 支持指纹识别

- 提供界面友好的指纹识别弹窗，可自定义其样式

- 实现国际化（支持中文和英文）

## Gradle依赖

```
    dependencies {
            implementation 'com.github.ZuoHailong:BiometricPrompt:0.1'
	}
	
```
## 指纹识别用法简述
```

    FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(MainActivity.this);
    builder.callback(new FingerprintCallback()).build();
    
```
使用指纹识别功能，只需要关心 FingerprintVerifyManager 和 FingerprintCallback 两个类：

- FingerprintVerifyManager 通过 Builder 初始化并管理指纹识别功能

- FingerprintCallback 指纹识别监听，提供 onSucceeded()、onFailed()、onCancel()、onError(String errorMsg)四个回调方法

    当用户取消指纹验证框时，回调 onCancel() 方法；
    
    当指纹验证出现异常时，回调onError(String errorMsg) 方法返回具体异常信息，诸如 “手指移动过快” “尝试次数过多” 等

` 用法的详细说明请移步我的博客： `[https://blog.csdn.net/hailong0529/article/details/95116481](https://blog.csdn.net/hailong0529/article/details/95116481)
    
## Builder详细用法

#### 1、设置验证结果监听
```
builder.callback(new FingerprintCallback())
```
#### 2、设置验证框中指纹图标的颜色
```
builder.fingerprintColor(@ColorInt int color)
```
#### 3、设置取消按钮的文本色
```
builder.cancelTextColor(@ColorInt int color)
```
#### 4、开始构建并弹出更新框
```
builder.build()
```

### 更多功能待续……

