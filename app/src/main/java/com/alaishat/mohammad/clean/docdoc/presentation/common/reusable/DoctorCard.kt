package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Gray
import kotlin.random.Random

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Composable
fun DoctorCard(
    name: String = "Dr. Jack Sullivan",
    specialization: String = "General",
    city: String = "Damascus",
    phone: String = "0999999999",
    degree: String = "Degree",
    gender: String = "Male",
    showGenderAndHidPhoneAndCity: Boolean = false,
    orientation: Int = LocalConfiguration.current.orientation,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    model: String = "",
) {
    val modifier =
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))


    val clickableMod = Modifier.clickable { onClick() }
    val toShowModel = if (Random.nextBoolean()) "https://www.americanbankingnews.com/wp-content/timthumb/timthumb.php?src=https://www.marketbeat.com/logos/tesla-inc-logo-1200x675.png" else model


    val resultMod =
        (if (clickable) modifier.then(clickableMod) else modifier)
            .padding(8.dp)

    Row(
        modifier = resultMod,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val imageModifier = if (orientation == Orientation.Vertical.ordinal) Modifier
            .fillMaxWidth(0.3f) else Modifier.requiredSize(120.dp)

        AsyncImage(
            modifier =
                imageModifier
                    .clip(RoundedCornerShape(12.dp)),
            model = toShowModel,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = name, style = CleanDocdocTheme.typography.titleSmall)
            Text(
                text = "$specialization | $degree",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showGenderAndHidPhoneAndCity)
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                else {
                    Text(
                        text = city,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                    Text(
                        text = " $phone",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                }
            }
        }
    }
}
