package com.example.enterdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enterdetails.entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;
    private final Context context;

    public UserAdapter(Context mContext) {
        // passed from activity when adapter initialised
        this.context = mContext;
    }

    public void setUserDataInAdapter(List<User> mUserList) {
        userList = mUserList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
        holder.email.setText(userList.get(position).getEmail());
        holder.phone.setText(userList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        if(userList==null) {
            return 0;
        }
        return userList.size() ;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.rowName);
            email = itemView.findViewById(R.id.rowEmail);
            phone = itemView.findViewById(R.id.rowPhone);
            image = itemView.findViewById(R.id.rowImgView);

        }
    }
}
