package com.tik.tiktok.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.tik.tiktok.Adepter.UserVideoListAdepter;
import com.tik.tiktok.GsonUtils;
import com.tik.tiktok.Model.UserVideoDetailResponce;
import com.tik.tiktok.Model.UserVideoList;
import com.tik.tiktok.R;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileFullDetailActivity extends AppCompatActivity {
    private TextView txtTitle;
    private RoundedImageView imgUser;
    private TextView txtName;
    private TextView txtUserName;
    private TextView txtUserFollower;
    private RecyclerView employeeList;
    GridLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isApiCall;
    GsonUtils gsonUtils;
    String has_next_page = "0";
    String has_more = "";
    UserVideoListAdepter userListAdepter;
    ArrayList<UserVideoList> userVideoLists = new ArrayList<>();
    String follower, userprofilePic, username, fullname, id;
    private static final String TAG = "ProfileFullDetailActivi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_profile_detail_activity);
        follower = getIntent().getStringExtra("follower");
        userprofilePic = getIntent().getStringExtra("userprofilePic");
        username = getIntent().getStringExtra("username");
        fullname = getIntent().getStringExtra("fullname");
        id = getIntent().getStringExtra("id");
        findViews();

        txtName.setText(fullname);
        txtUserFollower.setText(follower);
        txtUserName.setText("@" + username);

        Picasso.with(ProfileFullDetailActivity.this)
                .load(userprofilePic)
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(imgUser);
        getVIdeoList();
    }

    private void findViews() {
        userVideoLists = new ArrayList<>();
        gsonUtils = GsonUtils.getInstance();
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgUser = (RoundedImageView) findViewById(R.id.imgUser);
        txtName = (TextView) findViewById(R.id.txtName);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserFollower = (TextView) findViewById(R.id.txtUserFollower);
        employeeList = (RecyclerView) findViewById(R.id.employeeList);


        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        employeeList.setLayoutManager(mLayoutManager);

        employeeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView employeeList, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (!isApiCall && !has_more.equalsIgnoreCase("0")) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            getVIdeoList();
                        }
                    }
                }
            }
        });
    }

    public void getVIdeoList() {
        isApiCall = true;
        final ProgressDialog mProgressDialog = new ProgressDialog(ProfileFullDetailActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.addHeader("x-requested-with", "XMLHttpRequest");
        RequestParams params = new RequestParams();
        params.put("region", "us");
        params.put("user_id", id);
        params.put("max_cursor", has_next_page);
        try {
            client.setConnectTimeout(12000);
            client.post("https://vidnice.com/APIswitch.php?key=userownvideos", params, new BaseJsonHttpResponseHandler<UserVideoDetailResponce>() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserVideoDetailResponce response) {
                    isApiCall = false;
                    mProgressDialog.dismiss();
                    try {
                        if (response.getTiktokdata().getAweme_list() != null) {
                            has_more = response.getTiktokdata().getHas_more();
                            has_next_page = response.getTiktokdata().getMax_cursor();
                            userVideoLists.addAll(response.getTiktokdata().getAweme_list());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setAdepter();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserVideoDetailResponce errorResponse) {
                    mProgressDialog.dismiss();
                    Log.e(TAG, "onFailure() called with: statusCode = [" + statusCode + "], headers = [" + headers + "], throwable = [" + throwable + "], rawJsonData = [" + rawJsonData + "], errorResponse = [" + errorResponse + "]");
                }

                @Override
                protected UserVideoDetailResponce parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    Log.e(TAG, "parseResponse() called with: rawJsonData = [" + rawJsonData + "], isFailure = [" + isFailure + "]");

                    try {
                        if (!isFailure && !rawJsonData.isEmpty()) {
                            return gsonUtils.getGson().fromJson(rawJsonData, UserVideoDetailResponce.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFinish() {
                    super.onFinish();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdepter() {
        if (userListAdepter == null) {
            userListAdepter = new UserVideoListAdepter(ProfileFullDetailActivity.this, userVideoLists);
            employeeList.setAdapter(userListAdepter);
        } else {
            userListAdepter.loadMore(userVideoLists);
            userListAdepter.notifyItemInserted(userVideoLists.size());
        }
    }


}
