package me.cniekirk.mastodroid.feature.feed.feed

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.calculateStandardPaneScaffoldDirective
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.cniekirk.mastodroid.core.common.util.getUriFromBitmap
import me.cniekirk.mastodroid.core.common.util.shareMedia
import me.cniekirk.mastodroid.core.common.util.shareText
import me.cniekirk.mastodroid.core.designsystem.MastodroidTheme
import me.cniekirk.mastodroid.core.designsystem.component.MastodonStatus
import me.cniekirk.mastodroid.core.model.UserFeedItem
import me.cniekirk.mastodroid.core.ui.loadImage
import me.cniekirk.mastodroid.feature.feed.R
import me.cniekirk.mastodroid.feature.feed.feed.ViewState.AUTH_ERROR
import me.cniekirk.mastodroid.feature.feed.feed.ViewState.LOADING
import me.cniekirk.mastodroid.feature.feed.feed.ViewState.SUCCESS
import me.cniekirk.mastodroid.feature.feed.post.PostScreen
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

/**
 * Testing out List-Detail implementation
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun StatusListDetailRoute(
    viewModel: FeedViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit,
    onSettingsPressed: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = viewModel.collectAsState()
    val listDetailState = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = calculateStandardPaneScaffoldDirective(currentWindowAdaptiveInfo())
    )
    val bottomSheetState = rememberModalBottomSheetState()

    viewModel.collectSideEffect(
        sideEffect = { effect ->
            handleSideEffect(
                effect,
                context,
                scope,
                bottomSheetState,
                listDetailState,
                toast = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() },
                onDismissShare = viewModel::onDismissShare
            )
        }
    )

    BackHandler(enabled = listDetailState.canNavigateBack()) {
        listDetailState.navigateBack()
    }

    val listState = rememberLazyListState()

    ListDetailPaneScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = listDetailState.scaffoldState,
        listPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                when (state.value.viewState) {
                    LOADING -> LoadingView()
                    SUCCESS -> {
                        LaunchedEffect(state.value.viewState) {
                            onSuccess()
                        }

                        FeedScreen(
                            state = state.value,
                            listState = listState,
                            onSettingsClicked = { onSettingsPressed() },
                            onItemClicked = viewModel::onItemClicked,
                            onReplyClicked = {},
                            onReblogClicked = {},
                            onFavouriteClicked = {},
                            onShareClicked = {}
                        )
                    }
                    AUTH_ERROR -> {
                        LaunchedEffect(state) {
                            navigateToLogin()
                        }
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                if (state.value.postState.post == null) {
                    // Show no post selected screen
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = stringResource(id = R.string.no_item_selected_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(id = R.string.no_item_selected_description),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                } else {
                    PostScreen(
                        state = state.value.postState,
                        sheetState = bottomSheetState,
                        onBackPressed = viewModel::onBackPressed,
                        onReplyClicked = {},
                        onReblogClicked = {},
                        onFavouriteClicked = viewModel::favouritePost,
                        onShareClicked = viewModel::sharePost,
                        onShareLinkClicked = viewModel::shareLink,
                        onShareMediaClicked = viewModel::shareMedia,
                        onDismissShare = viewModel::onDismissShare
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
private fun handleSideEffect(
    feedEffect: FeedEffect,
    context: Context,
    scope: CoroutineScope,
    sheetState: SheetState,
    scaffoldNavigator: ThreePaneScaffoldNavigator,
    toast: (Int) -> Unit,
    onDismissShare: () -> Unit
) {
    when (feedEffect) {
        is FeedEffect.Error -> toast(feedEffect.message)
        is FeedEffect.ReplyToPost -> {

        }
        is FeedEffect.ShareMedia -> {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissShare()
                }
            }
            scope.launch {
                val uris = arrayListOf<Uri>()
                feedEffect.mediaUrls.forEach {
                    context.loadImage(it) { bmp -> uris.add(context.getUriFromBitmap(bmp)) }
                }
                context.shareMedia(uris)
            }
        }
        is FeedEffect.ShareLink -> {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissShare()
                }
            }
            context.shareText(feedEffect.link)
        }
        is FeedEffect.ItemClicked -> {
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
        }
        is FeedEffect.BackPressed -> {
            if (scaffoldNavigator.canNavigateBack()) {
                scaffoldNavigator.navigateBack()
            }
        }
    }
}

@Composable
internal fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    state: FeedState,
    listState: LazyListState,
    onSettingsClicked: () -> Unit,
    onItemClicked: (post: UserFeedItem) -> Unit,
    onReplyClicked: (postId: String) -> Unit,
    onReblogClicked: (postId: String) -> Unit,
    onFavouriteClicked: (postId: String) -> Unit,
    onShareClicked: (postId: String) -> Unit
) {
    val items = state.feedItems.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.feed_title),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onSettingsClicked() },
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (items.loadState.refresh) {
                is LoadState.Error -> {
                    // TODO
                }
                LoadState.Loading -> {
                    Spacer(modifier = Modifier.weight(1f))
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.weight(1f))
                }
                is LoadState.NotLoading -> {
                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp),
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(
                            items.itemCount,
                            key = items.itemKey { it.id }
                        ) { index ->
                            val feedItem = items[index]

                            if (feedItem != null) {
                                MastodonStatus(
                                    userFeedItem = feedItem,
                                    onItemClicked = { onItemClicked(it) },
                                    onReplyClicked = { onReplyClicked(it) },
                                    onReblogClicked = { onReblogClicked(it) },
                                    onFavouriteClicked = { onFavouriteClicked(it) },
                                    onShareClicked = { onShareClicked(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    val feedItem = UserFeedItem(
        1,
        "https://mastodon.social/@someone/112233",
        "Example User",
        "example",
        "",
        "1hr",
        "10 October 2023 10:34",
        "This is an example post and here's more text so that it spans more than one line.",
        11,
        12,
        13,
        "Mastodroid for Android",
        false,
        false,
        persistentListOf()
    )

    val state = FeedState(viewState = SUCCESS, feedItems = MutableStateFlow(PagingData.from(listOf(feedItem, feedItem.copy(id = 2), feedItem.copy(id = 3)))))
    MastodroidTheme {
        Surface {
            FeedScreen(state, rememberLazyListState(), {}, {}, {}, {}, {}, {})
        }
    }
}