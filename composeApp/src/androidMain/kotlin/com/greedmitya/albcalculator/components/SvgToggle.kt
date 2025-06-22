import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SvgToggle(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit
) {
    val strokeColor   = Color(0xFF9747FF)
    val fillActive    = Color(0xFFF0AD4E)
    val fillInactive  = Color(0xFF999999)
    val knobColor     = Color(0xFFF2E9DC)

    Box(
        modifier = modifier
            .size(72.dp)
            .clickable { onToggle(!selected) }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // теперь toPx() доступен без отдельного импорта
            val dashEffect = PathEffect.dashPathEffect(
                floatArrayOf(10.dp.toPx(), 5.dp.toPx()), 0f
            )

            drawRoundRect(
                color        = strokeColor,
                topLeft      = Offset(0.5.dp.toPx(), 0.5.dp.toPx()),
                size         = Size(size.width - 1.dp.toPx(), size.height - 1.dp.toPx()),
                cornerRadius = CornerRadius(4.5.dp.toPx()),
                style        = Stroke(width = 1.dp.toPx(), pathEffect = dashEffect)
            )

            drawRoundRect(
                color        = if (selected) fillActive else fillInactive,
                topLeft      = Offset(20.dp.toPx(), 14.dp.toPx()),
                size         = Size(40.dp.toPx(), 20.dp.toPx()),
                cornerRadius = CornerRadius(10.dp.toPx())
            )
            drawRoundRect(
                color        = if (selected) fillInactive else fillActive,
                topLeft      = Offset(20.dp.toPx(), 39.dp.toPx()),
                size         = Size(40.dp.toPx(), 20.dp.toPx()),
                cornerRadius = CornerRadius(10.dp.toPx())
            )

            val circleCenter = if (selected) {
                Offset((20 + 30).dp.toPx(), (14 + 10).dp.toPx())
            } else {
                Offset((20 + 10).dp.toPx(), (39 + 10).dp.toPx())
            }
            drawCircle(
                color  = knobColor,
                radius = 8.dp.toPx(),
                center = circleCenter
            )
        }
    }
}
