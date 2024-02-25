package ru.radzze.bookscan.navigation

import android.view.View
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import ru.radzze.bookscan.R
import ru.radzze.bookscan.ui.theme.Background
import ru.radzze.bookscan.ui.theme.BackgroundBottomNav
import ru.radzze.bookscan.ui.theme.PrimaryYellow


@Composable
fun BottomBarXml(navController: NavController, tabs: List<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()


    val routes = remember { tabs.map { it.route } }
    Column(modifier = Modifier.fillMaxWidth()){
        if ((navBackStackEntry?.destination?.route ?: tabs[0].route) in routes) {
            AndroidView(
                factory = { context ->
                    View.inflate(context, R.layout.bottom_nav, null).apply {
                        val bottomNavigation =
                            this.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
                        tabs.forEachIndexed { index, tab ->
                            bottomNavigation.add(MeowBottomNavigation.Model(index, tab.icon))
                        }
                        // id = 1 - start destination scanner screen
                        bottomNavigation.cells.get(2)
                        bottomNavigation.show(1, true)
                        bottomNavigation.setOnClickMenuListener {
                            when (it.id) {
                                0 -> bottomBarNavigate(
                                    navController,
                                    tabs[0].route,
                                    navBackStackEntry?.destination?.route ?: tabs[0].route
                                )

                                1 -> bottomBarNavigate(
                                    navController,
                                    tabs[1].route,
                                    navBackStackEntry?.destination?.route ?: tabs[0].route
                                )

                                2 -> bottomBarNavigate(
                                    navController,
                                    tabs[2].route,
                                    navBackStackEntry?.destination?.route ?: tabs[0].route
                                )
                            }
                        }
                    }
                },
            )
            Row(modifier = Modifier.fillMaxWidth().background(BackgroundBottomNav).padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween){
                tabs.forEach {
                    Text(text = stringResource(id =it.title ), fontSize = 13.sp)
                }
            }
        }

    }

}

fun bottomBarNavigate(navController: NavController, route: String, currentRoute: String) {
    if (route != currentRoute) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun BottomBar(navController: NavController, tabs: List<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: tabs[0]

    val routes = remember { tabs.map { it.route } }

    if (currentRoute in routes) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            val currentTab = tabs.find {
                it.route == currentRoute
            }
            val currentIndex = tabs.indexOf(currentTab!!)
            BottomBarIcon(
                modifier = Modifier.align(
                    when (currentIndex) {
                        0 -> {
                            Alignment.CenterStart
                        }

                        1 -> {
                            Alignment.Center
                        }

                        else -> {
                            Alignment.CenterEnd
                        }
                    }
                ),
                icon = { Icon(painterResource(currentTab!!.icon), contentDescription = null) },
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(20))
                    .fillMaxWidth(1f)
                    .background(BackgroundBottomNav),
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomBarItem(
                        modifier = Modifier.align(
                            if (index == 0) {
                                Alignment.CenterStart
                            } else if (index == 1) {
                                Alignment.Center
                            } else {
                                Alignment.CenterEnd
                            }
                        ),
                        icon = {
                            if (currentRoute != tab.route) Icon(
                                painterResource(tab.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(tab.title), fontSize = 12.sp) },
                        onClick = {
                            if (tab.route != currentRoute) {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    )


                }
            }
        }
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable() (() -> Unit),
) {
    Column(
        modifier = modifier
            .size(80.dp)
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        icon()
        label()
    }
}


@Composable
fun BottomBarIcon(
    modifier: Modifier,
    icon: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(bottom = 45.dp)
            .zIndex(3f)
            .clip(CircleShape)
            .size(60.dp)
            .background(PrimaryYellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
    }
}

