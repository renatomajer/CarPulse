package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.IconImage
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationState
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotationState
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.ui.components.TripHistoryDataComponent
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.OrangeColor
import hr.fer.carpulse.ui.theme.StrongRedColor
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.viewmodel.TripDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TripDetailsScreen(
    modifier: Modifier = Modifier,
    tripUUID: String,
    viewModel: TripDetailsViewModel = getViewModel(),
    onNavigateBack: () -> Unit = {}
) {

    val mapInset = calculateMapInsets()

    val polylineAnnotationState = PolylineAnnotationState().apply {
        lineColor = StrongRedColor
        lineWidth = 7.0
        lineBorderColor = StrongRedColor
        lineJoin = LineJoin.ROUND
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val coordinates = viewModel.tripCoordinates

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(5.0)
            center(Point.fromLngLat(15.99, 45.8008))
            pitch(0.0)
            bearing(0.0)
        }
    }

    val scrollState = rememberScrollState()

    val tripStartDate = "25.5.2025."
    val tripStartTime = "15:30"
    val onAssistantClick: () -> Unit = {}

    val maxBottomSheetHeight = getScreenHeight() - 125.dp

    LaunchedEffect(Unit) {
        viewModel.getTripCoordinates(tripUUID)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 300.dp,
        sheetShape = RoundedCornerShape(topStartPercent = 8, topEndPercent = 8),
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp, max = maxBottomSheetHeight)
                    .background(color = AppBackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(23.dp))
                Spacer(
                    modifier = Modifier
                        .width(50.dp)
                        .height(6.dp)
                        .background(color = LightGrayColor, shape = RoundedCornerShape(30))
                )
                Spacer(modifier = Modifier.height(22.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.7.dp)
                        .background(
                            color = if (scrollState.value > 0) LightGrayColor else Color.Transparent
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 34.dp, end = 22.dp)
                        .verticalScroll(scrollState)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.trip_details_screen_listen_to_assistant),
                            style = title
                        )

                        IconButton(
                            modifier = Modifier.size(70.dp),
                            onClick = onAssistantClick
                        ) {
                            Image(
                                modifier = Modifier.size(70.dp),
                                painter = painterResource(R.drawable.ic_ai_assistant),
                                contentDescription = null
                            )
                        }
                    }

                    TripHistoryDataComponent(
                        tripDistance = 15.0,
                        startAddress = "Jarun 5",
                        endAddress = "Banjavčićeva 1A",
                        weatherDescription = "Light rain",
                        temperature = 12,
                        startTimestamp = 1750795702000,
                        endTimestamp = 1750894602000,
                        idlingPercent = 30,
                        idlingTimeMinutes = 5,
                        averageSpeed = 25,
                        averageRpm = 25,
                        maxSpeed = 70,
                        maxRpm = 70,
                        drivingAboveSpeedLimitPercentage = 50,
                        drivingWithinSpeedLimitPercentage = 60,
                        suddenBreaking = 5,
                        suddenAcceleration = 4,
                        stopAndGoSituations = 10
                    )
                }
            }
        }
    ) {
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
                    onClick = onNavigateBack
                ) {
                    Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(text = tripStartDate, style = title)

                Spacer(modifier = Modifier.width(10.dp))

                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .background(color = OrangeColor)
                        .size(18.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = tripStartTime, style = title)

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            MapboxMap(
                Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                mapViewportState = mapViewportState,
            ) {

                if (coordinates?.isNotEmpty() == true) {

                    MapEffect(Unit) { mapView ->
                        mapView.mapboxMap.cameraForCoordinates(
                            listOf(
                                Point.fromLngLat(
                                    coordinates.first().longitude,
                                    coordinates.first().latitude
                                ),
                                Point.fromLngLat(
                                    coordinates.last().longitude,
                                    coordinates.last().latitude
                                )
                            ),
                            CameraOptions.Builder().build(),
                            EdgeInsets(
                                0.0,
                                mapInset,
                                mapInset * 3,
                                mapInset
                            ),
                            15.0,
                            null
                        ) { cameraOptions ->
                            CoroutineScope(Dispatchers.Default).launch {
                                mapViewportState.easeTo(
                                    cameraOptions = cameraOptions,
                                    animationOptions = MapAnimationOptions.mapAnimationOptions {
                                        duration(0)
                                    }
                                )
                                mapView.invalidate()
                            }
                        }
                    }

                    PolylineAnnotation(
                        points = coordinates.map {
                            Point.fromLngLat(it.longitude, it.latitude)
                        },
                        polylineAnnotationState = polylineAnnotationState
                    )

                    AddRouteMarker(coordinates.last())
                    AddRouteMarker(coordinates.first())
                }
            }

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

@Composable
fun AddRouteMarker(coordinate: Coordinate) {

    val bitmap = ImageBitmap.imageResource(R.drawable.ic_location).asAndroidBitmap()
    val pointAnnotationState = PointAnnotationState()
    pointAnnotationState.iconImage = IconImage(bitmap)
    pointAnnotationState.iconSize = 0.45


    PointAnnotation(
        point = Point.fromLngLat(coordinate.longitude, coordinate.latitude),
        pointAnnotationState = pointAnnotationState
    )
}

/**
 * Gets the screen width in dp.
 */
@Composable
private fun getScreenWidth(): Double {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp.value.toDouble()
}

@Composable
private fun getScreenHeight(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.dp
}

@Composable
private fun calculateMapInsets(): Double {
    val screenWidth = getScreenWidth()
    val inset = with(LocalDensity.current) { (screenWidth / 5).dp.toPx() }
    return inset.toDouble()
}

