package com.hx.spotifydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hx.spotifydemo.ui.theme.SpotifyDemoTheme
import com.hx.spotifydemo.viewModels.MainViewModle
import java.lang.Error

class MainActivity : ComponentActivity() {
    val vm by lazy { ViewModelProvider(this).get(MainViewModle::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val collectAsLazyPagingItems = vm.repositoryPager.collectAsLazyPagingItems()
            val state: LazyListState = rememberLazyListState()
            SpotifyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (collectAsLazyPagingItems.loadState.refresh is LoadState.Error) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Button(
                                onClick = { collectAsLazyPagingItems.refresh() },
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Text(text = "加载失败，请重试")
                            }
                        }
                    } else {
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing = (collectAsLazyPagingItems.loadState.refresh is LoadState.Loading)),
                            onRefresh = { collectAsLazyPagingItems.refresh() }) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                contentPadding = PaddingValues(10.dp)
                            ) {
                                items(items = collectAsLazyPagingItems) { item ->
                                    item?.let {
                                        Card(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .fillMaxWidth()
                                                .height(80.dp)
                                        ) {
                                            Row {
                                                Surface(
                                                    shape = CircleShape, modifier = Modifier
                                                        .size(66.dp)
                                                        .align(Alignment.CenterVertically)
                                                        .padding(5.dp)
                                                ) {
                                                    AsyncImage(
                                                        model = item.owner.avatar_url,
                                                        contentDescription = "Acatar"
                                                    )
                                                }
                                                Column {
                                                    Text(text = it.name)
                                                }
                                            }
                                        }
                                    }
                                }
                                if (collectAsLazyPagingItems.loadState.append is LoadState.Loading) {
                                    item {
                                        Box(modifier = Modifier.fillMaxWidth()) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.align(
                                                    Alignment.Center
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}