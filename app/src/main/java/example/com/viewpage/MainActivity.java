package example.com.viewpage;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private LinearLayout pointGroup;

    private TextView description;

    private boolean isRunning;

    private final int[] imageIds = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};

    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };

    private ArrayList<ImageView> imageList;

    private int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pointGroup = (LinearLayout) findViewById(R.id.point_group);
        description = (TextView) findViewById(R.id.text);
        description.setText(imageDescriptions[0]);

        imageList =new ArrayList<ImageView>();
        for (int i = 0; i<imageIds.length; i++){
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);

            ImageView point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.rightMargin = 10;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                position = position%imageList.size();
                description.setText(imageDescriptions[position]);
                pointGroup.getChildAt(position).setEnabled(true);
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 2000);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            if (isRunning){
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    @Override
    protected void onDestroy(){
        isRunning =false;
        super.onDestroy();
    }



    class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            container.addView(imageList.get(position%imageList.size()));
            return imageList.get(position%imageList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
            object = null;
        }
    }
}
