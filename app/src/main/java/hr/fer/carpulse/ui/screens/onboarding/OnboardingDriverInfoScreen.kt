package hr.fer.carpulse.ui.screens.onboarding

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.driver.Gender
import hr.fer.carpulse.ui.components.CarPulseTextField
import hr.fer.carpulse.ui.components.DropdownPicker
import hr.fer.carpulse.ui.components.NavigationCarousel
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.avatarBgColors
import hr.fer.carpulse.ui.theme.subtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.defaultKeyboardActions
import hr.fer.carpulse.viewmodel.OnboardingViewModel

@Composable
fun OnboardingDriverInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel,
    navigateToCarInfoScreen: () -> Unit,
    navigateToDriverSelfInfoScreen: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardActions = defaultKeyboardActions(focusManager)

    val driverData by viewModel.driverData.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = AppBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .padding(top = 72.dp, bottom = 19.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.onboarding_screen_driver_info_title),
                style = title,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = stringResource(R.string.onboarding_screen_driver_info_subtitle),
                style = subtitle
            )
        }

        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(bottom = 10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            // TODO: add avatars
            val tapToChangeColorText =
                stringResource(R.string.onboarding_screen_tap_to_change_avatar)

            Canvas(modifier = Modifier.size(250.dp)) {
                drawIntoCanvas {
                    val textPadding = 15.dp.toPx()
                    val arcHeight = 250.dp.toPx()
                    val arcWidth = 250.dp.toPx()

                    // Path for curved text
                    val path = Path().apply {
                        addArc(0f, textPadding, arcWidth, arcHeight, 180f, 180f)
                    }
                    it.nativeCanvas.drawTextOnPath(
                        tapToChangeColorText,
                        path,
                        0f,
                        0f,
                        Paint().apply {
                            textSize = 14.sp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            Icon(
                modifier = Modifier
                    .height(164.dp)
                    .width(169.dp),
                painter = painterResource(R.drawable.color_path),
                contentDescription = null,
                tint = avatarBgColors[viewModel.selectedColorIndex]
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 43.dp)
        ) {
            Text(
                text = stringResource(R.string.onboarding_screen_driver_name),
                style = subtitle
            )
            CarPulseTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.driverName,
                onChange = { viewModel.updateDriverName(it) },
                placeholder = "",
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = stringResource(R.string.onboarding_screen_driver_email),
                style = subtitle
            )
            CarPulseTextField(
                modifier = Modifier.fillMaxWidth(),
                value = driverData.email,
                onChange = {
                    viewModel.updateEmail(it)
                },
                placeholder = "",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email,
                keyboardActions = keyboardActions,
                readOnly = !viewModel.isOnboarding
            )

            Spacer(modifier = Modifier.height(7.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_driver_age),
                        style = subtitle
                    )
                    CarPulseTextField(
                        value = if (driverData.age == 0) "" else driverData.age.toString(),
                        onChange = { viewModel.updateAge(it) },
                        placeholder = "",
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                        keyboardActions = keyboardActions
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_driver_gender),
                        style = subtitle
                    )
                    DropdownPicker(
                        values = arrayOf("Male", "Female"), selectedItem =
                        if (driverData.gender == Gender.Male.value) {
                            "Male"
                        } else if (driverData.gender == Gender.Female.value){
                            "Female"
                        } else {
                            // Set default value if the user does not change the initial state
                            viewModel.updateGender(Gender.Female)
                            "Female"
                        },
                        onOptionSelected = {
                            if (it == "Male") {
                                viewModel.updateGender(Gender.Male)
                            } else {
                                viewModel.updateGender(Gender.Female)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            NavigationCarousel(
                carouselCount = 3,
                currentCarouselIndex = 1,
                onPreviousButtonClick = navigateToCarInfoScreen,
                onNextButtonClick = navigateToDriverSelfInfoScreen
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}