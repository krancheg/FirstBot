package org.homeproject.configuration

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MonitoringConfiguration {

    @Bean
    open fun simpleMeterRegistry() : MeterRegistry {
        return SimpleMeterRegistry()
    }
}