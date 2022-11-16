package org.homeproject.monitoring

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service

@Service
class PrometheusService(private val meterRegistry: MeterRegistry) : MonitoringService {

    override fun incrementMessage(queueName: String) {
        meterRegistry.counter("count_queue_", "queue", queueName)
    }

}