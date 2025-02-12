package com.mohaberabi.lokalip

interface LokalIp {
    fun getLocalIpAddress(): String
}


expect class LokalIpFactory() {
    fun create(): LokalIp
}


