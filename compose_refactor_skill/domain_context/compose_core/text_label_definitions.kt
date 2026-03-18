

/**
 * https://foso.github.io/Jetpack-Compose-Playground/foundation/lazyverticalgrid/
 */
@Preview
@Composable
fun TextLabelContent01(
    listDataState: ListDataState<TextLabelDelegate> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<TextLabelDelegate>? = null
) {
    when(listDataState) {
        is ListDataState.Init -> {
            Logger.getLogger("TextLabelContent01").log(Level.INFO, "TextLabelContent01 [Init]")

        }
        is ListDataState.Loading -> {
            Logger.getLogger("TextLabelContent01").log(Level.INFO, "TextLabelContent01 [Loading]")
            ListDataLoading01()
        }
        is ListDataState.Available -> {
            Logger.getLogger("TextLabelContent01").log(Level.INFO, "TextLabelContent01 [Available]")
            val textLabelItemList = ImmutableObjectList<TextLabelDelegate>(listDataState.theList)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
            ) {
                items(
                    items = textLabelItemList.objectList,
                    key = { textLabelItem: TextLabelDelegate -> textLabelItem.theIdentifier },
                    contentType = { textLabelItem: TextLabelDelegate -> textLabelItem.theCellType },
                ) { textLabelItem: TextLabelDelegate ->
                    when(textLabelItem.theCellType) {
                        BaseCellTypes.ITEM -> { TextLabelItem01(textLabelItem, itemClickCallback) }
                    }
                }
            }
        }
        is ListDataState.Unavailable -> {
            if (listDataState.theList.isNotEmpty()) {
                Logger.getLogger("TextLabelContent01").log(Level.INFO, "TextLabelContent01 [Unavailable]")
                val textLabelItem: TextLabelDelegate = listDataState.theList[0]
                ListDataUnavailable01(
                    onClick = {
                        itemClickCallback?.onHolderItemClicked(textLabelItem, BaseActions.REFRESH, -1)
                    },
                )
            }
            else { Logger.getLogger("TextLabelContent01").log(Level.WARNING, "TextLabelContent01 [Unavailable] - listDataState.theList is empty!!") }

        }
        is ListDataState.None -> { Logger.getLogger("TextLabelContent01").log(Level.INFO, "TextLabelContent01 [None]") }
    }
}


@Preview
@Composable
fun TextLabelItem01(
    textLabelItem: TextLabelDelegate = TextLabelDelegateImpl(
        theIdentifier = generateRandomStringViaUuid(),
        theCellType = BaseCellTypes.ITEM,
    ),
    itemClickCallback: HolderItemClickDelegate<TextLabelDelegate>? = null,
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Blue012,
    backgroundShape: Shape = RoundedCornerShape(8.dp),
) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .background(
                color = backgroundColor,
                shape = backgroundShape,
            )
            .clickable {
                itemClickCallback?.onHolderItemClicked(
                    textLabelItem,
                    textLabelItem.theAction,
                    -1
                )
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = textLabelItem.theDescription,
            fontSize = textFontSize,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun TextLabelItem02(
    textLabelItem: TextLabelDelegate = TextLabelDelegateImpl(
        theIdentifier = generateRandomStringViaUuid(),
        theCellType = BaseCellTypes.ITEM,
    ),
    itemClickCallback: HolderItemClickDelegate<TextLabelDelegate>? = null,
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Blue012,
    backgroundShape: Shape = RoundedCornerShape(8.dp),
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .background(
                color = backgroundColor,
                shape = backgroundShape,
            )
            .clickable {
                itemClickCallback?.onHolderItemClicked(
                    textLabelItem,
                    textLabelItem.theAction,
                    -1
                )
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = textLabelItem.theIdentifier,
            fontSize = textFontSize,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = textLabelItem.theDescription,
            fontSize = textFontSize,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun TextLabelListDataContent01(
    listDataState: ListDataState<TextLabelDelegate> = ListDataState.Available(),
    itemClickCallback: HolderItemClickDelegate<TextLabelDelegate>? = null,
    lazyColumnContentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 16.dp,),
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Blue012,
    backgroundShape: Shape = RoundedCornerShape(8.dp),
) {
    val textLabelItemList = ImmutableObjectList<TextLabelDelegate>(listDataState.theList)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = lazyColumnContentPadding,
    ) {
        items(
            items = textLabelItemList.objectList,
            key = { textLabelItem: TextLabelDelegate -> textLabelItem.theIdentifier },
            contentType = { textLabelItem: TextLabelDelegate -> textLabelItem.theCellType },
        ) { textLabelItem: TextLabelDelegate ->
            when(textLabelItem.theCellType) {
                BaseCellTypes.ITEM -> {
                    TextLabelItem01(
                        textLabelItem = textLabelItem,
                        itemClickCallback = itemClickCallback,
                        textFontSize = textFontSize,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        backgroundShape = backgroundShape,
                    )
                }
            }
        }
    }
}

///

@Preview
@Composable
fun BuiltInLabelValueTextRow01(
    labelText: String = "Label",
    valueText: String = "Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text",
    fontSize: TextUnit = 20.sp,
    labelColor: Color = Blue001,
    valueColor: Color = Blue001,
    valueMaxLines: Int = 2,
    verticalPadding: Dp = 4.dp,
    referencedWidth: Dp = builtInMeasureTextWidth(text = labelText, style = TextStyle(fontSize = fontSize)),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            //.background(Pink002)
            .padding(vertical = verticalPadding)
            .height(IntrinsicSize.Min), // Allows children to take max height of each other
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = labelText,
            style = TextStyle(fontSize = fontSize),
            color = labelColor,
            textAlign = TextAlign.Start,
            maxLines = valueMaxLines,
            modifier = Modifier.wrapContentSize()
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = valueText,
            style = TextStyle(fontSize = fontSize),
            color = valueColor,
            textAlign = TextAlign.Start,
            maxLines = valueMaxLines,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )
    }
}


@Preview
@Composable
fun BuiltInLabelValueTextRow02(
    labelText: String = "Label",
    valueText: String = "Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text",
    fontSize: TextUnit = 20.sp,
    labelColor: Color = Blue001,
    valueColor: Color = Blue001,
    valueMaxLines: Int = 2,
    verticalPadding: Dp = 4.dp,
    referencedWidth: Dp = builtInMeasureTextWidth(text = labelText, style = TextStyle(fontSize = fontSize)),
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = verticalPadding),
    ) {

        val (label, value) = createRefs()

        Text(
            text = labelText,
            style = TextStyle(fontSize = fontSize),
            color = labelColor,
            textAlign = TextAlign.Start,
            maxLines = valueMaxLines,
            modifier = Modifier
                .constrainAs(label) {
                    linkTo(top = value.top, bottom = value.bottom, bias = 0f)
                    linkTo(start = parent.start, end = value.end, bias = 0f)
                    width = Dimension.value(referencedWidth)
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = valueText,
            style = TextStyle(fontSize = fontSize),
            color = valueColor,
            textAlign = TextAlign.Start,
            maxLines = valueMaxLines,
            modifier = Modifier
                .constrainAs(value) {
                    top.linkTo(parent.top)
                    linkTo(start = label.end, end = parent.end, bias = 0f, startMargin = 6.dp)
                    width = Dimension.fillToConstraints // 0dp
                    height = Dimension.wrapContent
                }
        )
    }
}


/**
 * * [Ref](https://stackoverflow.com/questions/70500071/is-it-possible-to-measure-string-width-to-properly-size-a-text-composable)
 */
@Composable
fun builtInMeasureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return with(LocalDensity.current) { widthInPixels.toDp() }
}


@TestOnly
@Composable
fun builtInMeasureTextHeight(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val heightInPixels = textMeasurer.measure(text, style).size.height
    return with(LocalDensity.current) { heightInPixels.toDp() }
}



