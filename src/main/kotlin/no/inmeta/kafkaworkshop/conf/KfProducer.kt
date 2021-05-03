package no.inmeta.kafkaworkshop.conf

import no.inmeta.kafkaworkshop.model.Payment
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.*

@Component
class KfProducer {
    @Value(value = "\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Value(value = "\${kafka.producer.clientId}")
    lateinit var clientId: String

    @Bean
    fun customKafkaProducer(): KafkaProducer<String, Payment> {
        val props = Properties()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ProducerConfig.CLIENT_ID_CONFIG] = clientId
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KfSerialiser::class.java.name
        return KafkaProducer<String,Payment>(props)
    }
}
