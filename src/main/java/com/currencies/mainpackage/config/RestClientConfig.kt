package com.currencies.mainpackage.config

import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Configuration


@Configuration
class RestClientConfig {

    fun MyComponent(sslBundles: SslBundles) {
        val sslBundle = sslBundles.getBundle("mybundle")
        val sslContext = sslBundle.createSslContext()
        // do something with the created sslContext
    }

}
