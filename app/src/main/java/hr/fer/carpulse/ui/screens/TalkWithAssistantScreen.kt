package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.common.assistant.AssistantResponse
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.smallLightText
import hr.fer.carpulse.ui.theme.smallThinText
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.viewmodel.TalkWithAssistantViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun TalkWithAssistantScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: TalkWithAssistantViewModel = getViewModel()
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppBackgroundColor)
    ) {

        Spacer(modifier = Modifier.height(65.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100)
                    ),
                onClick = {
                    //TODO: kill assistant
                    navigateBack()
                }
            ) {
                Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(text = stringResource(R.string.talk_with_assistant_screen_title), style = title)

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 30.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.talk_with_assistant_screen_content), style = title)

            Image(
                modifier = Modifier
                    .clickable { viewModel.talk(context) },
                painter = painterResource(R.drawable.assistant_logo_big),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (viewModel.isListening) {
                Icon(
                    painter = painterResource(R.drawable.ic_microphone),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            } else if (viewModel.fetchingResponse) {
                CircularProgressIndicator(modifier = Modifier.size(30.dp), color = LightGrayColor)
            } else {
                Spacer(modifier = Modifier.height(30.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(13.dp)
                    )
                    .padding(start = 13.dp, top = 12.dp, end = 18.dp, bottom = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                viewModel.conversation.forEach {
                    Row(modifier = modifier.fillMaxWidth()) {
                        Text(
                            text = "${if (it is AssistantRequest) "Q" else "A"}: ",
                            style = smallLightText
                        )
                        Text(
                            text = if (it is AssistantRequest) it.question else if (it is AssistantResponse) it.response else "",
                            style = smallThinText
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}