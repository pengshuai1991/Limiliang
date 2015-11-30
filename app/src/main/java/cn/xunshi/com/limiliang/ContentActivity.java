package cn.xunshi.com.limiliang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import cn.xunshi.com.entity.Designer;
import cn.xunshi.com.entity.Photo;
import cn.xunshi.com.entity.Works;
import cn.xunshi.com.okhttp.OkHttpClientManager;

public class ContentActivity extends AppCompatActivity {

    private TextView mTv;
    private List<Photo> photos;
    private Designer worksDesigner;
    ViewGroup viewGroup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("key").equals("designer")) {
            Designer designer = (Designer) bundle.get("designer");
            Log.e("pengshu", designer.getDesignername()+" "+designer.getDesignerphone());

            TextView desinameTextView = (TextView) findViewById(R.id.desinametext);
            TextView desiphoneTextView = (TextView) findViewById(R.id.desiphonetext);
            desinameTextView.setTextSize(33.0f);
            desiphoneTextView.setTextSize(33.0f);

            desinameTextView.setText("设计师："+designer.getDesignername());
            desiphoneTextView.setText("联系方式: " + designer.getDesignerphone());


        } else if (bundle.getString("key").equals("works")) {
            Works works = (Works) bundle.get("works");
            Log.e("pengshu", works.getWorksname());

            TextView worksnameText = (TextView) findViewById(R.id.worksnametext);
            TextView workspriceText = (TextView) findViewById(R.id.workspricetext);
            TextView worksmaterialText = (TextView) findViewById(R.id.worksmaterailtext);

            workspriceText.setText("价格:"+works.getWorksprice());
            worksnameText.setText("作品: " + works.getWorksname());
            worksmaterialText.setText("材料: "+works.getMaterial());

            OkHttpClientManager.getAsyn("http://192.168.0.102:8080/XunShi/designer.do?designerid=" + works.getDesignerid(),
                    new OkHttpClientManager.ResultCallback<List<Designer>>() {

                        public void onError(Request request, Exception e) {
                            e.printStackTrace();
                        }

                        public void onResponse(List<Designer> desi) {

                            if (desi != null) {
                               Log.e("pengshu", "designername=" + desi.get(0).getDesignername());
                                worksDesigner = desi.get(0);
                                TextView desinameTextView = (TextView) findViewById(R.id.desinametext);
                                TextView desiphoneTextView = (TextView) findViewById(R.id.desiphonetext);

                                 desinameTextView.setText("设计师："+worksDesigner.getDesignername());
                                 desiphoneTextView.setText("联系方式: "+worksDesigner.getDesignerphone());

                            }
                        }
                    });

            OkHttpClientManager.getAsyn("http://192.168.0.102:8080/XunShi/photo.do?worksid=" + works.getWorksid(),
                    new OkHttpClientManager.ResultCallback<List<Photo>>() {

                        public void onError(Request request, Exception e) {
                            e.printStackTrace();
                        }

                        public void onResponse(List<Photo> ps) {

                            photos = ps;
                            Log.e("pengshu", "photos size = " + photos.size());
                            viewGroup = (ViewGroup) findViewById(R.id.viewgroup);

                            ImageView[] imageViews = new ImageView[photos.size()];
                            for (int i = 0; i < imageViews.length; i++) {
                                ImageView imageView = new ImageView(ContentActivity.this);
                                imageView.setLayoutParams(new LinearLayout.LayoutParams(840, 780));
                                imageViews[i] = imageView;
                                Log.e("pengshu", photos.get(i).getPhotoname());
                                OkHttpClientManager.displayImage(imageView, "http://192.168.0.102:8080/XunShi/images/" + photos.get(i).getPhotoname());
                                viewGroup.addView(imageView);
                            }


                        }
                    });


        }


    }


}
