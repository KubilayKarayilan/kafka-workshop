package no.inmeta.kafkaworkshop

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class KafkaService {

    @Autowired
    lateinit var customKafkaConsumer: KafkaConsumer<String,String>

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
            }
        }
    }
}
