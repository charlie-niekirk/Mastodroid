package me.cniekirk.mastodroid.core.designsystem.component

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.model.Theme

@Composable
fun ThemeSelection(
    modifier: Modifier = Modifier,
    theme: Theme,
    onLightSelected: () -> Unit,
    onDarkSelected: () -> Unit,
    onSystemSelected: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .selectable(
                    selected = theme == Theme.LIGHT,
                    enabled = true,
                    onClick = { onLightSelected() }
                )
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
                .selectable(
                    selected = theme == Theme.DARK,
                    enabled = true,
                    onClick = { onDarkSelected() }
                )
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

        Column(
            modifier = Modifier
                .selectable(
                    selected = theme == Theme.SYSTEM,
                    enabled = true,
                    onClick = { onSystemSelected() }
                )
                .background(
                    if (theme == Theme.SYSTEM) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier
                    .width(80.dp)
                    .height(150.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                drawRoundRect(
                    color = Color.Black,
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    size = Size(width = 80.dp.toPx(), height = 150.dp.toPx())
                )

                val rect = Rect(Offset.Zero, size)
                val trianglePath = Path().apply {
                    moveTo(rect.topRight)
                    lineTo(rect.bottomRight)
                    lineTo(rect.bottomLeft)
                    close()
                }

                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(trianglePath),
                        paint = Paint().apply {
                            color = Color.White
                            pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "System"
                )
                if (theme == Theme.SYSTEM) {
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

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)

@Preview
@Composable
fun ThemeSelectionLightPreview() {
    MastodroidTheme {
        Surface {
            Row(modifier = Modifier.fillMaxWidth()) {
                ThemeSelection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    theme = Theme.LIGHT,
                    onLightSelected = {},
                    onDarkSelected = {},
                    onSystemSelected = {}
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
                    theme = Theme.DARK,
                    onLightSelected = {},
                    onDarkSelected = {},
                    onSystemSelected = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun ThemeSelectionSystemPreview() {
    MastodroidTheme {
        Surface {
            Row(modifier = Modifier.fillMaxWidth()) {
                ThemeSelection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    theme = Theme.SYSTEM,
                    onLightSelected = {},
                    onDarkSelected = {},
                    onSystemSelected = {}
                )
            }
        }
    }
}