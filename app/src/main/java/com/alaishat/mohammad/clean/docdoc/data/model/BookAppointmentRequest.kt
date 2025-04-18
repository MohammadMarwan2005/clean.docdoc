package com.alaishat.mohammad.clean.docdoc.data.model

import com.alaishat.mohammad.clean.docdoc.domain.model.BookAppointment
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter
import java.util.Locale

@Serializable
data class BookAppointmentRequest(
    @SerializedName("doctor_id") val doctorId: Int,
    @SerializedName("start_time") val startTime: String,
    val notes: String
)

fun BookAppointment.toRequest(): BookAppointmentRequest {
    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:mm", Locale.ENGLISH)
    return BookAppointmentRequest(
        doctorId = doctorId,
        startTime = startTime.format(formatter),
        notes = notes
    )
}
