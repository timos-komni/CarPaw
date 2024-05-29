package com.timkom.carpaw.ui.components.cards

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

data class Content(
    val id: Int,
    @StringRes val title: Int,
    val text: String
)

/**
 * TODO (Chloe-IMPORTANT!) Remove `contentList` property from top-level scope. Move it into a [Composable]!
 *
 * EXPLANATION: It will probably translate into a static field of the class ExpandableCardKt in Java
 * thus getting initialized when the class ExpandableCardKt is loaded.
 */
val contentList = listOf(
    Content(0,  R.string.leaving_from__title,  "TEST"),
    Content(1,  R.string.travelling_to__title,  "TEST")
)

/**
 * TODO Consider adding [Modifier] parameter
 * TODO (Chloe) Consider making the whole view clickable, not just the arrow-button
 */
@Composable
fun ExpandableCard(
    content: Content,
    expanded: Boolean,
    /**
     * TODO maybe remove parameter name (id) from type?!
     *
     * _onClickExpanded: (Int) -> Unit_
     */
    onClickExpanded: (id: Int) -> Unit
) {
    val transition = updateTransition(targetState = expanded, label = "trans")
    // TODO maybe "inline" the "delegator"?!
    val iconRotationDeg by
    transition.animateFloat(label = "icon change") { state ->
        if (state) 0f else 180f
    }
    // TODO maybe "inline" the "delegator"?!
    val color by
    transition.animateColor(label = "color change") { state ->
        if (state) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
        }
    }

    Card(
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ){
                Text(
                    text = stringResource(id = content.title),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    lineHeight = 1.25.em,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_bold)),
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Drop-Up Arrow",
                    modifier = Modifier
                        .rotate(iconRotationDeg)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onClickExpanded(content.id)
                        }
                )

            }
            Spacer(modifier = Modifier.size(16.dp))
            ExpandableContent(isExpanded = expanded, desc = content.text)
        }
    }
}

/**
 * TODO (Chloe) Don't forget Previews...maybe unnecessary, but quite useful
 */
@Preview(showBackground = true)
@Composable
fun ExpandableCardPreview() {
    CarPawTheme {
        ExpandableCard(
            content = Content(
                0,
                R.string.leaving_from__title,
                "test"
            ),
            expanded = true
        ) {

        }
    }
}

/**
 * TODO Consider adding [Modifier] parameter
 */
@Composable
fun ExpandableContent(
    isExpanded: Boolean,
    /**
     * TODO (Chloe) if unused, remove it
     */
    desc: String
){
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            initialAlpha = .3f,
            animationSpec = tween(300),
            )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        )+ fadeOut(animationSpec = tween(300))
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column {
           // SearchLocationBar(placeholder = R.string.search_departure__placeholder) does not working yet see the preview
            Text(text = "test")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableContentPreview() {
    CarPawTheme {
        ExpandableContent(isExpanded = true, desc = "Test")
    }
}

