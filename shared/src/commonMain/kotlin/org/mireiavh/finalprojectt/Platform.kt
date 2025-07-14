package org.mireiavh.finalprojectt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform