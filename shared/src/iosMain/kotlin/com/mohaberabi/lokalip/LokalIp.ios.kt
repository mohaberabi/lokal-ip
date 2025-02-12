package com.mohaberabi.lokalip

import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cValue
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import platform.EventKitUI.EventKitUIBundle
import platform.darwin.EMPTY
import platform.darwin.freeifaddrs
import platform.darwin.getifaddrs
import platform.darwin.ifaddrs
import platform.darwin.inet_ntoa
import platform.posix.AF_INET
import platform.posix.sockaddr_in
import kotlin.coroutines.cancellation.CancellationException

private class IosLokalIp : LokalIp {


    @OptIn(ExperimentalForeignApi::class)
    override fun getLocalIpAddress(): String {
        memScoped {
            val ifAddrStruct = alloc<CPointerVar<ifaddrs>>()

            if (getifaddrs(ifAddrStruct.ptr) != 0) return "---.---.---"

            var ptr = ifAddrStruct.value
            while (ptr != null) {
                val interfaceName = ptr.pointed.ifa_name?.toKString() ?: ""
                val addrFamily = ptr.pointed.ifa_addr?.pointed?.sa_family?.toInt()

                if (addrFamily == AF_INET && interfaceName == "en0") {
                    val sockaddr = ptr.pointed.ifa_addr!!.reinterpret<sockaddr_in>().pointed
                    val ip = inet_ntoa(cValue {
                        this.s_addr = sockaddr.sin_addr.s_addr
                    })?.toKString()
                    freeifaddrs(ifAddrStruct.value)
                    return ip ?: "---.---.---"
                }
                ptr = ptr.pointed.ifa_next
            }
            freeifaddrs(ifAddrStruct.value)
        }
        return "---.---.---"
    }
}

actual class LokalIpFactory actual constructor() {
    actual fun create(): LokalIp = IosLokalIp()
}