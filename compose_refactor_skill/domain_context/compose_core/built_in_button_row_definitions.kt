

@SuppressLint("ModifierParameter")
@Preview
@Composable
fun BuiltInSaveButton01(
    buttonModifier: Modifier = builtInSaveButton01Modifier(),
    buttonText: String = "儲存",
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.White,
    buttonColors: ButtonColors = theBuiltInButtonColor01(),
    buttonIconImageVector: ImageVector? = Icons.Filled.Save,
    buttonOnClick: () -> Unit = {},
) {
    Button(
        modifier = buttonModifier,
        onClick = buttonOnClick,
        enabled = true,
        shape = ButtonDefaults.shape, // | elevatedShape | outlinedShape | textShape
        colors = buttonColors,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 10.dp),
    ) {
        if (null != buttonIconImageVector) {
            Icon(imageVector = buttonIconImageVector, contentDescription = null, tint = Color.White)

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        }

        Text(
            modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp),
            text = buttonText,
            fontSize = fontSize,
            color = fontColor,
            textAlign = TextAlign.Center,
        )
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun builtInSaveButton01Modifier(): Modifier =
    Modifier
        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 50.dp)


@Preview
@Composable
fun IconWithTextButton(
    buttonModifier: Modifier = Modifier,
    buttonText: String = "儲存",
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.White,
    buttonIconImageVector: ImageVector = Icons.Filled.Save,
    buttonOnClick: () -> Unit = {},
    paddingHorizontal: Dp = 16.dp,
    paddingVertical: Dp = 8.dp,
) {
    Row(
        modifier = buttonModifier
            .clickable(onClick = buttonOnClick)
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical), // Adjust padding as needed
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(buttonIconImageVector, contentDescription = buttonText, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buttonText,
            fontSize = fontSize,
            color = fontColor,
        )
    }
}


