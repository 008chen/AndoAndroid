package com.improve.modules.greendao;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.improve.R;
import com.improve.base.fragment.BaseSwipeFragment;
import com.improve.data.bean.User;
import com.improve.data.dao.DaoUtils;
import com.improve.data.dao.RoleDao;
import com.improve.data.dao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * GreenDao + RxJava
 * <p>
 * 可喜可贺 GreenDao 是支持 RxJava 的
 * <p>
 * Created by javakam on 2018-7-5 19:56:05
 */
public class RxGreenDaoSwipeFragment extends BaseSwipeFragment {

    TextView tvContent;

    private UserDao userDao;
    private RoleDao roleDao;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_green_dao;
    }

    @Override
    protected void initViews(View v) {
        initTopBar("GreenDao", true);
        tvContent = v.findViewById(R.id.content);
        // 当 TextView内容过多时，可以上下滚动查看
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        //1 获取 XXXDao
        userDao = DaoUtils.getDao().getUserDao();
        roleDao = DaoUtils.getDao().getRoleDao();


        rootView.findViewById(R.id.btAdd).setOnClickListener(this);
        rootView.findViewById(R.id.btDelete).setOnClickListener(this);
        rootView.findViewById(R.id.btChange).setOnClickListener(this);
        rootView.findViewById(R.id.btGetAll).setOnClickListener(this);
        rootView.findViewById(R.id.btGetByKey).setOnClickListener(this);
        rootView.findViewById(R.id.btGetByName).setOnClickListener(this);
        rootView.findViewById(R.id.btGetByName2).setOnClickListener(this);
        rootView.findViewById(R.id.btGetByName3).setOnClickListener(this);
        rootView.findViewById(R.id.btDeleteTable).setOnClickListener(this);

    }

    int i = 0;

    //2 操作 XXXDao
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAdd:
                User user1 = new User();
                user1.setId(1000L + i);
                user1.setName("小明" + i);
                userDao.insert(user1);
                i++;
                break;
            case R.id.btChange:
                // 修改 id=1000L 为 小明明
                User user2 = new User();
                user2.setId(1000L);
                user2.setName("小明明");
                userDao.insertOrReplace(user2);
                Toast.makeText(mActivity, "修改 id=1000 的用户为小明明 ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btGetAll:
                List<User> userList = userDao.loadAll();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < userList.size(); i++) {
                    sb.append(userList.get(i).toString()).append(" ; \n");
                }
                tvContent.setText("查询全部数据==>\n" + sb.toString());
                break;
            case R.id.btGetByKey:
                User user4 = userDao.load(1000L);
                tvContent.setText("查询到的用户==>\n" + user4.toString());
                break;
            case R.id.btGetByName:
                List<User> userList1 = userDao.queryRaw("where name = ? ", "小明3");
                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i < userList1.size(); i++) {
                    sb1.append(userList1.get(i).toString()).append(" ; \n");
                }
                tvContent.setText("查询到的用户为==>\n" + sb1.toString());
                break;
            case R.id.btGetByName2:
                List<User> userList2 = userDao.queryRaw("where _id between ? and ? ", "1003", "1006");
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < userList2.size(); i++) {
                    sb2.append(userList2.get(i).toString()).append(" ; \n");
                }
                tvContent.setText("查询到的用户为==>\n" + sb2.toString());
                break;
            case R.id.btGetByName3:
                QueryBuilder<User> builder = userDao.queryBuilder();
                List<User> userList3 = builder.where(UserDao.Properties.Id.gt(1005)).build().list();
                StringBuilder sb3 = new StringBuilder();
                for (int i = 0; i < userList3.size(); i++) {
                    sb3.append(userList3.get(i).toString()).append(" ; \n");
                }
                tvContent.setText("查询到的用户为==>\n" + sb3.toString());
                break;
            case R.id.btDelete:
                userDao.deleteByKey(1000L);
                Toast.makeText(mActivity, "删除 id=1000 的用户", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btDeleteTable:
                userDao.deleteAll();
                Toast.makeText(mActivity, "删除全部用户", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
