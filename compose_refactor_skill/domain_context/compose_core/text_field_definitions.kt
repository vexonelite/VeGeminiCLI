

fun DrawScope.drawUnderlineExt(underlineWidth: Dp = 1.dp, underlineColor: Color = Grey85, ) {
    val strokeWidth = underlineWidth.toPx()
    val y = size.height - (strokeWidth / 2f)
    drawLine(
        underlineColor,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth
    )
}


fun TextFieldValue.builtInTextFieldValueChangeHandler01(
    selectAllOnFocus: Boolean,
    focusIndicator: Int
): Pair<TextFieldValue, Int> {
    var focusIndicatorX: Int = focusIndicator
    Logger.getLogger("TextFieldValue Ktx").log(Level.INFO, "builtInTextFieldValueChangeHandler01() - onValueChange() - focusIndicator: [$focusIndicator], newValue： [${this.text}]")
    val returnedValue: TextFieldValue = if (selectAllOnFocus && (focusIndicatorX == 2)) {
        focusIndicatorX--
        TextFieldValue(
            text = this.text, selection = TextRange(0, this.text.length)
        )
    }
    else {
        TextFieldValue(
            text = this.text, selection = TextRange(this.text.length)
        )
    }

    return Pair<TextFieldValue, Int>(returnedValue, focusIndicatorX)
}


fun TextFieldValue.builtInTextFieldFocusChangedHandler01(
    selectAllOnFocus: Boolean,
    focusState: FocusState
): Pair<TextFieldValue, Int> {
    val focusIndicator: Int = if (focusState.isFocused) { 2 } else { 0 }
    Logger.getLogger("TextFieldValue Ktx").log(Level.INFO, "builtInTextFieldFocusChangedHandler01 - onFocusChanged() - isFocused： [${focusState.isFocused}], focusIndicator: [$focusIndicator]")

    val returnedValue: TextFieldValue = if (selectAllOnFocus && (focusIndicator == 2)) {
        TextFieldValue(
            text = this.text, selection = TextRange(0, this.text.length)
        )
    }
    else {
        TextFieldValue(
            text = this.text, selection = TextRange(this.text.length)
        )
    }

    return Pair<TextFieldValue, Int>(returnedValue, focusIndicator)
}


data class BuiltInTextField01Parameter(
    val textValue: TextFieldValue = TextFieldValue(),
    val onValueChange: (TextFieldValue) -> Unit = {},
    val hint: String = "Hint",
    val paddingHorizontal: Dp = 12.dp,
    val paddingVertical: Dp = 12.dp,
    val textColor: Color = Blue002,
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val underlineWidth: Dp = 2.dp,
    val underlineColor: Color = Blue,
    val backgroundColor: Color = Color.Transparent,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val singleLine: Boolean = true,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val selectAllOnFocus: Boolean = false,
    val onFocusChanged: (FocusState) -> Unit = {},
    val focusRequester: FocusRequester? = null
)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField01Modifier01(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField01Parameter,
): Modifier =
    Modifier.then(modifier)
        .background(parameter.backgroundColor)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField01Modifier02(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField01Parameter,
): Modifier =
    Modifier.then(createBuiltInTextField01Modifier01(modifier = modifier, parameter = parameter))
        .fillMaxWidth()
        .wrapContentHeight()


@Preview
@Composable
fun BuiltInTextField01(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField01Parameter = BuiltInTextField01Parameter()
) {
    Logger.getLogger("BuiltInTextField01").log(Level.INFO, "BuiltInTextField01 - textValue: [${parameter.textValue.text}]")

    val textStyle = TextStyle(
        color = parameter.textColor,
        fontWeight = parameter.fontWeight,
        fontSize = parameter.fontSize,
    )

    Row(
        modifier =
            Modifier.then(modifier)
                .drawBehind {
                    this.drawUnderlineExt(parameter.underlineWidth, parameter.underlineColor)
                }
                .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val textFieldModifier: Modifier = if (null != parameter.focusRequester) {
                Modifier.focusRequester(parameter.focusRequester)
            } else { Modifier }
            .then(
                if(parameter.selectAllOnFocus) {
                    Modifier.onFocusChanged(parameter.onFocusChanged)
                }
                else { Modifier }
            )
            .fillMaxWidth()
            .wrapContentHeight()

        BasicTextField(
            value = parameter.textValue,
            onValueChange = parameter.onValueChange,
            // https://proandroiddev.com/decorating-text-field-in-jetpack-compose-b033ade8ad6
            modifier = textFieldModifier,
            textStyle = textStyle,
            //colors = theAppTextFieldColor01(), // colors only works for TextField,
            keyboardOptions = parameter.keyboardOptions,
            keyboardActions = parameter.keyboardActions,
            visualTransformation = parameter.visualTransformation,
            singleLine = parameter.singleLine,
            enabled = parameter.enabled,
            readOnly = parameter.readOnly,
            decorationBox = { innerTextField ->
                if (parameter.textValue.text.isEmpty()) {
                    Text(
                        text = parameter.hint,
                        color = parameter.hintColor,
                        fontSize = parameter.fontSize,
                        fontWeight = parameter.fontWeight,
                    )
                }
                innerTextField()
            },
        )
    }
}


data class BuiltInTextField02Parameter(
    val textValue: TextFieldValue = TextFieldValue(),
    val onValueChange: (TextFieldValue) -> Unit = {},
    val hint: String = "Hint",
    val height: Dp = 0.dp,
    val paddingHorizontal: Dp = 12.dp,
    val paddingVertical: Dp = 12.dp,
    val textColor: Color = Blue002,
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val elevation: Dp = 3.dp,
    val cornerShape: Shape = RoundedCornerShape(4.dp),
    val borderEnabled: Boolean = true,
    val borderWidth: Dp = 1.dp,
    val borderColor: Color = Blue002,
    val backgroundColor: Color = Blue012,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val selectAllOnFocus: Boolean = false,
    val onFocusChanged: (FocusState) -> Unit = {},
    val focusRequester: FocusRequester? = null
)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField02Modifier01(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField02Parameter,
): Modifier =
    Modifier.then(modifier)
        .then(
            if (null != parameter.focusRequester) {
                Modifier.focusRequester(parameter.focusRequester)
            }
            else { Modifier }
        )
        .then(
            if(parameter.selectAllOnFocus) {
                Modifier.onFocusChanged(parameter.onFocusChanged)
            }
            else { Modifier }
        )
        .then(
            if (parameter.borderEnabled) {
                Modifier.border(parameter.borderWidth, SolidColor(parameter.borderColor), parameter.cornerShape)
            }
            else {
                Modifier.shadow(elevation = parameter.elevation, shape = parameter.cornerShape)
            }
        )
        .background(color = parameter.backgroundColor, shape = parameter.cornerShape)
        .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField02Modifier02(
    parameter: BuiltInTextField02Parameter,
): Modifier =
    createBuiltInTextField02Modifier01(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        parameter = parameter,
    )


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField02Modifier03(
    parameter: BuiltInTextField02Parameter,
): Modifier =
    createBuiltInTextField02Modifier01(
        modifier = Modifier
            .fillMaxWidth()
            .height(parameter.height),
        parameter = parameter,
    )


@Preview
@Composable
fun BuiltInTextField02(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField02Parameter = BuiltInTextField02Parameter(),
) {

    BasicTextField(
        modifier = modifier,
        value = parameter.textValue,
        onValueChange = parameter.onValueChange,
        textStyle = TextStyle(
            color = parameter.textColor,
            fontSize = parameter.fontSize,
            fontWeight = parameter.fontWeight
        ),
        decorationBox = { innerTextField ->
            if (parameter.textValue.text.isEmpty()) {
                Text(
                    text = parameter.hint,
                    color = parameter.hintColor,
                    fontSize = parameter.fontSize,
                    fontWeight = parameter.fontWeight,
                )
            }
            innerTextField()
        },
        keyboardOptions = parameter.keyboardOptions,
        keyboardActions = parameter.keyboardActions,
        visualTransformation = parameter.visualTransformation,
        singleLine = parameter.singleLine,
        enabled = parameter.enabled,
        readOnly = parameter.readOnly,
    )
}


data class BuiltInTextField03Parameter(
    val value: TextFieldValue = TextFieldValue(),
    val onValueChange: (TextFieldValue) -> Unit = {},
    val hint: String = "Hint",
    val paddingHorizontal: Dp = 12.dp,
    val paddingVertical: Dp = 12.dp,
    val textColor: Color = Blue002,
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val elevation: Dp = 3.dp,
    val cornerShape: Shape = RoundedCornerShape(4.dp),
    val borderEnabled: Boolean = true,
    val borderWidth: Dp = 1.dp,
    val borderColor: Color = Blue002,
    val backgroundColor: Color = Blue012,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val selectAllOnFocus: Boolean = false,
    val onFocusChanged: (FocusState) -> Unit = {},
    val focusRequester: FocusRequester? = null
)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInTextField03Modifier01(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField03Parameter,
): Modifier =
    Modifier.then(modifier)
        .then(
            if (parameter.borderEnabled) {
                Modifier.border(1.dp, SolidColor(parameter.borderColor), parameter.cornerShape)
            }
            else {
                Modifier.shadow(elevation = parameter.elevation, shape = parameter.cornerShape)
            }
        )
        .background(color = parameter.backgroundColor, shape = parameter.cornerShape)


@Preview
@Composable
fun BuiltInTextField03(
    modifier: Modifier = Modifier,
    parameter: BuiltInTextField03Parameter = BuiltInTextField03Parameter()
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val textModifier = if (null != parameter.focusRequester) {
                Modifier.focusRequester(parameter.focusRequester)
            }
            else { Modifier }
            .then(
                if(parameter.selectAllOnFocus) {
                    Modifier.onFocusChanged(parameter.onFocusChanged)
                }
                else { Modifier }
            )
            .fillMaxWidth()
            .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)

        BasicTextField(
            modifier = textModifier,
            value = parameter.value,
            onValueChange = parameter.onValueChange,
            textStyle = TextStyle(
                color = parameter.textColor,
                fontSize = parameter.fontSize,
                fontWeight = parameter.fontWeight
            ),
            decorationBox = { innerTextField ->
                if (parameter.value.text.isEmpty()) {
                    Text(
                        text = parameter.hint,
                        color = parameter.hintColor,
                        fontSize = parameter.fontSize,
                        fontWeight = parameter.fontWeight,
                    )
                }
                innerTextField()
            },
            keyboardOptions = parameter.keyboardOptions,
            keyboardActions = parameter.keyboardActions,
            visualTransformation = parameter.visualTransformation,
            singleLine = parameter.singleLine,
            enabled = parameter.enabled,
            readOnly = parameter.readOnly,
        )
    }
}

///


data class BuiltInOutlinedTextFieldParameter(
    val textValue: TextFieldValue = TextFieldValue(),
    val onValueChange: (TextFieldValue) -> Unit = {},
    val label: String = "Hint",                             // revision by elite_lin - 2025/12/07
    val placeholder: String = "", // ""default_content",    // added by elite_lin - 2025/12/07
    val height: Dp = 0.dp,
    val paddingHorizontal: Dp = 4.dp,
    val paddingVertical: Dp = 4.dp,
    val paddingFlag: Boolean = true,
    val textColor: Color = Blue002,
    val containerColor: Color = Blue012,    // added by elite_lin - 2025/05/12
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val elevation: Dp = 3.dp,
    val cornerShape: Shape = RoundedCornerShape(4.dp),
    val backgroundColor: Color = Blue012,
    val backgroundColorFlag: Boolean = true,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val selectAllOnFocus: Boolean = false,
    val onFocusChanged: (FocusState) -> Unit = {},
    val focusRequester: FocusRequester? = null,
)


data class BuiltInOutlinedTextFieldParameter02(
    val textValue: String = "",
    val onValueChange: (String) -> Unit = {},
    val label: String = "Hint",
    val placeholder: String = "", //""default_content",
    val height: Dp = 0.dp,
    val paddingHorizontal: Dp = 4.dp,
    val paddingVertical: Dp = 4.dp,
    val paddingFlag: Boolean = true,
    val textColor: Color = Blue002,
    val containerColor: Color = Blue012,    // added by elite_lin - 2025/05/12
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val elevation: Dp = 3.dp,
    val cornerShape: Shape = RoundedCornerShape(4.dp),
    val backgroundColor: Color = Blue012,
    val backgroundColorFlag: Boolean = true,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val selectAllOnFocus: Boolean = false,
    val onFocusChanged: (FocusState) -> Unit = {},
    val focusRequester: FocusRequester? = null,
)


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInOutlinedTextField01Modifier01(
    modifier: Modifier = Modifier,
    parameter: BuiltInOutlinedTextFieldParameter,
): Modifier =
    Modifier.then(modifier)
        .then(
            if (null != parameter.focusRequester) {
                Modifier.focusRequester(parameter.focusRequester)
            }
            else { Modifier }
        )
        .then(
            if(parameter.selectAllOnFocus) {
                Modifier.onFocusChanged(parameter.onFocusChanged)
            }
            else { Modifier }
        )
        .then(
            if (parameter.backgroundColorFlag) {
                Modifier.background(color = parameter.backgroundColor, shape = parameter.cornerShape)
            }
            else { Modifier }
        )
        .then(
            if (parameter.paddingFlag) {
                Modifier.padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)
            }
            else { Modifier }
        )


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInOutlinedTextField01Modifier011(
    parameter: BuiltInOutlinedTextFieldParameter,
) : Modifier =
    createBuiltInOutlinedTextField01Modifier01(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        parameter = parameter,
    )


@SuppressLint("ModifierFactoryExtensionFunction")
fun createBuiltInOutlinedTextField01Modifier02(
    modifier: Modifier = Modifier,
    parameter: BuiltInOutlinedTextFieldParameter02,
): Modifier =
    Modifier.then(modifier)
        .then(
            if (null != parameter.focusRequester) {
                Modifier.focusRequester(parameter.focusRequester)
            }
            else { Modifier }
        )
        .then(
            if(parameter.selectAllOnFocus) {
                Modifier.onFocusChanged(parameter.onFocusChanged)
            }
            else { Modifier }
        )
        .then(
            if (parameter.backgroundColorFlag) {
                Modifier.background(color = parameter.backgroundColor, shape = parameter.cornerShape)
            }
            else { Modifier }
        )
        .then(
            if (parameter.paddingFlag) {
                Modifier.padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)
            }
            else { Modifier }
        )


@Preview
@Composable
fun BuiltInOutlinedTextField01(
    modifier: Modifier = Modifier,
    parameter: BuiltInOutlinedTextFieldParameter = BuiltInOutlinedTextFieldParameter(),
    label: @Composable (() -> Unit)? = {
        Text(text = parameter.label, fontSize = parameter.fontSize)
    },
    placeholder: @Composable (() -> Unit)? = {
        Text(text = parameter.placeholder, fontSize = parameter.fontSize)
    },
) {
    OutlinedTextField(
        modifier = modifier,
        value = parameter.textValue,
        onValueChange = parameter.onValueChange,
        label = label,
        placeholder = placeholder,
        textStyle = TextStyle(
            color = parameter.textColor,
            fontSize = parameter.fontSize,
            fontWeight = parameter.fontWeight
        ),
        keyboardOptions = parameter.keyboardOptions,
        keyboardActions = parameter.keyboardActions,
        visualTransformation = parameter.visualTransformation,
        singleLine = parameter.singleLine,
        minLines = parameter.minLines,
        maxLines = parameter.maxLines,
        enabled = parameter.enabled,
        readOnly = parameter.readOnly,
        colors = theBuiltInOutlinedTextFieldColor01(containerColor = parameter.containerColor)
    )
}


@Preview
@Composable
fun BuiltInOutlinedTextField02(
    modifier: Modifier = Modifier,
    parameter: BuiltInOutlinedTextFieldParameter02 = BuiltInOutlinedTextFieldParameter02(),
    label: @Composable (() -> Unit)? = {
        Text(text = parameter.label, fontSize = parameter.fontSize)
    },
    placeholder: @Composable (() -> Unit)? = {
        Text(text = parameter.placeholder, fontSize = parameter.fontSize)
    },
) {
    OutlinedTextField(
        modifier = modifier,
        value = parameter.textValue,
        onValueChange = parameter.onValueChange,
        label = label,
        placeholder = placeholder,
        textStyle = TextStyle(
            color = parameter.textColor,
            fontSize = parameter.fontSize,
            fontWeight = parameter.fontWeight
        ),
        keyboardOptions = parameter.keyboardOptions,
        keyboardActions = parameter.keyboardActions,
        visualTransformation = parameter.visualTransformation,
        singleLine = parameter.singleLine,
        minLines = parameter.minLines,
        maxLines = parameter.maxLines,
        enabled = parameter.enabled,
        readOnly = parameter.readOnly,
        colors = theBuiltInOutlinedTextFieldColor01(containerColor = parameter.containerColor)
    )
}


data class BuiltInDropDownMenu01Parameter(
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val items: List<String> = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
    val hint: String = "Hint",
    val textColor: Color = Blue002,
    val hintColor: Color = Grey85,
    val paddingHorizontal: Dp = 12.dp,
    val paddingVertical: Dp = 12.dp,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val dropDownMenuBackgroundColor: Color = Blue012,
    val dropDownMenuTextColor: Color = Grey005,
    val dropDownMenuTextBackgroundColor: Color = Blue012,
    val dropDownMenuTextUnderlineWidth: Dp = 1.dp,
    val dropDownMenuTextUnderlineColor: Color = Grey85,
    val onDropDownMenuItemChange: (String) -> Unit = {},
    val focusRequester: FocusRequester? = null,
)


@Preview
@Composable
fun BuiltInDropDownMenu01(
    modifier: Modifier = Modifier,
    parameter: BuiltInDropDownMenu01Parameter = BuiltInDropDownMenu01Parameter()
) {
    var expanded by remember { mutableStateOf(false) }

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = parameter.textFieldValue,
            onValueChange = {  },
            label = {
                Text(
                    text = parameter.hint,
                    color = parameter.hintColor,
                    fontSize = parameter.fontSize,
                    fontWeight = parameter.fontWeight,
                )
            },
            textStyle = TextStyle(
                color = parameter.textColor,
                fontSize = parameter.fontSize,
                fontWeight = parameter.fontWeight
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = !expanded }
                )
            },
            colors = theBuiltInOutlinedTextFieldColor01(),
            readOnly = true, // key: preventing text input via the keyboard.
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.hide() // Hides the keyboard when the TextField gains focus
                    }
                }
                .then(
                    if (null != parameter.focusRequester) {
                        Modifier.focusRequester(parameter.focusRequester)
                    }
                    else { Modifier }
                ),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = parameter.dropDownMenuBackgroundColor)
        ) {
            parameter.items.forEach { label: String ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = label,
                            textAlign = TextAlign.Start,
                            color = parameter.dropDownMenuTextColor,
                            fontSize = parameter.fontSize,
                            fontWeight = parameter.fontWeight,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = parameter.dropDownMenuTextBackgroundColor)
                                .drawBehind {
                                    this.drawUnderlineExt(parameter.dropDownMenuTextUnderlineWidth, parameter.dropDownMenuTextUnderlineColor)
                                }
                                .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)
                        )
                    },
                    onClick = {
                        //selectedText = label
                        expanded = false
                        parameter.onDropDownMenuItemChange(label)
                    },
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                )
            }
        }
    }
}


data class BuiltInDropDownMenu02Parameter(
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val items: List<String> = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
    val hint: String = "Hint",
    val paddingHorizontal: Dp = 12.dp,
    val paddingVertical: Dp = 12.dp,
    val textColor: Color = Blue002,
    val hintColor: Color = Grey85,
    val fontSize: TextUnit = 20.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val elevation: Dp = 3.dp,
    val cornerShape: Shape = RoundedCornerShape(4.dp),
    val borderEnabled: Boolean = true,
    val borderWidth: Dp = 1.dp,
    val borderColor: Color = Blue002,
    val backgroundColor: Color = Blue012,
    val dropDownMenuBackgroundColor: Color = Blue012,
    val dropDownMenuTextColor: Color = Grey005,
    val dropDownMenuTextBackgroundColor: Color = Blue012,
    val dropDownMenuTextUnderlineWidth: Dp = 1.dp,
    val dropDownMenuTextUnderlineColor: Color = Grey85,
    val onDropDownMenuItemChange: (String) -> Unit = {},
)


@Preview
@Composable
fun BuiltInDropDownMenu02(
    modifier: Modifier = Modifier,
    parameter: BuiltInDropDownMenu02Parameter = BuiltInDropDownMenu02Parameter()
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    var selectedText: String by remember { mutableStateOf("") }

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .then(
                    if (parameter.borderEnabled) {
                        Modifier.border(1.dp, SolidColor(parameter.borderColor), parameter.cornerShape)
                    }
                    else {
                        Modifier.shadow(elevation = parameter.elevation, shape = parameter.cornerShape)
                    }
                )
                .background(color = parameter.backgroundColor, shape = parameter.cornerShape),
        ) {

            Box(
                Modifier.fillMaxWidth(),
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = parameter.backgroundColor)
                        .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.hide() // Hides the keyboard when the TextField gains focus
                            }
                        },
                    value = parameter.textFieldValue,
                    onValueChange = { },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        color = parameter.textColor,
                        fontSize = parameter.fontSize,
                        fontWeight = parameter.fontWeight
                    ),
                    readOnly = true, // key: preventing text input via the keyboard.
                    decorationBox = { innerTextField ->
                        if (selectedText.isEmpty()) {
                            Text(
                                text = parameter.hint,
                                color = parameter.hintColor,
                                fontSize = parameter.fontSize,
                                fontWeight = parameter.fontWeight,
                            )
                        }
                        innerTextField()
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,

                    ) {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = parameter.dropDownMenuBackgroundColor)
        ) {
            parameter.items.forEach { label: String ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = label,
                            textAlign = TextAlign.Start,
                            color = parameter.dropDownMenuTextColor,
                            fontSize = parameter.fontSize,
                            fontWeight = parameter.fontWeight,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = parameter.dropDownMenuTextBackgroundColor)
                                .drawBehind {
                                    this.drawUnderlineExt(parameter.dropDownMenuTextUnderlineWidth, parameter.dropDownMenuTextUnderlineColor)
                                }
                                .padding(horizontal = parameter.paddingHorizontal, vertical = parameter.paddingVertical)
                        )
                    },
                    onClick = {
                        //selectedText = label
                        expanded = false
                        selectedText = label
                        parameter.onDropDownMenuItemChange(label)
                    },
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                )
            }
        }
    }
}



