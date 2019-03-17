package com.test.ishanishah.user.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.ishanishah.R;
import com.test.ishanishah.databinding.ItemUserBinding;
import com.test.ishanishah.user.model.UserData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<UserData> userData;

    public UserListAdapter(List<UserData> userData) {
        this.userData = userData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding itemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user, parent, false);
        return new ViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setItemUserBinding(userData.get(position));
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding itemUserBinding;

        public ViewHolder(@NonNull ItemUserBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.itemUserBinding = itemUserBinding;
        }

        void setItemUserBinding(UserData userBinding) {
            itemUserBinding.setUser(userBinding);
        }
    }
}
