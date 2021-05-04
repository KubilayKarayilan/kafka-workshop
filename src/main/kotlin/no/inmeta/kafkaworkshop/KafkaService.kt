package no.inmeta.kafkaworkshop

import no.inmeta.kafkaworkshop.conf.KCustomSerdes
import no.inmeta.kafkaworkshop.model.Order
import no.inmeta.kafkaworkshop.model.Payment
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.JoinWindows
import org.apache.kafka.streams.kstream.KStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Duration
import java.util.*


@Service
class KafkaService {

    @Value(value = "\${kafka.topic.payment}")
    lateinit var paymentTopicName: String

    @Value(value = "\${kafka.topic.order}")
    lateinit var orderTopicName: String

    @EventListener(ApplicationReadyEvent::class)
    fun runAfterStartup() {
        streamStart()
    }

    fun streamStart() {
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "order-payment-stream-1"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG] = LogAndContinueExceptionHandler::class.java
        val builder = StreamsBuilder()

        val orderStream: KStream<String, Order> = builder.stream(
            orderTopicName,
            Consumed.with(Serdes.String(), KCustomSerdes(Order::class.java))
        ).filter { _, it ->
            it.amount != BigDecimal(0)
        }.selectKey { _, order ->
            order.orderId
        }.peek{key,value ->
            println("key: $key amount ${value.amount}")
        }

       val paymentStream: KStream<String, Payment> = builder.stream(
            paymentTopicName,
            Consumed.with(Serdes.String(), KCustomSerdes(Payment::class.java))
        ).selectKey { _, payment ->
            payment.orderId
        }.peek{key,value ->
            println("key: $key item ${value.item}")
        }

       paymentStream.join(
            orderStream,
            { payment, order -> Pair(payment, order) },
            JoinWindows.of(Duration.ofMinutes(5))
        ).foreach{key, value ->
            println("common key $key")
            println("value ${value.first.orderId}")
        }
        val topology = builder.build()
        val streams = KafkaStreams(topology,props)
        streams.start()
        println(topology.describe())

    }
}
