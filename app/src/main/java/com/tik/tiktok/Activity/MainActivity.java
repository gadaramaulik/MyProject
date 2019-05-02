package com.tik.tiktok.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.tik.tiktok.Adepter.UserListAdepter;
import com.tik.tiktok.GsonUtils;
import com.tik.tiktok.Model.UserDetail;
import com.tik.tiktok.Model.UserResponce;
import com.tik.tiktok.R;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText username;
    RecyclerView recyclerView;
    TextView btnSearch;
    UserListAdepter userListAdepter;
    private static final String TAG = "MainActivity";
    GsonUtils gsonUtils;
    private ArrayList<UserDetail> userListArray = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isApiCall;

    String has_next_page = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

    }

    public void findView() {
        gsonUtils = GsonUtils.getInstance();
        username = (EditText) findViewById(R.id.editUserName);
        btnSearch = (TextView) findViewById(R.id.btnSearch);
        recyclerView = (RecyclerView) findViewById(R.id.employeeList);


        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (!isApiCall && !has_next_page.equalsIgnoreCase("false")) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            getUserList(username.getText().toString());
                        }
                    }
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListArray.clear();
                has_next_page = "0";
                getUserList(username.getText().toString().trim().replaceAll("\\s+",""));
            }
        });
    }

    public void setAdepter() {
        if (userListAdepter == null) {
            userListAdepter = new UserListAdepter(MainActivity.this, userListArray);
            recyclerView.setAdapter(userListAdepter);
        } else {
            userListAdepter.loadMore(userListArray);
            userListAdepter.notifyItemInserted(userListArray.size());
        }
    }

    public void getUserList(String user) {
        isApiCall = true;
        String nextpage;
        if (has_next_page.equalsIgnoreCase("0")) {
            nextpage = "https://www.tiktokdom.com/search/" + user + "?type=user ";
        } else {
            nextpage = "https://www.tiktokdom.com/search/" + user + "?type=user " + "page=" + has_next_page;
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.addHeader("x-requested-with", "XMLHttpRequest");

        try {
            client.setConnectTimeout(12000);
            client.get(nextpage, new BaseJsonHttpResponseHandler<UserResponce>() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserResponce response) {
                    isApiCall = false;
                    mProgressDialog.dismiss();
                    has_next_page = response.getHas_next_page();
                    if (response.getLists() != null) {
                        if (response.getLists().size() > 0) {
                            userListArray.addAll(response.getLists());
                            setAdepter();

                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserResponce errorResponse) {
                    mProgressDialog.dismiss();
                    Log.e(TAG, "onFailure() called with: statusCode = [" + statusCode + "], headers = [" + headers + "], throwable = [" + throwable + "], rawJsonData = [" + rawJsonData + "], errorResponse = [" + errorResponse + "]");
                }

                @Override
                protected UserResponce parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    Log.e(TAG, "parseResponse() called with: rawJsonData = [" + rawJsonData + "], isFailure = [" + isFailure + "]");

                    try {
                        if (!isFailure && !rawJsonData.isEmpty()) {
                            return gsonUtils.getGson().fromJson(rawJsonData, UserResponce.class);
                        }
                    } catch (Exception e) {

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

}
