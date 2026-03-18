

@Composable
fun <T> ListDataContent01(
    listDataState: ListDataState<T> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    //unavailableOnClick: () -> Unit = {},
    initContent: @Composable () -> Unit = { ListDataInitContent01() },
    loadingContent: @Composable () -> Unit = { ListDataLoading01() },
    availableContent: @Composable (ListDataState<T>, HolderItemClickDelegate<T>?) -> Unit = { _, _ -> },
    unavailableContent: @Composable () -> Unit = { ListDataUnavailable01() },
) {
    when(listDataState) {
        is ListDataState.Init -> {
            Logger.getLogger("ListDataContent01").log(Level.INFO, "ListDataContent01 [Init]")
            initContent()
        }
        is ListDataState.Loading -> {
            Logger.getLogger("ListDataContent01").log(Level.INFO, "ListDataContent01 [Loading]")
            loadingContent()
        }
        is ListDataState.Available -> {
            Logger.getLogger("ListDataContent01").log(Level.INFO, "ListDataContent01 [Available]")
            availableContent(listDataState, itemClickCallback)
        }
        is ListDataState.Unavailable -> {
            Logger.getLogger("ListDataContent01").log(Level.INFO, "ListDataContent01 [Unavailable]")
            unavailableContent()
        }
        is ListDataState.None -> { Logger.getLogger("ListDataContent01").log(Level.INFO, "ListDataContent01 [None]") }
    }
}


@Preview
@Composable
fun ListDataInitContent01(
    initText: String = "Todo",
    onClick: () -> Unit = {},
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
) {
    Logger.getLogger("ListDataInitContent01").log(Level.INFO, "ListDataInitContent01")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor,),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = initText,
            fontSize = textFontSize,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 4,
            modifier = Modifier
                .padding(horizontal = 15.dp)

        )
    }
}


@Preview
@Composable
fun ListDataLoading01(
    progressColor: Color = Blue003,         // Pink001
    progressTrackColor: Color = Blue008,    // Yellow001
    backgroundColor: Color = Color.White,
) {
    Logger.getLogger("ListDataLoading01").log(Level.INFO, "ListDataLoading01")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor,),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = progressColor,
            trackColor = progressTrackColor,
        )
    }
}


@Preview
@Composable
fun ListDataUnavailable01(
    unavailableText: String = "Woos....Unavailable",
    onClick: () -> Unit = {},
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
) {
    Logger.getLogger("ListDataUnavailable01").log(Level.INFO, "ListDataLoading01")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor,),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column( // Child
            modifier = Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.chrome_crash_icon_150x150),
                contentDescription = "",
            )
            Box(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .wrapContentSize()
                    .padding(all = 10.dp),
            ) {
                Text(
                    text = unavailableText,
                    fontSize = textFontSize,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 4,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                )
            }
        }
    }
}


//@Preview
@Composable
fun <T> ListDataUnavailable02(
    delegate: T,
    itemClickAction: String = "",
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
) {
    ListDataUnavailable01(
        onClick = {
            itemClickCallback?.onHolderItemClicked(delegate, itemClickAction, -1)
        },
        textFontSize = textFontSize,
        textColor = textColor,
        backgroundColor = backgroundColor,
    )
}


//@Preview
@Composable
fun <T> ListDataUnavailable03(
    textLabelItem: TextLabelWrapperDelegate<T>,
    itemClickCallback: HolderItemClickDelegate<TextLabelWrapperDelegate<T>>? = null,
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
) {
    ListDataUnavailable01(
        onClick = {
            itemClickCallback?.onHolderItemClicked(textLabelItem, textLabelItem.theAction, -1)
        },
        textFontSize = textFontSize,
        textColor = textColor,
        backgroundColor = backgroundColor,
    )
}


