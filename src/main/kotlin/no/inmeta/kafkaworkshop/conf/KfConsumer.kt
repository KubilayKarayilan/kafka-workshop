package no.inmeta.kafkaworkshop.conf

import no.inmeta.kafkaworkshop.model.Order
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.connect.json.JsonDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class KfConsumer {
    @Value(value = "\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Value(value = "\${kafka.consumer.groupId}")
    lateinit var groupId: String

    @Bean
    fun customKafkaConsumer(): KafkaConsumer<String, Order> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KfDeserialiser::class.java.name
        return KafkaConsumer(props)
    }

}

