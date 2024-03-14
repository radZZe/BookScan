package ru.radzze.scan_impl.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.runBlocking
import ru.radzze.scan_impl.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    onResultScanNavigate: (encodeUri: String) -> Unit,
) {
    val decodeImage: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    if(decodeImage.value != null){
        onResultScanNavigate(decodeImage.value!!)
        decodeImage.value = null
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val cameraPermissionState: PermissionState =
            rememberPermissionState(permission = android.Manifest.permission.CAMERA)
        if (cameraPermissionState.status.isGranted) {
            CameraScreen(onResultScanNavigate = {
                decodeImage.value = it
            })
        } else {
            NoPermissionScreen {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }

}

@Composable
fun CameraScreen(
    onResultScanNavigate: (encodeUri:String) -> Unit,
    viewModel: ScanScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .zIndex(2f)
                .padding(start = 16.dp, top = 16.dp)
                .clip(
                    RoundedCornerShape(15)
                )
                .background(Color(53, 53, 53, 141))
                .padding(16.dp)
                .align(Alignment.TopStart)
                .clickable {
                    viewModel.changeFlashModeState()
                }) {
            Image(
                painter = painterResource(id = if (viewModel.isFlashModeOn) R.drawable.flash_on else R.drawable.flash_off),
                contentDescription = null
            )
        }
        PickPhotoFromGalleryButton(
            onSuccess = { bitmap ->

            }, modifier = Modifier
                .zIndex(2f)
                .padding(end = 16.dp, top = 16.dp)
                .clip(
                    RoundedCornerShape(15)
                )
                .background(Color(53, 53, 53, 141))
                .padding(16.dp)
                .align(Alignment.TopEnd)
        )


        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .padding(bottom = 80.dp),
            onClick = {
                takePicture(
                    cameraController = cameraController,
                    isFlashModeOn = viewModel.isFlashModeOn,
                    context = context
                ){
                    onResultScanNavigate(it)
                }
            }) {
            Text(text = "Сканировать", color = Color.Black)
        }
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )
    }

}

@Composable
fun NoPermissionScreen(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please grant the permission to use the camera to use the core functionality of this app.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestPermission) {
            Text(text = "Grant permission")
        }
    }
}

@Composable
fun PickPhotoFromGalleryButton(
    onSuccess: (image: Bitmap) -> Unit,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                context.getContentResolver(),
                Uri.parse(it.toString())
            )
            onSuccess(bitmap)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.clickable {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            painter = painterResource(id = R.drawable.baseline_perm_media_24),
            contentDescription = null
        )
    }
}

fun takePicture(
    cameraController: LifecycleCameraController,
    isFlashModeOn: Boolean,
    context: Context,
    onSuccess: (encodeUri: String) -> Unit
) {
    val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
        .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {

            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/BookScan")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()

    val cameraExecutor = Executors.newSingleThreadExecutor()

    cameraController.imageCaptureFlashMode =
        if (isFlashModeOn) FLASH_MODE_ON else FLASH_MODE_OFF
    cameraController.takePicture(
        outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e("IMAGE CAPT", "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri
                if(savedUri != null){
                    runBlocking {
                        val encodeUri = Uri.encode(savedUri.toString().replace('%','|'))
                        onSuccess(encodeUri)
                    }

                }
                Log.d("IMAGE CAPT", "Photo capture succeeded: $savedUri")
            }
        })
}