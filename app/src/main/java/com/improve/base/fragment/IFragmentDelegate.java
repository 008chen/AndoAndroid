package com.improve.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import androidx.annotation.Nullable;
import android.view.View;

import com.improve.base.BaseQMUIFragmentActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

public interface IFragmentDelegate {
    BaseQMUIFragmentActivity onAttach(Context context);

    void initTopBar(QMUITopBar topBar, String title, boolean showImageBack);

    void shortToast(Context context, String msg);

    void longToast(Context context, String msg);

    void showLoadingEmptyView();

    void hideEmptyView();

    void showNetErrorEmptyView(@Nullable View.OnClickListener onClickListener);

    void startService(Intent service);

    void stopService(Intent service);

    void bindService(Intent service, ServiceConnection conn, int flags);

    void unbindService(ServiceConnection conn);
}