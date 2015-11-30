package cn.xunshi.com.limiliang;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

import org.w3c.dom.Text;

public class MainActivity extends FragmentActivity {

    private IndicatorViewPager indicatorViewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SViewPager viewPager = (SViewPager)findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator)findViewById(R.id.tabmain_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator,viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);

    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = { "主页", "最新推荐", "我" };
        private int[] tabIcons = { R.drawable.maintab_1_selector, R.drawable.maintab_2_selector,
                R.drawable.maintab_4_selector };
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }


        public int getCount() {
            return tabNames.length;
        }


        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = (TextView) inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;

            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            textView.setText(tabNames[position]);
            return textView;
        }


        public Fragment getFragmentForPage(int position) {
            FirstLayerFragment mainFragment = new FirstLayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(FirstLayerFragment.INTENT_STRING_TABNAME, tabNames[position]);
            bundle.putInt(FirstLayerFragment.INTENT_INT_INDEX, position);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }
}
