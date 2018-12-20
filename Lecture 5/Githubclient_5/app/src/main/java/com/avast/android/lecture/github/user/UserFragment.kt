package com.avast.android.lecture.github.user

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.net.NetRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user.img_avatar
import kotlinx.android.synthetic.main.fragment_user.rv_repositories
import kotlinx.android.synthetic.main.fragment_user.txt_followers_value
import kotlinx.android.synthetic.main.fragment_user.txt_repositories_value
import kotlinx.android.synthetic.main.fragment_user.txt_url_value
import kotlinx.android.synthetic.main.fragment_user.txt_username_value
import java.lang.ref.WeakReference

/**
 * Fragment to show user detail.
 */
class UserFragment : Fragment() {

    var user: User? = null

    /**
     * Lazily initialize data repository.
     */
    private val dataRepository: Repository by lazy {
        NetRepository()
    }

    lateinit var repositoryAdapter: RepositoryAdapter

    /**
     * Inflate view hierarchy to this fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositoryAdapter = RepositoryAdapter()
        rv_repositories.adapter = repositoryAdapter
        rv_repositories.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        rv_repositories.addItemDecoration(DividerItemDecoration(view.context, LinearLayoutManager.VERTICAL))
    }

    override fun onStart() {
        super.onStart()
        val username = arguments?.getString(KEY_USERNAME, "")
        if (username != null) {
            DownloadAsyncTask().execute(username)
            DownloadReposAsyncTaskWeak(dataRepository, repositoryAdapter).execute(username)

        }
    }

    /**
     * Stop any [Glide] operations related to this fragment.
     */
    override fun onStop() {
        super.onStop()
        Glide.with(this).onStop()
    }

    /**
     * This is example of dangerous usage of inner async task. It holds implicit reference on parent class.
     * If the fragment is recreated during the background processing, garbage collector can't collect this parent class
     * instance.
     */
    inner class DownloadAsyncTask: AsyncTask<String, Unit, User?>() {

        override fun doInBackground(vararg username: String): User? {
            return dataRepository.getUser(username.first())
        }

        override fun onPostExecute(result: User?) {
            super.onPostExecute(result)
            result?.let {
                txt_username_value.text = it.login
                txt_url_value.text = it.url
                txt_followers_value.text = it.followers.toString()
                txt_repositories_value.text = it.public_repos.toString()
                Glide.with(this@UserFragment)
                        .load(it.avatar_url)
                        .into(img_avatar)
            }
        }
    }

    /**
     * This is also dangerous, because it holds hard reference to the adapter. Instead of leaking fragment it leaks the
     * adapter.
     */
    class DownloadReposAsyncTask(private val repo: Repository,
                                 private val repositoryAdapter: RepositoryAdapter)
        : AsyncTask<String, Unit, List<GithubRepository>>() {

        override fun doInBackground(vararg username: String): List<GithubRepository>? {
            return repo.getUserRepository(username.first())
        }

        override fun onPostExecute(result: List<GithubRepository>?) {
            super.onPostExecute(result)
            if (result != null) {
                repositoryAdapter.repositories = result
            }
        }
    }

    /**
     * This is also dangerous, because it holds hard reference to the adapter. Instead of leaking fragment it leaks the
     * adapter.
     */
    class DownloadReposAsyncTaskWeak(private val repo: Repository,
                                     repositoryAdapter: RepositoryAdapter)
        : AsyncTask<String, Unit, List<GithubRepository>>() {

        val weakRepositoryAdapter = WeakReference(repositoryAdapter)

        override fun doInBackground(vararg username: String): List<GithubRepository>? {
            return repo.getUserRepository(username.first())
        }

        override fun onPostExecute(result: List<GithubRepository>?) {
            super.onPostExecute(result)
            weakRepositoryAdapter.get()?.let {
                it.repositories =  result ?: emptyList()
            }
        }
    }

    companion object {

        val KEY_USERNAME = "username"

        /**
         * Factory method to create fragment instance. Framework requires empty default constructor.
         */
        @JvmStatic
        fun newInstance(username: String): UserFragment {
            val fragment = UserFragment()
            fragment.arguments = Bundle().apply {
                putString(KEY_USERNAME, username)
            }

            return fragment
        }
    }
}