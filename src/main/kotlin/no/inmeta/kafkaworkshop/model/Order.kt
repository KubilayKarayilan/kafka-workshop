package no.inmeta.kafkaworkshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class Order(
    @JsonProperty("orderId") val orderId: String,
    @JsonProperty("item") val item: String,
    @JsonProperty("amount")val amount: BigDecimal
) {

}
