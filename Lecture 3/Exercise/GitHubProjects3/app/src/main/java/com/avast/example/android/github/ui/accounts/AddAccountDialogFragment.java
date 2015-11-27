package com.avast.example.android.github.ui.accounts;

import java.util.List;
import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.avast.android.dialogs.core.BaseDialogBuilder;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IPositiveButtonDialogListener;
import com.avast.example.android.github.R;
import com.avast.example.android.github.db.AccountDataSource;

/**
 * @author Tomáš Kypta (kypta)
 */
public class AddAccountDialogFragment extends SimpleDialogFragment {

    public interface IAddAccountDialogListener {
        void onPositiveButtonClicked(String account);
    }

    @Bind(R.id.et_account)
    EditText vEtAccount;

    public static String TAG = "add-account";

    public static void show(AppCompatActivity activity) {
        new AddAccountDialogFragment().show(activity.getSupportFragmentManager(), TAG);
    }

    @Override
    protected Builder build(Builder builder) {
        final View customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_account, null);
        ButterKnife.bind(this, customView);
        builder
            .setView(customView)
            .setTitle(R.string.add_account)
            .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (IAddAccountDialogListener listener : getAddAccountDialogListeners()) {
                        listener.onPositiveButtonClicked(vEtAccount.getText().toString());
                    }
                    dismiss();
                }
            });
        return builder;
    }


    protected List<IAddAccountDialogListener> getAddAccountDialogListeners() {
        return getDialogListeners(IAddAccountDialogListener.class);
    }
}
