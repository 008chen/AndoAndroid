package com.improve.modules.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;

import com.improve.R;
import com.improve.UIRouter;
import com.improve.base.fragment.BaseSwipeFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

/**
 * 兼容 4.0
 * <p>
 * 在{@link ServiceFragment4}上加入了线程演示
 * <p>
 * Created by javakam on 2018/6/17.
 */
public class ServiceFragment4 extends BaseSwipeFragment {

    private QMUIRoundButton button;

    private Intent intent;
    int first = 1;
    private MyService4.MyService4Binder myService4Binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("ServiceFragment4.onServiceConnected");
            myService4Binder = (MyService4.MyService4Binder) service;
            if (first == 1) {
                first++;
                return;
            }
            Parcel parcel1 = Parcel.obtain();
            //更改线程的日期输出格式
            parcel1.writeString("HH:mm:ss");
            Parcel parcel2 = Parcel.obtain();
            try {
                boolean transact = myService4Binder.transact(123, parcel1, parcel2, IBinder.FLAG_ONEWAY);
                //通信成功
                System.out.println("通信成功 ：" + transact + "  " + parcel2.readString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("ServiceFragment4.onServiceDisconnected");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            System.out.println("ServiceFragment4.onBindingDied");
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intent = new Intent(mActivity, MyService4.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_service_3;
    }

    @Override
    protected void initViews(View v) {
        initTopBar(UIRouter.FRAG_SERVICE4, true);
        button = v.findViewById(R.id.btnStart);
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialogUtil.dismiss();
            }
        }, 1500);

        v.findViewById(R.id.btnStart).setOnClickListener(this);
        v.findViewById(R.id.btnBind).setOnClickListener(this);
        v.findViewById(R.id.btnUnbind).setOnClickListener(this);
        v.findViewById(R.id.btnStop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                mActivity.startService(intent);
                break;
            case R.id.btnBind:
                mTipDialogUtil.createIconWithTipDialog(mActivity, QMUITipDialog.Builder.ICON_TYPE_LOADING
                        , "正在加载...");
                mActivity.bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbind:
                try {
                    mTipDialogUtil.dismiss();
                    mActivity.unbindService(conn);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
            case R.id.btnStop:
                mActivity.stopService(intent);
                break;
            default:
                break;
        }
    }
}
