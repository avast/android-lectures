package com.avast.example.android.github.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avast.example.android.github.R;

/**
 * Fragment with the list of users.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserListFragment extends Fragment {

    //TODO 4 Recycler Adapter

    private static final String[] items = {"avast", "inmite", "square"};

    @Nullable
    private OnUserSelectedListener mOnUserSelectedListener;
    private RecyclerView vRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        vRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_users_list);

        UsersAdapter adapter = new UsersAdapter(items, mOnUserSelectedListener);
        vRecyclerView.setAdapter(adapter);
        vRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        vRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnUserSelectedListener) {
            mOnUserSelectedListener = (OnUserSelectedListener) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
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

        private final String[] mUsers;
        private OnUserSelectedListener mListener;

        UsersAdapter(String[] users, OnUserSelectedListener listener) {
            mUsers = users;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list, parent, false);
            // set the view's size, margins, paddings and layout parameters

            return new ViewHolder((TextView) v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.vTextView.setText(mUsers[position]);
            holder.vTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onUserSelected(mUsers[holder.getAdapterPosition()]);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.length;
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
