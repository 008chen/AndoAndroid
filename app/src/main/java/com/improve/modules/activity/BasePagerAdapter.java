package com.improve.modules.activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.improve.R;
import com.improve.base.adapter.ContentPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javakam on 2018/6/20.
 */
public class BasePagerAdapter extends PagerAdapter {
    private Context context;
    private Map<ContentPage, View> mPageMap = new HashMap<>();

    public BasePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ContentPage.SIZE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ContentPage page = ContentPage.getPage(position);
        View view = getPageView(page);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
        return view;
    }

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(context, R.color.app_color_description));
            textView.setText("这是第 " + (page.getPosition() + 1) + " 个 Item 的内容区");
            if (page.getPosition() == ContentPage.ITEM0.getPosition()) {
                textView.setOnClickListener(v -> {
//                 context.startActivity(new Intent(context, SampleActivity1.class));
                });
            } else if (page.getPosition() == ContentPage.ITEM1.getPosition()) {
                textView.setOnClickListener(v -> {
//                 context.startActivity(new Intent(context, UIDrawingProcessActivity.class));
                });

            }
            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
