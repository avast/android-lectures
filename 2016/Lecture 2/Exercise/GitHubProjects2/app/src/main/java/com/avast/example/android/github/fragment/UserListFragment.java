package com.avast.example.android.github.fragment;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.avast.example.android.github.R;

/**
 * Fragment with the list of users.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserListFragment extends ListFragment {

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
}
