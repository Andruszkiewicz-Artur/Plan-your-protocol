package com.andruszkiewiczarturmobileeng.planyourprotocol

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}