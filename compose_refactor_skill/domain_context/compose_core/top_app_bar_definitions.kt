

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BuiltInTopAppBarScreenContent(
    topAppBarTitle: String = "Test",
    topAppBarContainerColor: Color = Blue001,
    topAppBarTitleContentColor: Color = Color.White,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bodyContent: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TopAppBar(
            title = { Text(text = topAppBarTitle) },
            //backgroundColor =  MaterialTheme.colors.primarySurface,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = topAppBarContainerColor,
                titleContentColor = topAppBarTitleContentColor,
            ),
            navigationIcon = navigationIcon,
            actions = actions,
        )

        bodyContent()
    }
}


@Preview
@Composable
fun TopAppBarNavigation01(
    imageVector: ImageVector = Icons.Filled.Menu,
    tint: Color = Color.White,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(imageVector = imageVector, tint = tint, contentDescription = "")
    }
}


