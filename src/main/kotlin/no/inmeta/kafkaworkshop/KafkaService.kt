package no.inmeta.kafkaworkshop

import no.inmeta.kafkaworkshop.model.Order
import no.inmeta.kafkaworkshop.model.Payment
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.record.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Duration
import java.util.*


@Service
class KafkaService {

    @Autowired
    lateinit var customKafkaConsumer: KafkaConsumer<String,Order>

    @Autowired
    lateinit var customKafkaProducer: KafkaProducer<String,Payment>

    @Value(value = "\${kafka.topic.payment}")
    lateinit var paymentTopicName: String

    @EventListener(ApplicationReadyEvent::class)
    fun runAfterStartup() {
        subscribe()
    }
    fun subscribe(){
        customKafkaConsumer.subscribe(listOf("order"))
        while (true) {
          val records = customKafkaConsumer.poll(Duration.ofSeconds(1))
            records.forEach {
                print("##################")
                println(it.headers())
                println(it.key())
                println(it.value())
                print("##################")
                val payment = Payment(it.value().orderId,it.value().item, it.value().amount)
                val record = ProducerRecord(paymentTopicName, UUID.randomUUID().toString(),payment)
                customKafkaProducer.send(record)
            }
        }
    }
}
