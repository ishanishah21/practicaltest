package com.test.ishanishah.user.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.ishanishah.R;
import com.test.ishanishah.databinding.FragmentUserListBinding;
import com.test.ishanishah.user.model.BridgeModel;
import com.test.ishanishah.user.model.UserData;
import com.test.ishanishah.user.utils.PaginationScrollListenerList;
import com.test.ishanishah.user.view.adapters.UserListAdapter;
import com.test.ishanishah.user.viewmodel.UserListViewModel;
import com.test.ishanishah.user.viewmodel.UserListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class UserListFragment extends Fragment {
    private FragmentUserListBinding fragmentUserListBinding;
    private UserListViewModelFactory userListViewModelFactory;
    private UserListViewModel userListViewModel;
    private MutableLiveData<List<UserData>> listMutableLiveData;
    private List<UserData> userData;
    private UserListAdapter userListAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentUserListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false);
        listMutableLiveData = new MutableLiveData<>();
        userData = new ArrayList<>();

        userListAdapter = new UserListAdapter(userData);
        isLastPage = false;
        isLoading = false;
        setupAdapter();
        initHandlers();
        recyclerViewListingScollingCalled();

        return fragmentUserListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (userListViewModel == null) {
            userListViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(UserListViewModel.class);
        }
        fragmentUserListBinding.loader.setVisibility(View.VISIBLE);
        userListViewModel.changePage(0, listMutableLiveData);

        listMutableLiveData.observe(this, new Observer<List<UserData>>() {
            @Override
            public void onChanged(List<UserData> userData) {
                Log.e("::", "::USER DATA::");
                if (!userData.isEmpty()) {
                    fragmentUserListBinding.loader.setVisibility(View.GONE);
                    UserListFragment.this.userData.addAll(userData);
                    userListAdapter.notifyDataSetChanged();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        });

    }

    private void setupAdapter() {
        fragmentUserListBinding.rvUserList.setAdapter(userListAdapter);
    }


    private void initHandlers() {
        fragmentUserListBinding.fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserData> userDatas = new ArrayList<>();
                for (int i = 0; i < userData.size(); i++) {
                    if (userData.get(i).isChecked()) {
                        userDatas.add(userData.get(i));
                    }
                }
                if (!userDatas.isEmpty()) {
                    Bundle bundle = new Bundle();
                    BridgeModel bridgeModel = new BridgeModel();
                    bridgeModel.setUserData(userDatas);
                    bundle.putSerializable(getString(R.string.access_args), bridgeModel);
                    Navigation.findNavController(fragmentUserListBinding.getRoot()).navigate(R.id.action_userListFragment_to_selectedUserList, bundle);
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_valiadtion), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void recyclerViewListingScollingCalled() {
        fragmentUserListBinding.rvUserList.addOnScrollListener(new PaginationScrollListenerList((LinearLayoutManager) fragmentUserListBinding.rvUserList.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                try {
                    isLoading = true;
                    loadMoreData();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public int getTotalPageCount() {
                return 10;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadMoreData() {
        fragmentUserListBinding.loader.setVisibility(View.GONE);
        userListViewModel.changePage(listMutableLiveData.getValue().get(listMutableLiveData.getValue().size() - 1).getId(), listMutableLiveData);
    }
}
