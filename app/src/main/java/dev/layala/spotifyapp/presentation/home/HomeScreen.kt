package dev.layala.spotifyapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import dev.layala.spotifyapp.presentation.navigation.Routes

// -------------------- Datos demo --------------------
private data class TrackUi(
    val title: String,
    val artists: String,
    val duration: String,
    val coverUrl: String
)

private val demoTracks = listOf(
    TrackUi("Still with you", "Jung kook", "2:47", "https://i.scdn.co/image/ab67616d00001e02a7f42c375578df426b37638d"),
    TrackUi("Everybody's Changing", "Keane", "3:30", "https://i.scdn.co/image/ab67616d0000b2737d6cd95a046a3c0dacbc7d33"),
    TrackUi("Cardigan", "Taylor Swift", "4:00", "https://cdn-images.dzcdn.net/images/cover/290abe93bdda84bb8b170f30a4998c4c/1900x1900-000000-80-0-0.jpg"),
    TrackUi("Demons", "Imagine Dragons", "2:58", "https://i.scdn.co/image/ab67616d0000b273b2b2747c89d2157b0b29fb6a"),
    TrackUi("New Jeans", "Supernatural", "3:11", "https://cdn-images.dzcdn.net/images/cover/826894f2ec39ae1eea620eddecdcf6b7/0x1900-000000-80-0-0.jpg"),
    TrackUi("Clocks", "Coldplay", "3:12", "https://cdn-images.dzcdn.net/images/cover/5ba1787e1ec36dbbca38ff01fea8fb21/1900x1900-000000-80-0-0.jpg"),

    )
// ----------------------------------------------------

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        val extraBottomSpace = 76.dp // espacio para que no tape la tarjeta flotante

        Box(Modifier.fillMaxSize()) {

            // -------- Contenido scroll (Hero + tracks) --------
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding() + extraBottomSpace + 12.dp)
            ) {
                // HERO
                item {
                    Box {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://es.rollingstone.com/wp-content/uploads/2024/10/La-gran-aventura-interestelar-de-Coldplay-1-min.jpg")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Playlist cover",
                            contentScale = ContentScale.Crop,
                            loading = {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(320.dp)
                                        .background(Color(0xFF2A2A2A))
                                )
                            },
                            error = {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(320.dp)
                                        .background(Color(0xFF5A2A2A))
                                ) {
                                    Text(
                                        "No se pudo cargar la imagen",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(8.dp),
                                        color = Color.White
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        )

                        // Gradiente para texto legible
                        Box(
                            Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                                        startY = 0f, endY = 900f
                                    )
                                )
                        )

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                "Today’s Top Hits",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "The hottest 50. Cover: Coldplay",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                "Spotify · 35,212,210 saves · 2h 50m",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Spacer(Modifier.height(12.dp))

                            // Fila de acciones (avatar cuadrado + íconos + shuffle + play)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AvatarSquare(
                                    imageUrl = "https://www.t-mobilecenter.com/assets/img/08.15.17-Coldplay-530x500-v1-ec4dca7cbf.jpg",
                                    sizeDp = 28.dp
                                )
                                Spacer(Modifier.width(10.dp))

                                ActionIcon(Icons.Outlined.Add)
                                Spacer(Modifier.width(8.dp))
                                ActionIcon(Icons.Outlined.FileDownload)
                                Spacer(Modifier.width(8.dp))
                                ActionIcon(Icons.Outlined.MoreHoriz)

                                Spacer(Modifier.weight(1f))

                                ActionIcon(Icons.Outlined.Shuffle)
                                Spacer(Modifier.width(8.dp))

                                PlayFab()
                            }
                        }
                    }
                }

                // LISTA DE TRACKS
                itemsIndexed(demoTracks) { idx, t ->
                    TrackRow(index = idx, t = t)
                }
            }

            // -------- Tarjeta flotante "Now Playing" --------
            NowPlayingFloating(
                track = demoTracks.last(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = padding.calculateBottomPadding() + 12.dp
                    )
            )
        }
    }
}

// ------------------------- Bottom Bar -------------------------
@Composable
private fun BottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.HOME,
            onClick = { if (currentRoute != Routes.HOME) navController.navigate(Routes.HOME) },
            icon = { Icon(Icons.Outlined.Home, null) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.SEARCH,
            onClick = { if (currentRoute != Routes.SEARCH) navController.navigate(Routes.SEARCH) },
            icon = { Icon(Icons.Outlined.Search, null) },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.LIBRARY,
            onClick = { if (currentRoute != Routes.LIBRARY) navController.navigate(Routes.LIBRARY) },
            icon = { Icon(Icons.Outlined.LibraryMusic, null) },
            label = { Text("Your Library") }
        )
    }
}

// ------------------------- Componentes UI -------------------------
@Composable
private fun ActionIcon(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        shape = RoundedCornerShape(14.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.padding(10.dp))
    }
}

/** Avatar cuadrado con esquinas redondeadas */
@Composable
private fun AvatarSquare(imageUrl: String, sizeDp: Dp) {
    Surface(shape = RoundedCornerShape(6.dp), color = Color.Transparent) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile",
            modifier = Modifier
                .size(sizeDp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

/** Botón verde circular estilo Spotify */
@Composable
private fun PlayFab() {
    Surface(
        color = Color(0xFF1DB954), // verde Spotify
        shape = CircleShape,
        modifier = Modifier.size(56.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black,
            )
        }
    }
}

@Composable
private fun TrackRow(index: Int, t: TrackUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Miniatura
        AsyncImage(
            model = t.coverUrl,
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(10.dp))

        // Número
        Text(
            text = "${index + 1}",
            modifier = Modifier.width(28.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        // Título + artistas
        Column(Modifier.weight(1f)) {
            Text(t.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                t.artists,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Duración + menú
        Text(
            t.duration,
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Icon(Icons.Outlined.MoreHoriz, contentDescription = null)
    }
}

// -------- Tarjeta flotante estilo “Now Playing” --------
@Composable
private fun NowPlayingFloating(
    track: TrackUi,
    modifier: Modifier = Modifier
) {
    val playingBg = Color(0xFF6E2B17) // tono óxido/naranja
    val playingFg = Color(0xFFE9B7A4)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(14.dp),
        color = playingBg,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            // cover
            AsyncImage(
                model = track.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(10.dp))

            // títulos
            Column(Modifier.weight(1f)) {
                Text(track.title, maxLines = 1, color = Color.White)
                Text(
                    track.artists,
                    maxLines = 1,
                    color = playingFg,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.width(12.dp))

            // ▶ flechita blanca simple (sin círculo)
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
