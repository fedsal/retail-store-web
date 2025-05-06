package org.fedsal.buenpuerto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform