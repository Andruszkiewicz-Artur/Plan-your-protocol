package com.andruszkiewiczarturmobileeng.planyourprotocol

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform