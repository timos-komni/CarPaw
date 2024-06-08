import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.ui.theme.CarPawTheme
import kotlinx.datetime.Clock

data class SearchResultCardData(
    val ride: Ride,
    val user: User,
    val selectedAnimals: List<Int>
)
@Composable
fun SearchResultCard(data: SearchResultCardData) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${data.ride.start} to ${data.ride.destination}",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = /*data.user.userImage*/ R.drawable.account_circle),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${data.user.firstName} ${data.user.lastName}",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.euro_symbol),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${data.ride.price} ",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        data.selectedAnimals.forEach { animalRes ->
                            Image(
                                painter = painterResource(id = animalRes),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (index < data.user.rating.toInt()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: Handle view details action */ },
                shape = RoundedCornerShape(14.dp),
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
        hostId = 1,
        ownerId = 1,
        start = "Thessaloniki",
        destination = "Ioannina",
        date = "2024-06-10",
        status = "Scheduled",
        startTime = Clock.System.now().toString(),
        endTime = Clock.System.now().toString(),
        price = 20.0f
    )

    val sampleUser = User(
        id = 1,
        createdAt = "2024-01-01T10:00:00Z",
        uuid = "123-456-789",
        username = "olga",
        password = "password",
        firstName = "Olga",
        middleName = null,
        lastName = "S.",
        birthdate = "1990-01-01",
        email = "olga@example.com",
        phoneNumber = "1234567890",
        rating = 4.5f,
        imageUrl = null
    )

    val sampleData = SearchResultCardData(
        ride = sampleRide,
        user = sampleUser,
        selectedAnimals = listOf(R.drawable.cat_icon, R.drawable.dog_icon)
    )

    CarPawTheme(dynamicColor = false) {
        SearchResultCard(data = sampleData)
    }
}
