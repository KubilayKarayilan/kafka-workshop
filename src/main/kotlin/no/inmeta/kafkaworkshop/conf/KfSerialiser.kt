package no.inmeta.kafkaworkshop.conf

import com.fasterxml.jackson.databind.ObjectMapper
import no.inmeta.kafkaworkshop.model.Payment
import org.apache.kafka.common.serialization.Serializer

class KfSerialiser: Serializer<Payment> {
    override fun serialize(topic: String?, data: Payment?): ByteArray {
       val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsBytes(data
        )
    }
}
