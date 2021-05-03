package no.inmeta.kafkaworkshop.model

import java.math.BigDecimal

data class Payment(val orderId:String, val item:String, val amount:BigDecimal)
