

@Preview
@Composable
fun BuiltInCircleIcon01(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    iconImageVector: ImageVector = Icons.Filled.KeyboardDoubleArrowUp,
    iconTint: Color = Color.White,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Icon(
            modifier = Modifier.size(size),
            imageVector = iconImageVector,
            tint = iconTint,
            contentDescription = null,
        )
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInCircleIcon01Modifier01(
    modifier: Modifier = Modifier,
    circleColor: Color = Teal200,
    onClick: () -> Unit = {}
): Modifier =
    Modifier.then(modifier)
        .background(color = circleColor, shape = CircleShape)
        .clickable(onClick = onClick)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInCircleIcon01Modifier02(
    circleColor: Color = Teal200,
    size: Dp = 200.dp,
    onClick: () -> Unit = {}
): Modifier = createBuiltInCircleIcon01Modifier01(
    modifier = Modifier.size(size),
    circleColor = circleColor,
    onClick = onClick,
)


@Preview
@Composable
fun BuiltInCircleIcon02(
    modifier: Modifier = Modifier,
    arcColor: Color = Teal200,
    iconImageVector: ImageVector = Icons.Filled.KeyboardDoubleArrowUp,
    iconTint: Color = Color.White,
) {
    Box(
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            drawCircle(
                color = arcColor,
            )
        }

        Icon(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            imageVector = iconImageVector,
            tint = iconTint,
            contentDescription = null,
        )
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInCircleIcon02Modifier01(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
): Modifier =
    Modifier.then(modifier)
        .clickable(onClick = onClick)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInCircleIcon02Modifier02(
    size: Dp = 200.dp,
    onClick: () -> Unit = {}
): Modifier = createBuiltInCircleIcon02Modifier01(
    modifier = Modifier.size(size),
    onClick = onClick,
)



@Preview
@Composable
fun BuiltInColorCircle01(
    modifier: Modifier = Modifier,
    arcColor: Color = Teal200,
    //size: Dp = 200.dp,
) {
    Canvas(
        modifier = modifier
    ) {
        drawCircle(
            color = arcColor,
        )
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInColorCircle01Modifier01(
    size: Dp = 200.dp,
): Modifier = Modifier.size(size)


@Preview
@Composable
fun BuiltInRoundedCornerIcon01(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    iconImageVector: ImageVector = Icons.Filled.KeyboardDoubleArrowUp,
    iconTint: Color = Color.White,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Icon(
            modifier = Modifier.size(size),
            imageVector = iconImageVector,
            tint = iconTint,
            contentDescription = null,
        )
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInRoundedCornerIcon01Modifier01(
    modifier: Modifier = Modifier,
    rectColor: Color = Teal200,
    onClick: () -> Unit = {}
): Modifier =
    Modifier.then(modifier)
        .background(color = rectColor, shape = RoundedCornerShape(8.dp))
        .clickable(onClick = onClick)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInRoundedCornerIcon01Modifier02(
    rectColor: Color = Teal200,
    size: Dp = 200.dp,
    onClick: () -> Unit = {}
): Modifier = createBuiltInRoundedCornerIcon01Modifier01(
    modifier = Modifier.size(size),
    rectColor = rectColor,
    onClick = onClick,
)


