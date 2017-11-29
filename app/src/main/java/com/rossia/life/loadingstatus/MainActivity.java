package com.rossia.life.loadingstatus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rossia.life.loadstatus.LoadingStatusLayout;

/**
 * @author pd_liu 2017/11/20.
 *         用于展示LoadingStatusLayout.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mLoadingBtn, mEmptyBtn, mNoNetBtn, mErrorBtn, mLibraryBtn;

    private LoadingStatusLayout mLoadingStatusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingStatusLayout = (LoadingStatusLayout) findViewById(R.id.loading_status_layout);

        mLoadingBtn = (Button) findViewById(R.id.loading);
        mEmptyBtn = (Button) findViewById(R.id.empty);
        mNoNetBtn = (Button) findViewById(R.id.no_net);
        mErrorBtn = (Button) findViewById(R.id.error);
        mLibraryBtn = (Button) findViewById(R.id.library);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mLoadingBtn.setOnClickListener(this);
        mEmptyBtn.setOnClickListener(this);
        mNoNetBtn.setOnClickListener(this);
        mErrorBtn.setOnClickListener(this);
        mLibraryBtn.setOnClickListener(this);

        mLoadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
    }

    @Override
    public void onClick(View v) {

        if (mLoadingBtn == v) {

            mLoadingStatusLayout.setStatus(LoadingStatusLayout.LOADING_STATUS);

            return;
        }
        if (mEmptyBtn == v) {

            mLoadingStatusLayout.setStatus(LoadingStatusLayout.EMPTY_STATUS);

            return;
        }

        if (mNoNetBtn == v) {

            mLoadingStatusLayout.setStatus(LoadingStatusLayout.NO_NET_WORK_STATUS);

            return;
        }

        if (mErrorBtn == v) {

            mLoadingStatusLayout.setStatus(LoadingStatusLayout.ERROR_STATUS);

            return;
        }
    }
}
