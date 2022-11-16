package org.homeproject.monitoring

interface MonitoringService {
    fun incrementMessage(queueName: String)
}