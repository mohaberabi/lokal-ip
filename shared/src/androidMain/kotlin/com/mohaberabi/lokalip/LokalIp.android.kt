package com.mohaberabi.lokalip

import java.net.Inet4Address
import java.net.NetworkInterface
import kotlin.coroutines.cancellation.CancellationException

private class AndroidLokalIp : LokalIp {
    override fun getLocalIpAddress(): String {
        return try {

            val allInterfaces = NetworkInterface.getNetworkInterfaces()

            for (networkInterface in allInterfaces) {
                val allAddresses = networkInterface.inetAddresses

                while (allAddresses.hasMoreElements()) {
                    val address = allAddresses.nextElement()
                    if (!address.isLoopbackAddress && address is Inet4Address) {
                        return address.hostAddress?.toString() ?: EMPTY_IP
                    } else {
                        EMPTY_IP
                    }
                }
            }
            EMPTY_IP
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            EMPTY_IP
        }
    }
}

actual class LokalIpFactory actual constructor() {
    actual fun create(): LokalIp = AndroidLokalIp()
}