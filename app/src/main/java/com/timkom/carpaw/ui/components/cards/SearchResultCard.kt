package com.timkom.carpaw.ui.components.cards

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.ui.components.PriceRow
import com.timkom.carpaw.ui.components.RatingStars
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.theme.CarPawTheme
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.io.Serial
import java.util.UUID

data class SearchResultCardData(
    val ride: Ride,
    val user: User,
    val selectedAnimals: List<CompanionAnimalItem>
) : java.io.Serializable

@SuppressLint("ResourceType")
@Composable
fun SearchResultCard(
    data: SearchResultCardData,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "${data.ride.start.substringBefore(',')} to ${data.ride.destination.substringBefore(',')}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Column(){
                    PriceRow(data.ride.price)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_circle),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${data.user.firstName} ${data.user.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
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
                    Spacer(modifier = Modifier.height(8.dp))
                    RatingStars(rating = data.user.rating)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.elevatedButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.view_details__button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultCardPreview() {
    val sampleRide = Ride(
        id = 1,
        createdAt = "2024-01-01T10:00:00Z",
        hostId = UUID.randomUUID().toString(),
        ownerId = null,
        start = "Thessaloniki",
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

    val sampleData = SearchResultCardData(
        ride = sampleRide,
        user = sampleUser,
        selectedAnimals = sampleAnimals
    )

    CarPawTheme(dynamicColor = false) {
        SearchResultCard(data = sampleData, onClick = {})
    }
}

