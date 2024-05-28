import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

data class InfoCardSpec(
    val id: Int,
    @StringRes val info: Int,
    @DrawableRes val icon: Int
)

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    spec: InfoCardSpec,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(10.dp),
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
                painter = painterResource(id = spec.icon),
                contentDescription = stringResource(R.string.decorative_icon),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .requiredWidth(width = 32.dp)
                    .requiredHeight(height = 32.dp)
            )
            //Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = spec.info),
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
            spec = InfoCardSpec(
                id = 1,
                info = R.string.info_item__create_ride,
                icon = R.drawable.add_location
            ),
            navController = rememberNavController()
        )
    }
 }
