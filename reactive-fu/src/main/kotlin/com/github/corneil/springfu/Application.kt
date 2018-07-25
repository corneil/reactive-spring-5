package com.github.corneil.springfu

import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.fu.application
import org.springframework.fu.module.data.mongodb.mongodb
import org.springframework.fu.module.logging.LogLevel.DEBUG
import org.springframework.fu.module.logging.LogLevel.WARN
import org.springframework.fu.module.logging.level
import org.springframework.fu.module.logging.logback.consoleAppender
import org.springframework.fu.module.logging.logback.debug
import org.springframework.fu.module.logging.logback.logback
import org.springframework.fu.module.logging.logback.rollingFileAppender
import org.springframework.fu.module.logging.logging
import org.springframework.fu.module.webflux.jackson.jackson
import org.springframework.fu.module.webflux.netty.netty
import org.springframework.fu.module.webflux.webflux
import org.springframework.fu.ref
import java.io.File

fun main(args: Array<String>) = application {
    bean<LocationHistoryRepository>()
    bean<LocationHistoryService>()
    bean<LocationHandler>()
    logging {
        level(WARN)
        // level("org.springframework", DEBUG)
        // level<DefaultListableBeanFactory>(WARN)

        logback {
            debug(true)
            consoleAppender()
            rollingFileAppender(File(System.getProperty("java.io.tmpdir"), "log.txt"))
        }
    }
    webflux {
        val port = if (profiles.contains("test")) 8181 else 8080
        server(netty(port)) {
            codecs {
                jackson()
            }
            router {
                val locHandler = ref<LocationHandler>()
                GET("/last30days", locHandler::findLast30Days)
                GET("/extlast30days", locHandler::findExtendedLast30Days)
            }
        }
    }
    mongodb("mongodb://user:password@localhost:27017/locs")
}.run(await = true)
