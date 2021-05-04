package no.inmeta.kafkaworkshop.conf

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class KCustomSerializer<T:Any>:Serializer<T> {
    private val mapper = ObjectMapper()
    override fun serialize(topic: String?, data: T): ByteArray {
        return mapper.writeValueAsBytes(data)
    }
}
