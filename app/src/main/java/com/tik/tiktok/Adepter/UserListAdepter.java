package com.tik.tiktok.Adepter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.tik.tiktok.Activity.MainActivity;
import com.tik.tiktok.Activity.ProfileFullDetailActivity;
import com.tik.tiktok.Model.UserVideoList;
import com.tik.tiktok.R;

import java.util.ArrayList;

import com.tik.tiktok.Model.UserDetail;

public class UserListAdepter extends RecyclerView.Adapter<UserListAdepter.ViewHolder> {
    private ArrayList<UserDetail> userListArray = new ArrayList<>();
    Context context;
    MainActivity mainActivity;

    public UserListAdepter(MainActivity Activity, ArrayList<UserDetail> Array) {
        mainActivity = Activity;
        userListArray = Array;
        context = Activity;
    }

    public void loadMore(ArrayList<UserDetail> datas) {

        userListArray = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_activity_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserDetail requestData = userListArray.get(position);
        holder.txtUserName.setText("@" + requestData.getUsername());
        holder.txtName.setText(requestData.getFull_name());
        Picasso.with(context)
                .load(requestData.getAvatar())
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(holder.imgUser);

        holder.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ProfileFullDetailActivity.class);
                intent.putExtra("userprofilePic", requestData.getAvatar());
                intent.putExtra("follower", requestData.getCount());
                intent.putExtra("fullname", requestData.getFull_name());
                intent.putExtra("id", requestData.getUid());
                intent.putExtra("username", requestData.getUsername());
                mainActivity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return userListArray.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imgUser;
        private TextView txtName;
        private TextView txtUserName;

        private ViewHolder(View itemView) {
            super(itemView);


            this.imgUser = (RoundedImageView) itemView.findViewById(R.id.imgUser);
            this.txtName = (TextView) itemView.findViewById(R.id.txtName);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);


        }


    }

}
