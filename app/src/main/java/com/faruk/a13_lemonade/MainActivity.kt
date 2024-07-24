package com.faruk.a13_lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faruk.a13_lemonade.ui.theme._13_lemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _13_lemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Lemonade", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(249, 228, 76)
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentStep) {
                in 1..4 -> {
                    LemonadeImageAndText(
                        textLabelResourceId = R.string.tree,
                        drawableResourceId = R.drawable.lemon_tree,
                        contentDescriptionResourceId = "Lemon Tree",
                        onImageClick = {
                            currentStep = 5
                            squeezeCount = (5..10).random()
                        },
                        textSqueezeCount = squeezeCount
                    )
                }
                in 5..10 -> {
                    LemonadeImageAndText(
                        drawableResourceId = R.drawable.lemon_squeeze,
                        textLabelResourceId = R.string.lemon,
                        contentDescriptionResourceId = "Lemon",
                        onImageClick = {
                            squeezeCount--
                            if (squeezeCount == 0) currentStep = 11
                        },
                        textSqueezeCount = squeezeCount
                    )
                }
                11 -> {
                    LemonadeImageAndText(
                        drawableResourceId = R.drawable.lemon_drink,
                        textLabelResourceId = R.string.lemonade,
                        contentDescriptionResourceId = "Lemonade",
                        onImageClick = {
                            currentStep = 12
                        },
                        textSqueezeCount = null
                    )
                }
                12 -> {
                    LemonadeImageAndText(
                        drawableResourceId = R.drawable.lemon_restart,
                        textLabelResourceId = R.string.empty_glass,
                        contentDescriptionResourceId = "Empty glass",
                        onImageClick = {
                            currentStep = 1
                        },
                        textSqueezeCount = null
                    )
                }
            }
        }
    }
}

@Composable
fun LemonadeImageAndText(
    drawableResourceId: Int,
    textLabelResourceId: Int,
    contentDescriptionResourceId: String,
    modifier: Modifier = Modifier,
    onImageClick: () -> Unit,
    textSqueezeCount: Int?
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (textSqueezeCount != null && textSqueezeCount != 0) {
                Text(
                    text = "$textSqueezeCount!",
                    fontSize = 44.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(195, 236, 210)),
                modifier = modifier
                    .border(
                        width = 4.dp,
                        color = Color(105,205,216) ,
                        shape = RoundedCornerShape(40.dp)
                    )

            ) {
                Image(
                    painter = painterResource(id = drawableResourceId),
                    contentDescription = contentDescriptionResourceId,

                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = textLabelResourceId),
                fontSize = 18.sp
            )
        }
    }
}
