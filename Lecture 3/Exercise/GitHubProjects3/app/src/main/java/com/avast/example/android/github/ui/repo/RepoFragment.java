package com.avast.example.android.github.ui.repo;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.api.ApiFactory;
import com.avast.example.android.github.api.GitHubApi;
import com.avast.example.android.github.model.Account;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * @author Tomáš Kypta (kypta)
 */
public class RepoFragment extends Fragment implements LoadAccountDetailsAsyncTask.AccountDetailListener {

    // TODO task 12
    ImageView vImage;
    TextView vTxtName;
    RecyclerView vRvRepos;
    LinearLayout vLlRepo;

    private RecyclerView.LayoutManager mLayoutManager;
    private ReposRecyclerViewAdapter mAdapter;
    private Account mAccount;
    private User mUser;

    // TODO task 7
    GitHubApi mApi = ApiFactory.createGitHubApi();

    private LoadAccountDetailsAsyncTask mLoadAccountDetailsAsyncTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((GitHubApplication) context.getApplicationContext()).getAppComponent()
            .injectRepoFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo, container, false);
        // TODO task 12
        vImage = (ImageView) view.findViewById(R.id.image);
        vTxtName = (TextView) view.findViewById(R.id.txt_name);
        vRvRepos = (RecyclerView) view.findViewById(R.id.rv_repos);
        vLlRepo = (LinearLayout) view.findViewById(R.id.ll_repo);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vRvRepos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        vRvRepos.setLayoutManager(mLayoutManager);
        mAdapter = new ReposRecyclerViewAdapter();
        vRvRepos.setAdapter(mAdapter);
    }

    public void showAccount(Account account) {
        mAccount = account;
        mAdapter.setRepos(null);
        if (account != null) {
            // TODO task 6

            vTxtName.setText(account.getName());
            loadUser(account);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // TODO task 12
    }

    private void loadUser(Account account) {
        // well, this approach has some issues
        mLoadAccountDetailsAsyncTask = new LoadAccountDetailsAsyncTask(getActivity(), this);
        mLoadAccountDetailsAsyncTask.execute(account.getName());

        // TODO task
        // Beware this is nice API, but we just created a memory leak here.
        // Inner class hold reference to it's parent (the fragment),
        // so we are leaking the fragment until the request is done.
        mApi.getUserRepos(mAccount.getName(), new Callback<List<Repo>>() {
            @Override
            public void success(List<Repo> repos, Response response) {
                if (isAdded()) {
                    mAdapter.setRepos(repos);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!isAdded()) {
                    return;
                }
                Snackbar.make(vLlRepo,
                    getActivity().getString(R.string.loading_repos_failed, mAccount.getName()),
                    Snackbar.LENGTH_LONG)
                    .show();
                mAdapter.setRepos(null);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLoadAccountDetailsAsyncTask != null) {
            mLoadAccountDetailsAsyncTask.cancel(true);
            mLoadAccountDetailsAsyncTask = null;
        }
    }

    // LoadAccountDetailsAsyncTask.AccountDetailListener

    @Override
    public void onDataLoaded(User user) {
        mUser = user;
        if (user != null) {
            // TODO task 11
        }
    }

    public static class ReposRecyclerViewAdapter
        extends RecyclerView.Adapter<ReposRecyclerViewAdapter.ViewHolder> {

        private List<Repo> mRepos;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // TODO task 12
            TextView vTextView;

            public ViewHolder(View view) {
                super(view);
                // TODO task 12
                vTextView = (TextView) view.findViewById(R.id.text);
            }
        }

        public void setRepos(List<Repo> repos) {
            mRepos = repos;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mRepos != null ? mRepos.size() : 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // TODO task 9
        }

    }
}
