package com.avast.example.android.github.activity;

import java.util.List;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.avast.example.android.github.R;
import com.avast.example.android.github.api.ApiFactory;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.Repo;

/**
 * Activity displaying list of repositories after
 * @author Tomáš Kypta (kypta)
 */
public class ReposActivity extends ListActivity {


    private ReposBaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ReposBaseAdapter(this, Storage.getRepos());
        setListAdapter(mAdapter);
    }

    // TODO create instance of BaseAdapter
    private static class ReposBaseAdapter extends BaseAdapter {

        private Context mContext;
        private List<Repo> mRepos;

        public ReposBaseAdapter(Context context, List<Repo> repos) {
            mContext = context;
            mRepos = repos;
        }

        public void setRepos(List<Repo> repos) {
            mRepos = repos;
        }

        @Override
        public int getCount() {
            // TODO
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO
            return null;
        }
    }
}
