package com.avast.example.android.github.ui.accounts;

import java.util.List;
import javax.inject.Inject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.db.AccountDataSource;
import com.avast.example.android.github.event.DbUpdatedEvent;
import com.avast.example.android.github.model.Account;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountFragment extends Fragment implements LoadAccountsAsyncTask.AccountListListener {

    @Inject
    AccountDataSource mAccountDataSource;
    // TODO task 10

    @Bind(R.id.rv_accounts)
    RecyclerView vRecyclerView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout vSwipeRefreshLayout;

    private AccountContract mAccountContract;
    private LoadAccountsAsyncTask mLoadAccountsAsyncTask;
    private AccountsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AccountFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountContract) {
            mAccountContract = (AccountContract) context;
        }
        ((GitHubApplication) context.getApplicationContext()).getAppComponent()
            .injectAccountFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        vRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AccountsRecyclerViewAdapter(mAccountContract);
        vRecyclerView.setAdapter(mAdapter);
        // TODO task 5 - start refresh action
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();
        // TODO task 10
    }

    private void loadData() {
        // well, this approach has some issues
        mLoadAccountsAsyncTask = new LoadAccountsAsyncTask(getActivity(), this);
        mLoadAccountsAsyncTask.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO task 10
        if (mLoadAccountsAsyncTask != null) {
            mLoadAccountsAsyncTask.cancel(true);
            mLoadAccountsAsyncTask = null;
        }
    }

    // TODO task 10

    // LoadAccountsAsyncTask.AccountListListener

    @Override
    public void onDataLoaded(List<Account> accounts) {
        // TODO task 5 - stop refreshing
        mAdapter.setAccounts(accounts);
    }


    public static class AccountsRecyclerViewAdapter
        extends RecyclerView.Adapter<AccountsRecyclerViewAdapter.ViewHolder> {

        private List<Account> mAccounts;
        private AccountContract mAccountContract;


        public AccountsRecyclerViewAdapter(AccountContract accountContract){
            mAccountContract = accountContract;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.text) TextView vTextView;
            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public void setAccounts(List<Account> accounts) {
            mAccounts = accounts;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mAccounts != null ? mAccounts.size() : 0;
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
            final Account account = mAccounts.get(position);
            holder.vTextView.setText(account.getName());
            holder.vTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAccountContract.showAccount(account);
                }
            });
        }

    }
}
