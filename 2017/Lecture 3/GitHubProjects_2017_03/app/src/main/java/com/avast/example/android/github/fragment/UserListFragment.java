package com.avast.example.android.github.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.example.android.github.R;
import com.avast.example.android.github.activity.UserDetailActivity;
import com.avast.example.android.github.data.Settings;

/**
 * Fragment with the list of users.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserListFragment extends Fragment {

    //TODO 4 Recycler Adapter

    private static final String[] items = {"avast", "inmite", "square"};

    RecyclerView mRecyclerView;

    @Nullable
    private OnUserSelectedListener mOnUserSelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_users_list);
        ArrayList<String> users = new ArrayList<>();
        users.addAll(Arrays.asList(items));
        mRecyclerView.setAdapter(new UsersAdapter(users, new OnUserSelectedListener() {
            @Override
            public void onUserSelected(String username) {
                Intent i = new Intent(getActivity(), UserDetailActivity.class);
                i.putExtra(UserDetailActivity.EXTRA_USERNAME, username);
                startActivity(i);
            }
        }));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnUserSelectedListener) {
            mOnUserSelectedListener = (OnUserSelectedListener) context;
        }
        Settings settings = new Settings(context);

        Toast.makeText(context, "App started for " + settings.getAppLaunchesCount(), Toast.LENGTH_LONG).show();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mOnUserSelectedListener = null;
        super.onDetach();
    }

    public interface OnUserSelectedListener {

        void onUserSelected(String username);
    }

    private static class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

        private final List<String> mUsers;
        private final OnUserSelectedListener mOnUserSelectedListener;

        public UsersAdapter(List<String> users, OnUserSelectedListener onUserSelectedListener) {
            mUsers = users;
            mOnUserSelectedListener = onUserSelectedListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list, parent, false);
            // set the view's size, margins, paddings and layout parameters

            final ViewHolder vh = new ViewHolder((TextView) v);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnUserSelectedListener.onUserSelected(mUsers.get(vh.getAdapterPosition()));
                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.vTextView.setText(mUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView vTextView;

            ViewHolder(TextView itemView) {
                super(itemView);
                vTextView = itemView;
            }
        }

    }
}
