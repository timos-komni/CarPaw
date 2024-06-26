import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.ui.components.PriceRow
import com.timkom.carpaw.ui.components.RatingStars
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.formatDateTime
import kotlinx.datetime.Clock
import java.util.UUID

data class CreatedRideCardData(
    val ride: Ride,
    val user: User,
    val selectedAnimals: List<CompanionAnimalItem>
)
@SuppressLint("ResourceType")
@Composable
fun CreatedRideCard(
    data: CreatedRideCardData,
    onClick: () -> Unit
) {
    val sortedAnimals = CompanionAnimalItem.entries.sortedByDescending { data.selectedAnimals.contains(it) }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Text(
                        text = "${data.ride.start.substringBefore(',').substringBefore('-')} to ${data.ride.destination.substringBefore(',').substringBefore('-')}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDateTime(data.ride.date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }

                Column {
                    PriceRow(data.ride.price)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        sortedAnimals.forEach { animal ->
                            Image(
                                painter = painterResource(id = animal.icon),
                                contentDescription = context.resources.getQuantityString(animal.animalName, 1),
                                modifier = Modifier.size(animal.iconSize),
                                colorFilter = ColorFilter.tint(
                                    if (data.selectedAnimals.contains(animal)) {
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f)
                                    }
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Created at: ${formatDateTime(data.ride.createdAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = stringResource(id = R.string.decorative_icon),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.edit__button),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatedRideCardPreview() {
    val sampleRide = Ride(
        id = 1,
        createdAt = "2024-06-25T20:26:56.982108+00:00",
        hostId = UUID.randomUUID().toString(),
        ownerId = null,
        start = "Thessaloniki, Greece",
        destination = "Ioannina",
        date = "2024-06-29",
        status = Ride.Status.Upcoming,
        startTime = Clock.System.now().toString(),
        endTime = Clock.System.now().toString(),
        price = 20.0f,
        startAddress = "Egnatia 122",
        destinationAddress = "Monastiriou"
    )

    val sampleUser = User(
        id = UUID.randomUUID().toString(),
        createdAt = "2024-01-01T10:00:00Z",
        uuid = "123-456-789",
        //username = "olga",
        //password = "password",
        firstName = "Olga",
        //middleName = null,
        lastName = "S.",
        birthdate = "1990-01-01",
        /*email = "olga@example.com",
        phoneNumber = "1234567890",*/
        rating = 4.5f,
        imageUrl = null,
        otherInfo = null
    )

    val sampleAnimals = listOf(
        CompanionAnimalItem.SMALL_DOG,
        CompanionAnimalItem.CAT,
        CompanionAnimalItem.BIRD
    )

    val sampleData = CreatedRideCardData(
        ride = sampleRide,
        user = sampleUser,
        selectedAnimals = sampleAnimals
    )

    CarPawTheme(dynamicColor = false) {
        CreatedRideCard(data = sampleData, onClick = {})
    }
}

