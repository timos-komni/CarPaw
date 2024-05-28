import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.InfoItem
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    infoItem: InfoItem,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors( MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .padding(4.dp)
            .width(170.dp)
            .height(160.dp)

    ){
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Image(
                painter = painterResource(id = infoItem.icon),
                contentDescription = stringResource(R.string.decorative_icon),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .requiredWidth(width = 32.dp)
                    .requiredHeight(height = 32.dp)
            )
            //Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = infoItem.info),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
                lineHeight = 1.40.em,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

        }
    }
}

@Preview(widthDp = 150, heightDp = 132)
@Composable
 fun InfoItemPreview() {
    CarPawTheme(dynamicColor = false){
        InfoCard(
            infoItem = InfoItem(
            id = 1,
            info = R.string.info_item__create_ride,
            icon = R.drawable.add_location
        ), navController = rememberNavController())
    }
 }
