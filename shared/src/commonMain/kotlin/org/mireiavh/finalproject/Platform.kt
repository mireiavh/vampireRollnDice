package org.mireiavh.finalproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform