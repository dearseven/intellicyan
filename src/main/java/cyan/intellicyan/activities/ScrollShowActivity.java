package cyan.intellicyan.activities;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cyan.intellicyan.R;
import cyan.intellicyan.components.forever_anim_viewpager.FixedScroller;

public class ScrollShowActivity extends AppCompatActivity implements ViewPager.OnAdapterChangeListener {
    public List<ImageView> mImageViewList = new ArrayList<ImageView>(4);//最少有4张，不然就不能循环滚动
    public ViewPager viewPager;
    public boolean isLoop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //------------start -滚动的viewpager
        //最少有4张，不然就不能循环滚动

        //1 这个方法是填充图片，因为太长了 所以移出去了
        fillImages();

        //2 初始化 ViewPager
        viewPager = (ViewPager) findViewById(R.id.h_s_vp);

        //2.1 这个适配器也很重要哦
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnAdapterChangeListener(this);

        //3 设置viewpager的滚动动画 让其看起来更流畅呀
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            FixedScroller scroller = new FixedScroller(viewPager.getContext(), sInterpolator);
            mScroller.set(viewPager, scroller);
        } catch (Exception ex) {

        }
        // 4 滚动
        setTopStartView();
        //------------end-- -滚动的viewpager
    }

    @Override
    protected void onDestroy() {
        isLoop = false;
        super.onDestroy();
    }

    /**
     * 启动轮播图轮播
     */
    public void setTopStartView() {

        // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isLoop) {
                    try {
                        SystemClock.sleep(2000);
                        handler.sendEmptyMessage(MSG_CHANGE_CURRENT_TOP_IMG);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    /**
     * 切换当前显示的图片
     */
    private final static int MSG_CHANGE_CURRENT_TOP_IMG = 1;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_CHANGE_CURRENT_TOP_IMG:
                    try {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                    } catch (Exception e) {
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void imgClick(View v) {
        Toast.makeText(this, "" + v.getTag(R.id.view_content_id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
        Toast.makeText(this, "onAdapterChanged", Toast.LENGTH_SHORT).show();
    }


    /**
     * 循环滚动ViewPger的适配器
     */
    public class ViewPagerAdapter extends PagerAdapter {

        public ViewPagerAdapter() {
            super();
        }

        /**
         * 该方法将返回所包含的 Item总个数。为了实现一种循环滚动的效果，返回了基本整型的最大值，这样就会创建很多的Item,
         * 其实这并非是真正的无限循环。
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 判断出去的view是否等于进来的view 如果为true直接复用
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来，就是position，
         * 因为mImageViewList只有五条数据，而position将会取到很大的值， 所以使用取余数的方法来获取每一条数据项。
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //if (mImageViewList.size() > 3) {
            container.removeView(mImageViewList.get(position % mImageViewList.size()));
            //}
        }

        /**
         * 创建一个view，
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(mImageViewList.get(position % mImageViewList.size()));
            } catch (IllegalStateException e) {
                container.removeView(mImageViewList.get(position % mImageViewList.size()));
            }
            return mImageViewList.get(position % mImageViewList.size());
        }
    }

    private static class Image {
        public int type;//1 resource，2 网络down下来的
        public String filePath;
        public int imageId;

        public Image(int type, int imageId) {
            this.type = type;
            this.imageId = imageId;
        }

        public Image(int type, String filePath) {
            this.type = type;
            this.filePath = filePath;
        }
    }

    /**
     * 压缩一下bitmap 防止内存溢出呀
     *
     * @param bitmap
     * @return
     */
    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        // 长和宽各缩小一半 会不会被人打啊
        matrix.postScale(0.5f, 0.5f); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 填充图片数据
     */
    private void fillImages() {
        Image img1 = new Image(1, R.mipmap.img_1);
        Image img2 = new Image(1, R.mipmap.img_2);
        Image img3 = new Image(1, R.mipmap.img_1);
        Image img4 = new Image(1, R.mipmap.img_2);

        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(img1.imageId);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(img2.imageId);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(img1.imageId);
        ImageView iv4 = new ImageView(this);
        iv4.setImageResource(img2.imageId);

        iv1.setTag(R.id.view_content_id, "iv1");
        iv2.setTag(R.id.view_content_id, "iv2");
        iv3.setTag(R.id.view_content_id, "iv3");
        iv4.setTag(R.id.view_content_id, "iv4");

        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgClick(v);
            }
        });
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgClick(v);
            }
        });
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgClick(v);
            }
        });
        iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgClick(v);
            }
        });

        mImageViewList.add(iv1);
        mImageViewList.add(iv2);
        mImageViewList.add(iv3);
        mImageViewList.add(iv4);
    }
}
