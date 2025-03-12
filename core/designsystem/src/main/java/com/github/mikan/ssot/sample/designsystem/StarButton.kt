package com.github.mikan.ssot.sample.designsystem

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch

@Composable
fun StarButton(
    hasStarred: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (hasStarred) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        label = "containerColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (hasStarred) {
            Color.Yellow
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        label = "contentColor"
    )
    val angle = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(hasStarred) {
        angle.animateTo(
            targetValue = if (hasStarred) {
                360f
            } else {
                0f
            },
            animationSpec = tween(200),
        )
    }

    IconButton(
        onClick = {
            scope.launch {
                scale.animateTo(0.8f)
                scale.animateTo(1f)
            }
            onClick()
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .scale(scale.value)
                .graphicsLayer {
                    rotationZ = angle.value
                },
        ) {
            if (hasStarred) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.star_outlined_24px),
                    contentDescription = null,
                )
            }
        }
    }
}
