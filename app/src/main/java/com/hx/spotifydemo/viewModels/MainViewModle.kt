package com.hx.spotifydemo.viewModels

import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hx.spotifydemo.http.Https
import com.hx.spotifydemo.http.Services
import com.hx.spotifydemo.pagings.PagingSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainViewModle : ViewModel() {
    val repositoryPager = Pager(config = PagingConfig(pageSize = 6)) {
        PagingSource(getServices(),"android")
    }.flow.cachedIn(viewModelScope)

    fun getServices():Services {
        val retrofit = Retrofit.Builder()
            .baseUrl(Https.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient
                .Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .build())
            .build()
        return retrofit.create(Services::class.java)
    }
}

data class RepositoryResp(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)

data class Item(
    val allow_forking: Boolean,
    val archive_url: String,
    val archived: Boolean,
    val assignees_url: String,
    val blobs_url: String,
    val branches_url: String,
    val clone_url: String,
    val collaborators_url: String,
    val comments_url: String,
    val commits_url: String,
    val compare_url: String,
    val contents_url: String,
    val contributors_url: String,
    val created_at: String,
    val default_branch: String,
    val deployments_url: String,
    val description: String,
    val disabled: Boolean,
    val downloads_url: String,
    val events_url: String,
    val fork: Boolean,
    val forks: Int,
    val forks_count: Int,
    val forks_url: String,
    val full_name: String,
    val git_commits_url: String,
    val git_refs_url: String,
    val git_tags_url: String,
    val git_url: String,
    val has_discussions: Boolean,
    val has_downloads: Boolean,
    val has_issues: Boolean,
    val has_pages: Boolean,
    val has_projects: Boolean,
    val has_wiki: Boolean,
    val homepage: String,
    val hooks_url: String,
    val html_url: String,
    val id: Int,
    val is_template: Boolean,
    val issue_comment_url: String,
    val issue_events_url: String,
    val issues_url: String,
    val keys_url: String,
    val labels_url: String,
    val language: Any,
    val languages_url: String,
    val license: Any,
    val merges_url: String,
    val milestones_url: String,
    val mirror_url: Any,
    val name: String,
    val node_id: String,
    val notifications_url: String,
    val open_issues: Int,
    val open_issues_count: Int,
    val owner: Owner,
    val `private`: Boolean,
    val pulls_url: String,
    val pushed_at: String,
    val releases_url: String,
    val score: Double,
    val size: Int,
    val ssh_url: String,
    val stargazers_count: Int,
    val stargazers_url: String,
    val statuses_url: String,
    val subscribers_url: String,
    val subscription_url: String,
    val svn_url: String,
    val tags_url: String,
    val teams_url: String,
    val topics: List<String>,
    val trees_url: String,
    val updated_at: String,
    val url: String,
    val visibility: String,
    val watchers: Int,
    val watchers_count: Int,
    val web_commit_signoff_required: Boolean
)

data class Owner(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)