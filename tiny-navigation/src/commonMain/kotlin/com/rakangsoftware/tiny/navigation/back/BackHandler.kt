package com.rakangsoftware.tiny.navigation.back

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(isEnabled: Boolean, onBack: ()-> Unit)