package com.avast.example.android.github.fragment;


import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avast.example.android.github.R;
import com.avast.example.android.github.model.User;

/**
 * Fragment with the list of users.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserListFragment extends ListFragment {

    //TODO 4 Recycler Adapter

    private static final String[] items = {"avast", "inmite", "square"};

    @Nullable
    private OnUserSelectedListener mOnUserSelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ListAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_user_list, items);
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnUserSelectedListener) {
            mOnUserSelectedListener = (OnUserSelectedListener) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mOnUserSelectedListener = null;
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mOnUserSelectedListener != null) {
            mOnUserSelectedListener.onUserSelected(items[position]);
        }

        super.onListItemClick(l, v, position, id);
    }

    public interface OnUserSelectedListener {

        void onUserSelected(String username);
    }

    private static class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

        private final List<User> mUsers;

        public UsersAdapter(List<User> users) {
            mUsers = users;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder((TextView) v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

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
