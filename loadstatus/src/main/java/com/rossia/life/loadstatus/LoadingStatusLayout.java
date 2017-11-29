package com.rossia.life.loadstatus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author pd_liu on 2017/11/1.
 *         加载状态布局.
 */

public class LoadingStatusLayout extends FrameLayout {

    private static final String TAG = "LoadingStatusLayout";

    /**
     * 不同的状态值.
     */
    @IntDef({SUCCESS_STATUS, ERROR_STATUS, EMPTY_STATUS, LOADING_STATUS, NO_NET_WORK_STATUS})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoadingStatus {
    }

    public static final int SUCCESS_STATUS = 0;
    public static final int ERROR_STATUS = 1;
    public static final int EMPTY_STATUS = 2;
    public static final int LOADING_STATUS = 3;
    public static final int NO_NET_WORK_STATUS = 4;

    private int DEFAULT_STATUS = LOADING_STATUS;

    /**
     * 不同状态的View.
     */
    private View mNoNetWorkView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;
    private View mLoadingView;

    private static int mNoNetWorkResID = LoadingStatusConfig.mNoNetWorkResID;
    private static int mErrorResID = LoadingStatusConfig.mErrorResID;
    private static int mEmptyResID = LoadingStatusConfig.mEmptyResID;
    private static int mLoadingResID = LoadingStatusConfig.mLoadingResID;

    /**
     * Context.
     */
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    /**
     * 管理View.
     */
    private final SparseArray<View> mViewArray = new SparseArray<>(10);

    public LoadingStatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        mLoadingView = mLayoutInflater.inflate(mLoadingResID, this, false);
        mErrorView = mLayoutInflater.inflate(mErrorResID, this, false);
        mNoNetWorkView = mLayoutInflater.inflate(mNoNetWorkResID, this, false);
        mEmptyView = mLayoutInflater.inflate(mEmptyResID, this, false);

        //StyledAttributes.
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingStatusLayout);
        DEFAULT_STATUS = typedArray.getInt(R.styleable.LoadingStatusLayout_status, 0);

        //Recycle.
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 设置当前页面的状态.
     *
     * @param status 状态值.
     */
    public void setStatus(@LoadingStatus int status) {

        switch (status) {

            case SUCCESS_STATUS:
                showView(SUCCESS_STATUS);
                break;

            case ERROR_STATUS:
                showView(ERROR_STATUS);
                break;

            case EMPTY_STATUS:
                showView(EMPTY_STATUS);
                break;

            case LOADING_STATUS:
                showView(LOADING_STATUS);
                break;
            case NO_NET_WORK_STATUS:
                showView(NO_NET_WORK_STATUS);
                break;
            default:
                break;
        }

    }

    /**
     * 进行切换View的不同状态.
     *
     * @param viewStatusID resourceID.
     */
    private void showView(int viewStatusID) {

        for (int i = 0; i < mViewArray.size(); i++) {
            View view = mViewArray.valueAt(i);
            view.setVisibility(GONE);
        }

        View view = mViewArray.get(viewStatusID);
        view.setVisibility(VISIBLE);
    }

    /**
     * Set no net work ui.
     *
     * @param layoutResID
     */
    public void setNoNetWorkView(@LayoutRes int layoutResID) {
        setStatusView(NO_NET_WORK_STATUS, layoutResID);
    }

    /**
     * Set success ui.
     *
     * @param layoutResID resourceId.
     */
    public void setSuccessView(@LayoutRes int layoutResID) {
        setStatusView(SUCCESS_STATUS, layoutResID);
    }

    /**
     * Set error ui.
     *
     * @param layoutResID resourceId.
     */
    public void setErrorView(@LayoutRes int layoutResID) {
        setStatusView(ERROR_STATUS, layoutResID);
    }

    /**
     * Set empty ui.
     *
     * @param layoutResID resourceId.
     */
    public void setEmptyView(@LayoutRes int layoutResID) {
        setStatusView(EMPTY_STATUS, layoutResID);
    }

    /**
     * Set loading ui.
     *
     * @param layoutResID resourceId.
     */
    public void setLoadingView(@LayoutRes int layoutResID) {
        setStatusView(LOADING_STATUS, layoutResID);
    }

    /**
     * 设置状态的视图
     *
     * @param status      状态
     * @param layoutResID 视图资源ID
     */
    private void setStatusView(int status, @LayoutRes int layoutResID) {

        View oldView = mViewArray.get(status);
        View newView = mLayoutInflater.inflate(layoutResID, null, false);

        //LayoutParams.
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        newView.setLayoutParams(layoutParams);

        //Update this state view.
        //Add and remove view.
        if (oldView != null && newView != null) {
            mViewArray.put(status, newView);
            removeView(oldView);
            addView(newView);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() == 0) {
            return;
        }

        /*
         将最后添加到此容器中的View作为最终的视图》.
         */
        if (getChildCount() > 1) {
            removeViews(0, getChildCount() - 2);
        }
        View contentView = getChildAt(getChildCount() - 1);
        mSuccessView = contentView;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLoadingView.setLayoutParams(layoutParams);
        mEmptyView.setLayoutParams(layoutParams);
        mErrorView.setLayoutParams(layoutParams);
        mNoNetWorkView.setLayoutParams(layoutParams);
        addView(mLoadingView);
        addView(mEmptyView);
        addView(mErrorView);
        addView(mNoNetWorkView);

        mViewArray.put(LOADING_STATUS, mLoadingView);
        mViewArray.put(EMPTY_STATUS, mEmptyView);
        mViewArray.put(ERROR_STATUS, mErrorView);
        mViewArray.put(SUCCESS_STATUS, mSuccessView);
        mViewArray.put(NO_NET_WORK_STATUS, mNoNetWorkView);

        //default show loading ui.
        showView(DEFAULT_STATUS);
    }
}
