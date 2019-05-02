package com.tik.tiktok.Adepter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tik.tiktok.Activity.ProfileFullDetailActivity;
import com.tik.tiktok.Model.UserVideoList;
import com.tik.tiktok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UserVideoListAdepter extends RecyclerView.Adapter<UserVideoListAdepter.ViewHolder> {
    Context context;
    ArrayList<UserVideoList> userVideoLists;

    public UserVideoListAdepter(ProfileFullDetailActivity profileFullDetailActivity, ArrayList<UserVideoList> videoLists) {
        context = profileFullDetailActivity;
        userVideoLists = videoLists;

    }

    public void loadMore(ArrayList<UserVideoList> datas) {

        userVideoLists = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserVideoList requestData = userVideoLists.get(position);

        holder.txtTag.setText(requestData.getDesc());
        holder.txtDay.setText(getDate(requestData.getCreate_time(),"dd/MMM/yyyy hh:mm"));
        holder.txtComment.setText("Comment :" + requestData.getStatistics().getComment_count());
        holder.txtHart.setText("Hart: " + requestData.getStatistics().getDigg_count());

        Picasso.with(context)
                .load(requestData.getVideo().getCover().getUrl_list().get(0))
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(holder.imgVideoTham);
    }


    @Override
    public int getItemCount() {
        return userVideoLists.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoTham;
        private TextView txtTag;
        private TextView txtDay;
        private TextView txtComment;
        private TextView txtHart;

        private ViewHolder(View itemView) {
            super(itemView);


            this.imgVideoTham = (ImageView) itemView.findViewById(R.id.imgVideoTham);
            this.txtTag = (TextView) itemView.findViewById(R.id.txtTag);
            this.txtDay = (TextView) itemView.findViewById(R.id.txtDay);
            this.txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            this.txtHart = (TextView) itemView.findViewById(R.id.txtHart);


        }


    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
