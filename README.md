## Lokal-IP KMP

**Lokal-IP KMP** is a Kotlin Multiplatform library that allows you to easily retrieve the local IP
address of the connected device on both **Android** and **iOS**.

### Features

✔ Retrieve the local IP address of the device  
✔ Supports **Android** and **iOS** using Kotlin Multiplatform  
✔ Lightweight and easy to use

## Installation

Add the following dependency to your **Kotlin Multiplatform** project:

```kotlin
commonMain.dependencies {
    implementation("io.mohaberabi:lokal-ip:0.0.2")
}
```

## Usage

### Common Code

```kotlin
import com.mohaberabi.lokalip.LokalIp

fun getLocalIp(): String {
    val lokalIp = LokalIpFactory().create()
    val localIpAddress = lokalIp.getLocalIpAddress()
    return localIpAddress
}
```


