package com.avast.example.android.github.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avast.example.android.github.R;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.Repo;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
public class ReposListFragment extends Fragment {

    private static final String ARG_USERNAME = "username";

    private ListView vReposListView;

    public static ReposListFragment newInstance(String username) {
        ReposListFragment fragment = new ReposListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repos_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        vReposListView = (ListView) view.findViewById(R.id.list_repos);
        vReposListView.setAdapter(new ReposAdapter(getActivity(), R.layout.item_repo, R.id.txt_repo_name, Storage
            .getRepos()));

        super.onViewCreated(view, savedInstanceState);
    }

    private static class ReposAdapter extends ArrayAdapter<Repo> {

        public ReposAdapter(Context context, int resource, int textViewResourceId, List<Repo> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Repo repo = getItem(position);

            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_repo, parent,false);
                vh = new ViewHolder();
                vh.vTxtRepoName = (TextView) convertView.findViewById(R.id.txt_repo_name);
                vh.vTxtRepoDescription = (TextView) convertView.findViewById(R.id.txt_repo_description);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }


            vh.vTxtRepoName.setText(repo.getName());
            vh.vTxtRepoDescription.setText(repo.getDescription());

            return convertView;
        }

        static class ViewHolder {
            TextView vTxtRepoName;
            TextView vTxtRepoDescription;
        }
    }
}
