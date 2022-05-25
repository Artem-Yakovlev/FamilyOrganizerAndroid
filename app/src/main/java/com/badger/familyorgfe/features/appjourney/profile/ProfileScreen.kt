package com.badger.familyorgfe.features.appjourney.profile

import android.Manifest
import android.graphics.Bitmap.CompressFormat
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.UserStatus
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.ui.theme.StatusAtHomeColor
import com.badger.familyorgfe.ui.theme.WhitePrimary
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


private const val EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
private const val LAUNCHER_PATH = "image/*"

@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: IProfileViewModel = hiltViewModel<ProfileViewModel>()
) {
    val showLogoutDialog by viewModel.showLogoutDialog.collectAsState()
    val editFamilyMemberDialog by viewModel.editFamilyMemberDialog.collectAsState()
    val excludeFamilyMemberDialog by viewModel.excludeFamilyMemberDialog.collectAsState()
    val statusMenuShowed by viewModel.changeStatusDialog.collectAsState()



    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar(onLogoutClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutClick) })

        val externalStoragePermissionState = rememberPermissionState(EXTERNAL_STORAGE_PERMISSION)

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            it?.let { uri ->
                val bitmap = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    MediaStore.Images
                        .Media.getBitmap(context.contentResolver, uri)

                } else {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder
                            .createSource(context.contentResolver, uri)
                    )
                }

                val f = File(context.cacheDir, "filename")
                f.createNewFile()

                val bos = ByteArrayOutputStream()
                bitmap.compress(CompressFormat.JPEG, 100 , bos)
                val bitmapdata: ByteArray = bos.toByteArray()

                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(f)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    fos?.write(bitmapdata)
                    fos?.flush()
                    fos?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                viewModel.onEvent(
                    IProfileViewModel.Event.OnProfileImageChanged(f)
                )
            }
        }

        val mainFamilyMember by viewModel.mainUser.collectAsState()
        val members by viewModel.members.collectAsState()

        MainUserItem(
            familyMember = mainFamilyMember,
            statusMenuShowed = statusMenuShowed,
            showStatusMenu = { show ->
                viewModel.onEvent(
                    IProfileViewModel.Event.ShowStatusMenu(show)
                )
            },
            onImageChangeClicked = {
                if (externalStoragePermissionState.status.isGranted) {
                    launcher.launch(LAUNCHER_PATH)
                } else {
                    externalStoragePermissionState.launchPermissionRequest()
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(members) { item ->
                FamilyMemberItem(
                    familyMember = item,
                    onEditMemberClicked = { familyMember ->
                        viewModel.onEvent(
                            IProfileViewModel.Event.OnEditMemberClicked(familyMember)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        excludeFamilyMemberDialog?.let { familyMember ->
            BaseDialog(
                titleText = stringResource(id = R.string.exclude_family_member_title),
                descriptionText = stringResource(
                    id = R.string.exclude_family_member_description,
                    excludeFamilyMemberDialog?.name.orEmpty()
                ),
                dismissText = stringResource(id = R.string.exclude_family_member_dismiss),
                actionText = stringResource(id = R.string.exclude_family_member_action),
                onActionClicked = {
                    viewModel.onEvent(
                        IProfileViewModel.Event.OnExcludeFamilyMemberAccepted(
                            familyMember
                        )
                    )
                },
                onDismissClicked = { viewModel.onEvent(IProfileViewModel.Event.OnExcludeDismiss) }
            )
        } ?: editFamilyMemberDialog?.let { familyMember ->
            val localName by viewModel.editFamilyMemberText.collectAsState()
            val saveEnabled by viewModel.editFamilyMemberSaveEnabled.collectAsState()

            Dialog(
                onDismissRequest = {
                    val event = IProfileViewModel.Event.OnEditMemberDismiss
                    viewModel.onEvent(event)
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    content = {
                        EditFamilyMemberDialog(
                            localName = localName,
                            saveEnabled = saveEnabled,
                            onTextChanged = { text ->
                                viewModel.onEvent(
                                    IProfileViewModel.Event.OnEditMemberTextChanged(
                                        text = text
                                    )
                                )
                            },
                            onSaveClicked = {
                                viewModel.onEvent(
                                    IProfileViewModel.Event.OnMemberLocalNameSaved(
                                        email = editFamilyMemberDialog?.email.orEmpty(),
                                        localName = localName
                                    )
                                )
                            },
                            onExcludeFamilyMemberClick = {
                                viewModel.onEvent(
                                    IProfileViewModel.Event.OnExcludeFamilyMemberClick(
                                        familyMember
                                    )
                                )
                            }
                        )
                    }
                )
            }
        } ?: if (showLogoutDialog) {
            BaseDialog(
                titleText = stringResource(id = R.string.profile_logout_title),
                descriptionText = stringResource(id = R.string.profile_logout_description),
                dismissText = stringResource(id = R.string.profile_logout_dismiss),
                actionText = stringResource(id = R.string.profile_logout_action),
                onActionClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutAccepted) },
                onDismissClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutDismiss) }
            )
        } else if (statusMenuShowed) {

            val dismissStatusDialog = {
                val event = IProfileViewModel.Event.ShowStatusMenu(false)
                viewModel.onEvent(event)
            }
            Dialog(
                onDismissRequest = dismissStatusDialog
            ) {
                Dropdown(
                    dismiss = dismissStatusDialog
                ) { status ->
                    val event = IProfileViewModel.Event.ChangeStatus(status)
                    viewModel.onEvent(event)
                }
            }
        }
    }
}

@Composable
private fun Toolbar(onLogoutClicked: () -> Unit) {
    BaseToolbar {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.profile_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = rememberRipple(bounded = false),
                    interactionSource = remember { MutableInteractionSource() }
                ) { onLogoutClicked() },
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun MainUserItem(
    familyMember: FamilyMember,
    statusMenuShowed: Boolean,
    showStatusMenu: (Boolean) -> Unit,
    onImageChangeClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(bottomEnd = 16.dp),
            elevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .padding(vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(57.dp)
                            .clickableWithoutIndication(onImageChangeClicked)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(57.dp)
                                .clip(CircleShape),
                            painter = rememberAsyncImagePainter(familyMember.imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .size(15.dp)
                                .clip(CircleShape)
                                .background(WhitePrimary)
                                .align(Alignment.BottomEnd)
                                .padding(1.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.Center),
                                painter = painterResource(R.drawable.ic_change_circle),
                                contentDescription = null,
                                tint = FamilyOrganizerTheme.colors.blackPrimary
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                    ) {

                        Text(
                            text = familyMember.name,
                            style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                            color = FamilyOrganizerTheme.colors.blackPrimary,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(modifier = Modifier.wrapContentWidth()) {
                            Text(
                                text = stringResource(id = familyMember.status.stringResource),
                                style = FamilyOrganizerTheme.textStyle.body,
                                color = familyMember.status.color,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(
                                            bounded = false,
                                            color = FamilyOrganizerTheme.colors.darkClay
                                        )
                                    ) { showStatusMenu(!statusMenuShowed) },
                                painter = painterResource(
                                    id = if (statusMenuShowed) {
                                        R.drawable.ic_profile_status_arrow_up
                                    } else {
                                        R.drawable.ic_profile_status_arrow_down
                                    }
                                ),
                                contentDescription = null,
                                tint = FamilyOrganizerTheme.colors.darkClay
                            )
                        }
                    }
                }

                Divider(
                    color = FamilyOrganizerTheme.colors.lightGray,
                    modifier = Modifier
                        .height(90.dp)
                        .width(1.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_bottom_navigation_account),
                    contentDescription = null,
                    tint = FamilyOrganizerTheme.colors.darkClay
                )
            }
        }
    }
}

@Composable
private fun FamilyMemberItem(
    familyMember: FamilyMember,
    onEditMemberClicked: (FamilyMember) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        Box(modifier = Modifier.size(57.dp)) {
            Image(
                modifier = Modifier
                    .size(57.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(familyMember.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            OnlineCircle(familyMember.online)
        }
        Spacer(modifier = Modifier.width(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = familyMember.name,
                        style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                        color = FamilyOrganizerTheme.colors.blackPrimary,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(id = familyMember.status.stringResource),
                        style = FamilyOrganizerTheme.textStyle.body,
                        color = familyMember.status.color,
                        maxLines = 1
                    )
                }
                Divider(
                    color = FamilyOrganizerTheme.colors.lightGray,
                    modifier = Modifier
                        .height(62.dp)
                        .width(1.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterVertically)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                color = FamilyOrganizerTheme.colors.darkClay
                            )
                        ) { onEditMemberClicked(familyMember) },
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = FamilyOrganizerTheme.colors.darkClay
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

        }
    }
}

@Composable
private fun BoxScope.OnlineCircle(online: Boolean) {
    Box(
        modifier = Modifier
            .size(15.dp)
            .clip(CircleShape)
            .background(WhitePrimary)
            .align(Alignment.BottomEnd)
            .padding(1.dp)
    ) {
        val onlineColor = if (online) {
            StatusAtHomeColor
        } else {
            FamilyOrganizerTheme.colors.disabled
        }
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(onlineColor)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Dropdown(
    dismiss: () -> Unit,
    changeStatus: (UserStatus) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { dismiss() },
        contentAlignment = Alignment.Center,
        content = {
            val shape = RoundedCornerShape(
                topStart = 0.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.TopCenter)
                    .padding(top = 96.dp, start = 52.dp, end = 32.dp)
                    .clip(shape)
                    .background(FamilyOrganizerTheme.colors.whitePrimary)
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                UserStatus.values().forEach { status ->
                    DropdownItem(status = status, changeStatus = changeStatus)
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider()
                }
            }
        }
    )
}

@Composable
private fun DropdownItem(status: UserStatus, changeStatus: (UserStatus) -> Unit) {
    Text(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { changeStatus(status) }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        text = stringResource(id = status.stringResource),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
        color = status.color,
        maxLines = 1
    )
}

@Composable
private fun EditFamilyMemberDialog(
    localName: String,
    saveEnabled: Boolean,
    onTextChanged: (String) -> Unit,
    onSaveClicked: (String) -> Unit,
    onExcludeFamilyMemberClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 16.dp))
            .background(color = FamilyOrganizerTheme.colors.whitePrimary)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = stringResource(R.string.family_member_edit_dialog_title),
                style = FamilyOrganizerTheme.textStyle.headline2,
                lineHeight = 26.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = stringResource(R.string.family_member_edit_dialog_desc),
                style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                    fontSize = 14.sp,
                    color = FamilyOrganizerTheme.colors.darkClay
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = localName,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { onTextChanged(it) },
                textStyle = FamilyOrganizerTheme.textStyle.input,
                colors = outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { onSaveClicked(localName) },
                enabled = saveEnabled,
                colors = buttonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = stringResource(R.string.family_member_edit_dialog_save).uppercase(),
                    color = FamilyOrganizerTheme.colors.whitePrimary,
                    style = FamilyOrganizerTheme.textStyle.button,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onExcludeFamilyMemberClick() },
                text = stringResource(R.string.family_member_edit_dialog_exclude),
                style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                    fontSize = 14.sp,
                    color = FamilyOrganizerTheme.colors.primary
                ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}