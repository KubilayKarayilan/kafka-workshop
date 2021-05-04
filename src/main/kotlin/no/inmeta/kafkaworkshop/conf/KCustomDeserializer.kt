package no.inmeta.kafkaworkshop.conf

import com.fasterxml.jackson.databind.ObjectMapper
import no.inmeta.kafkaworkshop.model.Order
import org.apache.kafka.common.serialization.Deserializer

class KCustomDeserializer<T>(private val clazz: Class<T>?) : Deserializer<T> {
    private val mapper = ObjectMapper()
    override fun deserialize(topic: String?, data: ByteArray?): T {
        return mapper.readValue(data,clazz)
    }

}
