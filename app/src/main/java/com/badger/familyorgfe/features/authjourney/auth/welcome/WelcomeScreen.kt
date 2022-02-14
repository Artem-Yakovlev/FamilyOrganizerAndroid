package com.badger.familyorgfe.features.authjourney.auth.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier,
    viewModel: WelcomeViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {

                Text(
                    text = stringResource(R.string.welcome_title),
                    style = FamilyOrganizerTheme.textStyle.headline2,
                )

                Icon(
                    painter = painterResource(R.drawable.ic_cool),
                    tint = Color.Unspecified,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.welcome_funny_message),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp)
            )
        }

        Button(
            onClick = { },
            colors = buttonColors(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .padding(bottom = 20.dp)
        ) {

            Text(
                text = stringResource(R.string.welcome_next_text).uppercase(),
                color = FamilyOrganizerTheme.colors.whitePrimary,
                style = FamilyOrganizerTheme.textStyle.button,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }

}