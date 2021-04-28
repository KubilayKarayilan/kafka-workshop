package no.inmeta.kafkaworkshop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaWorkshopApplication
@Autowired
lateinit var kafkaService: KafkaService
fun main(args: Array<String>) {
    runApplication<KafkaWorkshopApplication>(*args)
}
