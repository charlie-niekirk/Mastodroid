package me.cniekirk.mastodroid.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM
}

@Composable
fun ThemeSelection(
    modifier: Modifier = Modifier,
    theme: Theme
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .background(
                    if (theme == Theme.LIGHT) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(150.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Light"
                )
                if (theme == Theme.LIGHT) {
                    Icon(
                        modifier = Modifier.padding(start = 4.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .background(
                    if (theme == Theme.DARK) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(150.dp)
                    .background(Color.Black, RoundedCornerShape(8.dp))
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark"
                )
                if (theme == Theme.DARK) {
                    Icon(
                        modifier = Modifier.padding(start = 4.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ThemeSelectionLightPreview() {
    MastodroidTheme {
        Surface {
            Row(modifier = Modifier.fillMaxWidth()) {
                ThemeSelection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    theme = Theme.LIGHT
                )
            }
        }
    }
}

@Preview
@Composable
fun ThemeSelectionDarkPreview() {
    MastodroidTheme {
        Surface {
            Row(modifier = Modifier.fillMaxWidth()) {
                ThemeSelection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    theme = Theme.DARK
                )
            }
        }
    }
}