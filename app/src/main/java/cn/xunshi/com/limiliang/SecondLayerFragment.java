package cn.xunshi.com.limiliang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.squareup.okhttp.Request;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

import cn.xunshi.com.entity.Designer;
import cn.xunshi.com.entity.Works;
import cn.xunshi.com.okhttp.OkHttpClientManager;

public class SecondLayerFragment extends LazyFragment {

    private PullToZoomListView listView;
    private String[] adapterData;
    public static final String INTENT_STRING_TABNAME = "intent_String_tabName";
    public static final String INTENT_INT_POSITION = "intent_int_position";
    private String tabName;
    private int position;
    private TextView textView;
    private ProgressBar progressBar;
    private List<Works> workses;
    private List<Designer> designers;
    private ContentAdapter mContentAdapter;

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        tabName = getArguments().getString(INTENT_STRING_TABNAME);
        position = getArguments().getInt(INTENT_INT_POSITION);
        setContentView(R.layout.fragment_tabmain_item);
        mContentAdapter = new ContentAdapter();

        listView = (PullToZoomListView) findViewById(R.id.listview);

        if (tabName.equals("主页")){
            switch (position) {
                case 0:
                    OkHttpClientManager.getAsyn("http://192.168.0.102:8080/XunShi/works.do",
                            new OkHttpClientManager.ResultCallback<List<Works>>() {

                                public void onError(Request request, Exception e) {
                                    e.printStackTrace();
                                }

                                public void onResponse(List<Works> us) {
                                    Log.e("pengshu", us.size() + " ");
                                    workses = us;
                                    mContentAdapter.setContentworkses(us);
                                }
                            });
                    break;
                case 1:
                    OkHttpClientManager.getAsyn("http://192.168.0.102:8080/XunShi/designer.do",
                            new OkHttpClientManager.ResultCallback<List<Designer>>() {

                                public void onError(Request request, Exception e) {
                                    e.printStackTrace();
                                }

                                public void onResponse(List<Designer> ds) {
                                    Log.e("pengshu", ds.size() + " " + ds.get(1).getDesignername());
                                    designers = ds;
                                    mContentAdapter.setContentdesigners(ds);

                                }
                            });
                    break;
                case 2:

                    break;
            }
        } else if (tabName.equals("最新推荐")) {


        } else if (tabName.equals("我")) {
            switch (position) {
                case 0:
                    break;
                case 1:

                    break;
            }
        }

        adapterData = new String[]{"Activity", "Service", "Content Provider",
                "Intent", "BroadcastReceiver", "ADT", "Sqlite3",
                "HttpClient", "DDMS", "Android Studio", "Fragment", "Loader"};

        listView.setAdapter(mContentAdapter);

        listView.getHeaderView().setImageResource(R.drawable.splash01);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);

                Bundle bundle = new Bundle();


                if (designers != null && position != 0) {
                    bundle.putSerializable("designer", designers.get(position-1));
                    bundle.putString("key","designer");
                } else if (workses != null && position != 0 ) {
                    bundle.putSerializable("works", workses.get(position-1));
                    bundle.putString("key","works");
                } else if(position == 0){

                    bundle.putString("key","top");
                }else{

                    bundle.putString("key","nobody");
                }

                 i.putExtras(bundle);
                startActivity(i);
                Log.e("pengshu", "position  " + position + " ");
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);
        handler.sendEmptyMessageDelayed(1, 500);
    }


    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeMessages(1);
    }


    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        ;
    };


    private class ContentAdapter extends BaseAdapter {

        private String[] data = new String[]{"Activity", "Service", "Content Provider",
                "Intent", "BroadcastReceiver", "ADT", "Sqlite3",
                "HttpClient", "DDMS", "Android Studio", "Fragment", "Loader"};

        private LayoutInflater inflater;
        private List<Works> contentworkses;
        private List<Designer> contentdesigners;

        public void setContentworkses(List<Works> contentworkses) {
            this.contentworkses = contentworkses;
        }

        public void setContentdesigners(List<Designer> contentdesigners) {
            this.contentdesigners = contentdesigners;
        }

        public ContentAdapter() {
            inflater = LayoutInflater.from(getApplicationContext());
        }

//        public ContentAdapter(String[] data) {
//            this.data = data;
//            inflater = LayoutInflater.from(getApplicationContext());
//        }

        public int getCount() {
            if (contentworkses != null) {
                return contentworkses.size();
            } else if (contentdesigners != null) {
                return contentdesigners.size();
            }
            return data.length;
        }

        public String getItem(int position) {
            if (contentworkses != null) {
                return contentworkses.get(position).getWorksname();
            } else if (contentdesigners != null) {
                return contentdesigners.get(position).getDesignername();
            }
            return data[position];
        }


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = (LinearLayout) inflater.inflate(R.layout.layout_item, parent, false);
            }

            LinearLayout layout = (LinearLayout) convertView;
            ImageView imageView = (ImageView) layout.findViewById(R.id.image);
            TextView textView = (TextView) layout.findViewById(R.id.text);
            //  imageView.setImageResource(R.drawable.pic1);
            // textView.setText(data[position]);
            if (contentworkses != null) {
                OkHttpClientManager.displayImage(imageView,
                        "http://192.168.0.102:8080/XunShi/images/" + contentworkses.get(position).getWorkspicture());

                textView.setText(contentworkses.get(position).getWorksname());
            }

            if (contentdesigners != null) {
                imageView.setImageResource(R.drawable.slide);

                textView.setText(contentdesigners.get(position).getDesignername());
            }

            return layout;
        }
    }

    private Bitmap getLocalBitmap(String url) {

        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
