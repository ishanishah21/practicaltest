package com.test.ishanishah.user.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.ishanishah.R;
import com.test.ishanishah.databinding.FragmentSelectedUserListBinding;
import com.test.ishanishah.user.model.BridgeModel;
import com.test.ishanishah.user.view.adapters.SelectedUserListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class SelectedUserListFragment extends Fragment {

    private FragmentSelectedUserListBinding fragmentSelectedUserListBinding;
    private BridgeModel userData;
    private SelectedUserListAdapter userListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSelectedUserListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_user_list, container, false);

        if (getArguments() != null) {
            userData = (BridgeModel) getArguments().getSerializable(getString(R.string.access_args));
        }
        return fragmentSelectedUserListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userListAdapter = new SelectedUserListAdapter(userData.getUserData());
        fragmentSelectedUserListBinding.rvUserList.setAdapter(userListAdapter);
    }

}
