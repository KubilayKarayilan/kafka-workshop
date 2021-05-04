package no.inmeta.kafkaworkshop.conf

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer

class KCustomSerdes<T: Any>(clazz: Class<T>?):org.apache.kafka.common.serialization.Serde<T> {
    private val kCustomDeserializer = KCustomDeserializer(clazz)
    private val kCustomSerializer = KCustomSerializer<T>()
    override fun serializer(): Serializer<T> {
        return kCustomSerializer
    }

    override fun deserializer(): Deserializer<T> {
        return kCustomDeserializer
    }
}
