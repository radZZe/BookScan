package ru.radzze.bookscan.navigation

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.radzze.bookscan.R
import ru.radzze.bookscan.ui.theme.BackgroundBottomNav
import ru.radzze.bookscan.ui.theme.PrimaryYellow
import ru.radzze.curvedbottomnav.MeowBottomNavigation
import java.util.concurrent.Executor


@Composable
fun BottomBarXml(navController: NavController, tabs: List<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val routes = remember { tabs.map { it.route } }
    if ((navBackStackEntry?.destination?.route ?: tabs[0].route) in routes) {
        AndroidView(
            factory = { context ->
                View.inflate(context, R.layout.bottom_nav, null).apply {
                    val bottomNavigation =
                        this.findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
                    tabs.forEachIndexed { index, tab ->

                        bottomNavigation.add(MeowBottomNavigation.Model(index, tab.icon, title =
                            context.getString(tab.title)))
                    }
                    // id = 1 - start destination scanner screen
                    bottomNavigation.cells.get(2)
                    bottomNavigation.show(1, true)
                    bottomNavigation.setOnClickMenuListener {
                        when (it.id) {
                            0 -> bottomBarNavigate(
                                navController,
                                tabs[0].route,
                                navBackStackEntry?.destination?.route ?: tabs[0].route,

                            )

                            1 -> bottomBarNavigate(
                                navController,
                                tabs[1].route,
                                navBackStackEntry?.destination?.route ?: tabs[0].route,
                            )

                            2 -> bottomBarNavigate(
                                navController,
                                tabs[2].route,
                                navBackStackEntry?.destination?.route ?: tabs[0].route,
                            )
                        }
                    }
                }
            },
        )
    }


}

fun bottomBarNavigate(navController: NavController, route: String, currentRoute: String,) {
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


