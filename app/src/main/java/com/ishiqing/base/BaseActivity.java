package com.ishiqing.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.ishiqing.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by javakam on 2018/6/20.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.topbar)
    protected QMUITopBar mTopBar;
    @Nullable
    @BindView(R.id.emptyView)
    protected QMUIEmptyView mEmptyView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();

        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initViews();

    /**
     * 可以通过重写该方法，重新定义TopBar事件
     */
    public void initTopBar(String title, boolean showImageButtonBack) {
        if (mTopBar != null) {
            mTopBar.setTitle(title);
            if (showImageButtonBack) {
                mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    /**
     * loading
     */
    public void showLoadingEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.show(true);
        }
    }

    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.hide();
        }
    }

    /**
     * 显示联网异常的 QMUIEmptyView
     *
     * @param onClickListener 按钮的onClick监听，不需要则传null
     */
    public void showNetErrorEmptyView(@Nullable View.OnClickListener onClickListener) {
        mEmptyView.show(false, //是否要显示loading
                //标题的文字，不需要则传null
                getResources().getString(R.string.emptyView_mode_desc_fail_title),
                // 详情文字，不需要则传null
                getResources().getString(R.string.emptyView_mode_desc_fail_desc),
                //按钮的文字，不需要按钮则传null
                getResources().getString(R.string.emptyView_mode_desc_retry),
                onClickListener);
    }

}