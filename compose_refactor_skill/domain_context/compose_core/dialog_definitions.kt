

enum class DialogType {
    Progress,
    SingleAction,
    TwinActions,
    ItemPicker,
    TextInput,
    None,
}


interface BuiltInDialogStateDelegate {
    val theDialogType: DialogType
    val theDialogState: Boolean

    val theTitle: String
    val theTitleFontSize: TextUnit
    val theTitleTextColor: Color

    val theMessage: String
    val theMessageFontSize: TextUnit
    val theMessageTextColor: Color

    val theConfirmTitle: String
    val theConfirmTitleFontSize: TextUnit
    val theConfirmTitleTextColor: Color
    val theConfirmBackgroundColor: Color

    val theCancelTitle: String
    val theCancelTitleFontSize: TextUnit
    val theCancelTitleTextColor: Color
    val theCancelBackgroundColor: Color

    val onDismiss: () -> Unit
    val onConfirm: () -> Unit
}


data class BuiltInDialogStateImpl(
    override val theDialogType: DialogType = DialogType.None,
    override val theDialogState: Boolean = false,

    override val theTitle: String = "Title",
    override val theTitleFontSize: TextUnit = 26.sp,
    override val theTitleTextColor: Color = Blue002,

    override val theMessage: String = "Message",
    override val theMessageFontSize: TextUnit = 26.sp,
    override val theMessageTextColor: Color = Blue002,

    override val theConfirmTitle: String = "Confirm",
    override val theConfirmTitleFontSize: TextUnit = 26.sp,
    override val theConfirmTitleTextColor: Color = Blue002,
    override val theConfirmBackgroundColor: Color = Color.Unspecified,

    override val theCancelTitle: String = "Cancel",
    override val theCancelTitleFontSize: TextUnit = 26.sp,
    override val theCancelTitleTextColor: Color = Grey005,
    override val theCancelBackgroundColor: Color = Color.Unspecified,

    override val onDismiss: () -> Unit = {},
    override val onConfirm: () -> Unit = {},
) : BuiltInDialogStateDelegate


interface MutableBuiltInDialogStateDelegate: BuiltInDialogStateDelegate {

    override var theDialogState: Boolean

    override var theTitle: String
    override var theTitleFontSize: TextUnit
    override var theTitleTextColor: Color

    override var theMessage: String
    override var theMessageFontSize: TextUnit
    override var theMessageTextColor: Color

    override var theConfirmTitle: String
    override var theConfirmTitleFontSize: TextUnit
    override var theConfirmTitleTextColor: Color
    override var theConfirmBackgroundColor: Color

    override var theCancelTitle: String
    override var theCancelTitleFontSize: TextUnit
    override var theCancelTitleTextColor: Color
    override var theCancelBackgroundColor: Color

    override var onDismiss: () -> Unit
    override var onConfirm: () -> Unit
}


data class MutableBuiltInDialogStateImpl(
    override val theDialogType: DialogType = DialogType.None,
    override var theDialogState: Boolean = false,

    override var theTitle: String = "Title",
    override var theTitleFontSize: TextUnit = 26.sp,
    override var theTitleTextColor: Color = Blue002,

    override var theMessage: String = "Message",
    override var theMessageFontSize: TextUnit = 26.sp,
    override var theMessageTextColor: Color = Blue002,

    override var theConfirmTitle: String = "Confirm",
    override var theConfirmTitleFontSize: TextUnit = 26.sp,
    override var theConfirmTitleTextColor: Color = Blue002,
    override var theConfirmBackgroundColor: Color = Color.Unspecified,

    override var theCancelTitle: String = "Cancel",
    override var theCancelTitleFontSize: TextUnit = 26.sp,
    override var theCancelTitleTextColor: Color = Grey005,
    override var theCancelBackgroundColor: Color = Color.Unspecified,

    override var onDismiss: () -> Unit = {},
    override var onConfirm: () -> Unit = {},
) : MutableBuiltInDialogStateDelegate


fun MutableBuiltInDialogStateDelegate.toBuiltInDialogStateDelegate(): BuiltInDialogStateDelegate =
    BuiltInDialogStateImpl(
        theDialogType = this.theDialogType,
        theDialogState = this.theDialogState,

        theTitle = this.theTitle,
        theTitleFontSize = this.theTitleFontSize,
        theTitleTextColor = this.theTitleTextColor,

        theMessage = this.theMessage,
        theMessageFontSize = this.theMessageFontSize,
        theMessageTextColor = this.theMessageTextColor,
        theConfirmTitle = this.theConfirmTitle,
        theConfirmTitleFontSize = this.theConfirmTitleFontSize,
        theConfirmTitleTextColor = this.theConfirmTitleTextColor,
        theConfirmBackgroundColor = this.theConfirmBackgroundColor,

        theCancelTitle = this.theCancelTitle,
        theCancelTitleFontSize = this.theCancelTitleFontSize,
        theCancelTitleTextColor = this.theCancelTitleTextColor,
        theCancelBackgroundColor = this.theCancelBackgroundColor,

        onDismiss = this.onDismiss,
        onConfirm = this.onConfirm,
    )


interface BuiltInWrapperDialogStateDelegate<T>: BuiltInDialogStateDelegate {
    val theWrappedObject: T?
    val onConfirmWithWrappedObject: (T) -> Unit
}


data class BuiltInWrapperDialogStateImpl<T>(
    override val theWrappedObject: T? = null,

    override val theDialogType: DialogType = DialogType.None,
    override val theDialogState: Boolean = true,

    override val theTitle: String = "Title",
    override val theTitleFontSize: TextUnit = 26.sp,
    override val theTitleTextColor: Color = Blue002,

    override val theMessage: String = "Message",
    override val theMessageFontSize: TextUnit = 26.sp,
    override val theMessageTextColor: Color = Blue002,

    override val theConfirmTitle: String = "Confirm",
    override val theConfirmTitleFontSize: TextUnit = 26.sp,
    override val theConfirmTitleTextColor: Color = Blue002,
    override val theConfirmBackgroundColor: Color = Color.Unspecified,

    override val theCancelTitle: String = "Cancel",
    override val theCancelTitleFontSize: TextUnit = 26.sp,
    override val theCancelTitleTextColor: Color = Grey005,
    override val theCancelBackgroundColor: Color = Color.Unspecified,

    override val onDismiss: () -> Unit = {},
    override val onConfirm: () -> Unit = {},
    override val onConfirmWithWrappedObject: (T) -> Unit = { _ -> }

) : BuiltInWrapperDialogStateDelegate<T>


interface BuiltInItemPickerDialogStateDelegate: BuiltInDialogStateDelegate {
    val theListDataState: ListDataState<TextLabelDelegate>
    val theListItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>?
    val theListItemTextFontSize: TextUnit
    val theListItemTextColor: Color
    val theListItemBackgroundColor: Color
    val theListItemBackgroundShape: Shape
}


data class BuiltInItemPickerDialogStateImpl(
    override val theDialogType: DialogType = DialogType.None,
    override val theDialogState: Boolean = false,

    override val theTitle: String = "Title",
    override val theTitleFontSize: TextUnit = 26.sp,
    override val theTitleTextColor: Color = Blue002,

    override val theMessage: String = "Message",
    override val theMessageFontSize: TextUnit = 26.sp,
    override val theMessageTextColor: Color = Blue002,

    override val theConfirmTitle: String = "Confirm",
    override val theConfirmTitleFontSize: TextUnit = 26.sp,
    override val theConfirmTitleTextColor: Color = Blue002,
    override val theConfirmBackgroundColor: Color = Color.Unspecified,

    override val theCancelTitle: String = "Cancel",
    override val theCancelTitleFontSize: TextUnit = 26.sp,
    override val theCancelTitleTextColor: Color = Grey005,
    override val theCancelBackgroundColor: Color = Color.Unspecified,

    override val onDismiss: () -> Unit = {},
    override val onConfirm: () -> Unit = {},

    override val theListDataState: ListDataState<TextLabelDelegate> = ListDataState.Available(),
    override val theListItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>? = null,
    override val theListItemTextFontSize: TextUnit = 20.sp,
    override val theListItemTextColor: Color = Color.Black,
    override val theListItemBackgroundColor: Color = Blue012,
    override val theListItemBackgroundShape: Shape = RoundedCornerShape(8.dp),

): BuiltInItemPickerDialogStateDelegate


@Composable
fun BuiltInCustomDialog01(
    onDismissRequest: () -> Unit,
    dialogContent: @Composable () -> Unit,
    dialogBackgroundColor: Color = Color.White,
    dialogShape: Shape = ShapeDefaults.Medium,
    isFillMaxSize: Boolean = true,
    horizontalDialogPadding: Dp = 40.dp,
    verticalDialogPadding: Dp = 80.dp,
) {
    Logger.getLogger("BuiltInCustomDialog01").log(Level.INFO, "BuiltInCustomDialog01")

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.padding(vertical = 32.dp)
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                onClick = onDismissRequest,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = if (isFillMaxSize) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.wrapContentSize()
                }
                .padding(horizontal = horizontalDialogPadding, vertical = verticalDialogPadding)
                .background(color = dialogBackgroundColor, shape = dialogShape)
                .graphicsLayer {
                    shape = dialogShape
                    clip = true
                },
        ) {
            dialogContent()
        }
    }
}


// Not be tested yet - elite_lin - 2024/09/06
@Composable
fun BuiltInCustomDialog02(
    onDismissRequest: () -> Unit,
    dialogContent: @Composable () -> Unit,
    dialogBackgroundColor: Color = Color.White,
    dialogShape: Shape = ShapeDefaults.Medium,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Logger.getLogger("BuiltInCustomDialog02").log(Level.INFO, "BuiltInCustomDialog02")

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside),
    ) {
        Card(
            modifier = Modifier
                //.padding(all = 16.dp) // when using wrapContentXXXX(), padding() has no effect!!
                .wrapContentSize(unbounded = true),
            shape = dialogShape,
            colors = CardDefaults.cardColors().copy(
                containerColor = dialogBackgroundColor,
            ),
        ) {
            dialogContent()
        }
    }
}


@Preview
@Composable
fun BuiltInProgressDialog02(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "Loading"
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    progressColor: Color = Blue003,         // Pink001
    progressTrackColor: Color = Blue008,    // Yellow001
) {
    Logger.getLogger("BuiltInProgressDialog01").log(Level.INFO, "BuiltInProgressDialog01 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")
    if (dialogState.theDialogType != DialogType.Progress) { return }
    if (!dialogState.theDialogState) { return }

    BuiltInCustomDialog01(
        onDismissRequest = onDismiss,
        dialogContent = {
            BuiltInProgressContent01(
                dialogState = dialogState,
                progressColor = progressColor,
                progressTrackColor = progressTrackColor,
            )
        },
        isFillMaxSize = false
    )
}


@Preview
@Composable
fun BuiltInProgressDialog01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "Loading"
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    progressColor: Color = Blue003,         // Pink001
    progressTrackColor: Color = Blue008,    // Yellow001
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Logger.getLogger("BuiltInProgressDialog01").log(Level.INFO, "BuiltInProgressDialog01 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")
    if (dialogState.theDialogType != DialogType.Progress) { return }
    if (!dialogState.theDialogState) { return }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside),
    ) {
        Card(
            modifier = Modifier
                //.padding(all = 16.dp) // when using wrapContentXXXX(), padding() has no effect!!
                .wrapContentSize(unbounded = true),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
        ) {
            BuiltInProgressContent01(
                dialogState = dialogState,
                progressColor = progressColor,
                progressTrackColor = progressTrackColor,
            )
        }
    }
}


@Preview
@Composable
fun BuiltInProgressContent01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
    progressColor: Color = Blue003,         // Pink001
    progressTrackColor: Color = Blue008,    // Yellow001
) {
    Column(
        modifier = Modifier.padding(all = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = progressColor,
            trackColor = progressTrackColor,
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Text(
            text = dialogState.theMessage,
            fontSize = dialogState.theMessageFontSize,
            color = dialogState.theMessageTextColor,
            //modifier = Modifier.padding(16.dp),
        )
    }
}

///

@Preview
@Composable
fun BuiltInTitleOnlyContent02(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
        text = dialogState.theTitle,
        fontSize = dialogState.theTitleFontSize,
        color = dialogState.theTitleTextColor,
        fontWeight = FontWeight.Bold,
    )
}


@Preview
@Composable
fun BuiltInTitleOnlyContent01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = dialogState.theTitle,
            fontSize = dialogState.theTitleFontSize,
            color = dialogState.theTitleTextColor,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Preview
@Composable
fun BuiltInMessageOnlyContent02(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
        text = dialogState.theMessage,
        fontSize = dialogState.theMessageFontSize,
        color = dialogState.theMessageTextColor,
    )
}


@Preview
@Composable
fun BuiltInMessageOnlyContent01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = dialogState.theMessage,
            fontSize = dialogState.theMessageFontSize,
            color = dialogState.theMessageTextColor,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Preview
@Composable
fun BuiltInTitleMessageContent01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = dialogState.theTitle,
            fontSize = dialogState.theTitleFontSize,
            color = dialogState.theTitleTextColor,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = dialogState.theMessage,
            fontSize = dialogState.theMessageFontSize,
            color = dialogState.theMessageTextColor,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Preview
@Composable
fun BuiltInSingleActionBottom01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
) {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.Gray,
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onConfirm,),
        //shape = MaterialTheme.shapes.small,
        //shape = RoundedCornerShape(10.dp),
        color = dialogState.theConfirmBackgroundColor,
        //contentColor = Color.White,
        //border = BorderStroke(2.dp, Blue007),
    ) {
        Text(
            text = dialogState.theConfirmTitle,
            textAlign = TextAlign.Center,
            color = dialogState.theConfirmTitleTextColor,
            fontSize = dialogState.theConfirmTitleFontSize,
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}


@Preview
@Composable
fun BuiltInTwinActionsBottom01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
) {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.Gray,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // needed - make sure have enough height to accommodate the ``VerticalDivider``
    ) {
        Surface(
            modifier = Modifier
                .clickable(onClick = onDismiss,)
                .weight(1f),
            //shape = MaterialTheme.shapes.small,
            //shape = RoundedCornerShape(10.dp),
            color = dialogState.theCancelBackgroundColor,
            //contentColor = Color.White,
            //border = BorderStroke(2.dp, Blue007),
        ) {
            Text(
                text = dialogState.theCancelTitle,
                textAlign = TextAlign.Center,
                color = dialogState.theCancelTitleTextColor,
                fontSize = dialogState.theCancelTitleFontSize,
                modifier = Modifier.padding(all = 16.dp),
            )
        }

        VerticalDivider(
            //modifier = Modifier.fillMaxHeight().width(1.dp),
            thickness = 1.dp,
            color = Color.Gray,
        )

        Surface(
            modifier = Modifier
                .clickable(onClick = onConfirm,)
                .weight(1f),
            //shape = MaterialTheme.shapes.small,
            //shape = RoundedCornerShape(10.dp),
            color = dialogState.theConfirmBackgroundColor,
            //contentColor = Color.White,
            //border = BorderStroke(2.dp, Blue007),
        ) {
            Text(
                text = dialogState.theConfirmTitle,
                textAlign = TextAlign.Center,
                color = dialogState.theConfirmTitleTextColor,
                fontSize = dialogState.theConfirmTitleFontSize,
                modifier = Modifier.padding(all = 16.dp),
            )
        }
    }
}

///

@Preview
@Composable
fun BuiltInSingleActionDialog02(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
) {
    Logger.getLogger("BuiltInSingleActionDialog02").log(Level.INFO, "BuiltInSingleActionDialog02 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")
    if (dialogState.theDialogType != DialogType.SingleAction) { return }
    if (!dialogState.theDialogState) { return }

    BuiltInCustomDialog01(
        onDismissRequest = onDismiss,
        dialogContent = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                BuiltInTitleMessageContent01(dialogState = dialogState,)

                BuiltInSingleActionBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        },
        isFillMaxSize = false
    )
}


@Preview
@Composable
fun BuiltInSingleActionDialog01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Logger.getLogger("BuiltInSingleActionDialog01").log(Level.INFO, "BuiltInSingleActionDialog01 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")
    if (dialogState.theDialogType != DialogType.SingleAction) { return }
    if (!dialogState.theDialogState) { return }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(all = 16.dp),  // when using wrapContentXXXX(), padding() has no effect!!
                .wrapContentHeight(unbounded = true),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                BuiltInTitleMessageContent01(dialogState = dialogState,)

                BuiltInSingleActionBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        }
    }
}

///

@Preview
@Composable
fun BuiltInTwinActionsDialog02(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.TwinActions,
        theDialogState = true,
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
) {
    Logger.getLogger("BuiltInTwinActionsDialog02").log(Level.INFO, "BuiltInTwinActionsDialog02 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")

    if (dialogState.theDialogType != DialogType.TwinActions) { return }
    if (!dialogState.theDialogState) { return }

    BuiltInCustomDialog01(
        onDismissRequest = onDismiss,
        dialogContent = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BuiltInTitleMessageContent01(dialogState = dialogState,)

                BuiltInTwinActionsBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        },
        isFillMaxSize = false
    )
}


@Preview
@Composable
fun BuiltInTwinActionsDialog01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.TwinActions,
        theDialogState = true,
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Logger.getLogger("BuiltInTwinActionsDialog01").log(Level.INFO, "BuiltInTwinActionsDialog01 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")

    if (dialogState.theDialogType != DialogType.TwinActions) { return }
    if (!dialogState.theDialogState) { return }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside),
    ) {
        Card(
            modifier = Modifier
                //.padding(all = 16.dp),  // when using wrapContentXXXX(), padding() has no effect!!
                .wrapContentHeight(unbounded = true),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BuiltInTitleMessageContent01(dialogState = dialogState,)

                BuiltInTwinActionsBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        }
    }
}

///

@Preview
@Composable
fun BuiltInItemPickerDialog02(
    dialogState: BuiltInItemPickerDialogStateDelegate = BuiltInItemPickerDialogStateImpl(),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Blue012,
    backgroundShape: Shape = RoundedCornerShape(8.dp),
    dialogBackgroundColor: Color = Color.White,
) {
    Logger.getLogger("BuiltInItemPickerDialog02").log(Level.INFO, "BuiltInItemPickerDialog02 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")

    if (dialogState.theDialogType != DialogType.ItemPicker) { return }
    if (!dialogState.theDialogState) { return }

    BuiltInCustomDialog01(
        onDismissRequest = onDismiss,
        dialogBackgroundColor = dialogBackgroundColor,
        dialogContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                BuiltInTitleOnlyContent02(dialogState)

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TextLabelListDataContent01(
                        lazyColumnContentPadding = PaddingValues(horizontal = 8.dp,),
                        listDataState = dialogState.theListDataState,
                        itemClickCallback = dialogState.theListItemClickCallback,
                        textFontSize = textFontSize,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        backgroundShape = backgroundShape,
                    )
                }

                BuiltInSingleActionBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        },
    )
}


@Preview
@Composable
fun BuiltInItemPickerDialog01(
    dialogState: BuiltInItemPickerDialogStateDelegate = BuiltInItemPickerDialogStateImpl(
        theConfirmTitle = "Cancel",
        theConfirmTitleFontSize = 26.sp,
        theConfirmTitleTextColor = Color.White,
        theConfirmBackgroundColor = Blue002,
    ),
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
    dialogBackgroundColor: Color = Color.White,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Logger.getLogger("BuiltInItemPickerDialog01").log(Level.INFO, "BuiltInItemPickerDialog01 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")

    if (dialogState.theDialogType != DialogType.ItemPicker) { return }
    if (!dialogState.theDialogState) { return }

    Dialog(
        onDismissRequest = dialogState.onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside),
    ) {
        Card(
            modifier = Modifier
                .padding(vertical = 32.dp) // when using wrapContentXXXX(), padding() has no effect!!
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .background(color = dialogBackgroundColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                BuiltInTitleOnlyContent02(dialogState)

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TextLabelListDataContent01(
                        lazyColumnContentPadding = PaddingValues(all = 0.dp,),
                        listDataState = dialogState.theListDataState,
                        itemClickCallback = dialogState.theListItemClickCallback
                    )
                }

                BuiltInSingleActionBottom01(
                    dialogState = dialogState, onDismiss = onDismiss, onConfirm = onConfirm,
                )
            }
        }
    }
}

///

@Preview
@Composable
fun BuiltInTextInputDialog02(
    dialogState: BuiltInWrapperDialogStateDelegate<String> = BuiltInWrapperDialogStateImpl<String>(
        theDialogType = DialogType.TextInput,
        theDialogState = true,
    ),
    singleLine: Boolean = false,
    textFieldHeight: Dp = 250.dp,
    textFieldHint: String = "Hello",
    textFieldSelectAllOnFocus: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Email,
    submitResultOnDone: Boolean = false,
) {
    Logger.getLogger("BuiltInTextInputDialog02").log(Level.INFO, "BuiltInTextInputDialog02 - theDialogType: [${dialogState.theDialogType}], theDialogState: [${dialogState.theDialogState}]")

    if (dialogState.theDialogType != DialogType.TextInput) { return }
    if (!dialogState.theDialogState) { return }

    val initText = if (null != dialogState.theWrappedObject) { dialogState.theWrappedObject!! } else { "" }
    var inputValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var inputFocusIndicator by remember { mutableIntStateOf(0) }
    val inputFocusRequester: FocusRequester = remember { FocusRequester() }

    val onValueChange: (TextFieldValue) -> Unit  = { newValue: TextFieldValue ->
        inputValue = newValue
        Logger.getLogger("BuiltInTextInputDialog02").log(Level.INFO, "BuiltInTextInputDialog02() - onValueChange() - inputFocusIndicator: [$inputFocusIndicator], inputValue： [${inputValue.text}]")
    }

    val selectAllOnFocusValueChange: (TextFieldValue) -> Unit  = { newValue: TextFieldValue ->
        val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, inputFocusIndicator)
        inputFocusIndicator = pair.second
        inputValue = pair.first
        Logger.getLogger("BuiltInTextInputDialog02").log(Level.INFO, "BuiltInTextInputDialog02() - selectAllOnFocusValueChange() - inputFocusIndicator: [$inputFocusIndicator], inputValue： [${inputValue.text}]")
    }

    val onFocusChanged: (FocusState) -> Unit = {}
    val selectAllOnFocusChanged: (FocusState) -> Unit = {
        val pair: Pair<TextFieldValue, Int> = inputValue.builtInTextFieldFocusChangedHandler01(true, it)
        inputFocusIndicator = pair.second
        inputValue = pair.first
        Logger.getLogger("BuiltInTextInputDialog02").log(Level.INFO, "BuiltInTextInputDialog02() - onFocusChanged() - inputFocusIndicator: [$inputFocusIndicator], inputValue： [${inputValue.text}]")
    }

    BuiltInCustomDialog01(
        onDismissRequest = dialogState.onDismiss,
        dialogContent = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                BuiltInTitleOnlyContent02(dialogState)

                Box(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 14.dp),
                ) {
                    val parameter = BuiltInTextField02Parameter(
                        textValue = inputValue,
                        fontSize = dialogState.theMessageFontSize,
                        onValueChange = if (textFieldSelectAllOnFocus) { selectAllOnFocusValueChange } else { onValueChange },
                        cornerShape = RoundedCornerShape(4.dp),
                        //backgroundColor = Yellow002,
                        hint = textFieldHint,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = if (singleLine) { ImeAction.Done } else { ImeAction.Default }
                        ),
                        keyboardActions = if (submitResultOnDone) {
                                KeyboardActions(onDone = {
                                    dialogState.onConfirmWithWrappedObject(inputValue.text)
                                })
                            }
                            else { KeyboardActions.Default },
                        selectAllOnFocus = textFieldSelectAllOnFocus,
                        onFocusChanged = if (textFieldSelectAllOnFocus) { selectAllOnFocusChanged } else { onFocusChanged },
                        focusRequester = inputFocusRequester,
                        singleLine = singleLine,
                        height = textFieldHeight,
                    )

                    val textFieldModifier: Modifier = if ((!singleLine) && (textFieldHeight > 0.dp)) {
                        Modifier.fillMaxWidth()
                                .height(textFieldHeight)
                                .padding(horizontal = 10.dp)
                    }
                    else {
                        Modifier.fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 10.dp)
                    }
                    BuiltInTextField02(
                        modifier = createBuiltInTextField02Modifier01(
                            modifier = textFieldModifier, parameter = parameter,),
                        parameter = parameter,
                    )
                }

                BuiltInTwinActionsBottom01(
                    dialogState = dialogState,
                    onDismiss = dialogState.onDismiss,
                    onConfirm = {
                        dialogState.onConfirmWithWrappedObject(inputValue.text)
                    },
                )
            }
        },
        isFillMaxSize = false
    )
}

///

@Preview
@Composable
fun BuiltInDialogSet01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
    ),
    //theParameters: RfidTagParameters = RfidTagParameters.None,
    onDismiss: () -> Unit = dialogState.onDismiss,
    onConfirm: () -> Unit = dialogState.onConfirm,
    progressDismissOnBackPress: Boolean = false,
    progressDismissOnClickOutside: Boolean = false,
    singleActionDismissOnBackPress: Boolean = false,
    singleActionDismissOnClickOutside: Boolean = false,
    twinActionsDismissOnBackPress: Boolean = false,
    twinActionsDismissOnClickOutside: Boolean = false,
) {
    Logger.getLogger("BuiltInDialogSet01").log(Level.INFO, "BuiltInDialogSet01 [-1]")
    if (!dialogState.theDialogState) { return }

    when(dialogState.theDialogType) {
        DialogType.Progress -> {
            Logger.getLogger("BuiltInDialogSet01").log(Level.INFO, "BuiltInDialogSet01 [0] - [Progress]")
            BuiltInProgressDialog01(
                dialogState = dialogState,
                onDismiss = onDismiss,
                dismissOnBackPress = progressDismissOnBackPress,
                dismissOnClickOutside = progressDismissOnClickOutside,
            )
        }
        DialogType.SingleAction -> {
            Logger.getLogger("BuiltInDialogSet01").log(Level.INFO, "BuiltInDialogSet01 [0] - [SingleAction]")
            BuiltInSingleActionDialog01(
                dialogState = dialogState,
                onDismiss = onDismiss,
                onConfirm = onConfirm,
                dismissOnBackPress = singleActionDismissOnBackPress,
                dismissOnClickOutside = singleActionDismissOnClickOutside,
            )
        }
        DialogType.TwinActions -> {
            Logger.getLogger("BuiltInDialogSet01").log(Level.INFO, "BuiltInDialogSet01 [0] - [TwinActions]")
            BuiltInTwinActionsDialog01(
                dialogState = dialogState,
                onDismiss = onDismiss,
                onConfirm = onConfirm,
                dismissOnBackPress = twinActionsDismissOnBackPress,
                dismissOnClickOutside = twinActionsDismissOnClickOutside,
            )
        }
        else -> {
            Logger.getLogger("BuiltInDialogSet01").log(Level.INFO, "BuiltInDialogSet01 [0] - [Else]")
        }
    }
}


@Composable
fun BuiltInDialogSet02(
    progressDialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theMessage = "Loading"
    ),
    singleActionDialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
    ),
    twinActionsDialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.TwinActions,
    ),
) {
    BuiltInProgressDialog01(progressDialogState,)

    BuiltInSingleActionDialog01(singleActionDialogState,)

    BuiltInTwinActionsDialog01(twinActionsDialogState,)
}


