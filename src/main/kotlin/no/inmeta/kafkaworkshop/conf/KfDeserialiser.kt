package no.inmeta.kafkaworkshop.conf

import com.fasterxml.jackson.databind.ObjectMapper
import no.inmeta.kafkaworkshop.model.Order
import org.apache.kafka.common.serialization.Deserializer

class KfDeserialiser: Deserializer<Order> {
    override fun deserialize(topic: String?, data: ByteArray?): Order? {
        val objMapper = ObjectMapper()
        return objMapper.readValue(data, Order::class.java)
    }
}
