package com.li.games;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.li.Adapter.PuzzleItemAdapter;
import com.li.Tools.GamesUtil;
import com.li.Tools.ImagesUtil;
import com.li.Tools.ItemBeans;
import com.li.Tools.ScreenUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by li on 16-1-12.
 */
public class PuzzleMain extends Activity implements AdapterView.OnClickListener, AdapterView.OnItemClickListener {
    private Button p_bt_back;
    private Button p_bt_origin;
    private Button p_bt_reset;
    private TextView p_tv_counts;
    private TextView p_tv_times;
    private GridView p_gridview;
    public static int mType;
    private int select_type;
    private String mPicpath;
    private int picSelectID;
    private Bitmap picSelectedTemp;

    private Timer time;
    private List<Bitmap> mDataBitmap=new ArrayList<Bitmap>();
    public static int TIMER_INDEX = 0;
    public static int STEP_COUNTS=0;
    public static Bitmap mLastBitmap;
    private ImageView mImageView;


    private GamesUtil gamesUtil;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    TIMER_INDEX++;
                    p_tv_times.setText(""+TIMER_INDEX);
                    break;
                case 2:
                    STEP_COUNTS++;
                    p_tv_counts.setText(""+STEP_COUNTS);
        }
    }
    };
    private PuzzleItemAdapter adapter;
    private boolean mIsShowImg=true;
    private TextView p_tv_beststeps;
    private String type4;
    private String type3;
    private String type2;
    private boolean starttimeflag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle);

        // 初始化Views
        ininit();

        // 生成游戏数据
        generateGame();
    }

    /**
     * 生成游戏的数据
     */
    private void generateGame() {

        //讲传递过来的bitmap 分割成 mType*mType 张
        new ImagesUtil().createInitBitmaps(mType, picSelectedTemp, this);
        new GamesUtil().getPuzzleGenerator();

        for (ItemBeans temp : GamesUtil.mItemBeans) {
            mDataBitmap.add(temp.getmBitmap());
        }

        //那么如何获得mDataBitmap  的数据呢？？？？？？？
        adapter=new PuzzleItemAdapter(this,mDataBitmap);
        p_gridview.setAdapter(adapter);
        time = new Timer();

    }

    private void hanlderPicture(Bitmap bitmap) {
        int screenwidth= ScreenUtil.getScreenSize(this).widthPixels;
        int screenHeight=ScreenUtil.getScreenSize(this).heightPixels;
        picSelectedTemp= new ImagesUtil().resizeBitmap(screenwidth*0.8f,screenHeight*0.63f,bitmap);
    }

    private void ininit() {
        Intent intent=getIntent();
        mType=intent.getExtras().getInt("myType", 2);
        mPicpath=intent.getExtras().getString("mpicPath");
        picSelectID=intent.getExtras().getInt("picSelectID", 0);
        select_type=intent.getExtras().getInt("from");
        //Log.d("lixuan","游戏类型mType:  "+mType+"   "+"picSelectID为："+""+picSelectID+
         //       "   mPicpath；"+mPicpath+"   select_type:"+select_type);

        p_bt_back=(Button) findViewById(R.id.p_bt_back);
        p_bt_origin=(Button) findViewById(R.id.p_bt_origin);
        p_bt_reset=(Button) findViewById(R.id.p_bt_reset);

        p_tv_counts=(TextView) findViewById(R.id.p_tv_counts);
        p_tv_times=(TextView) findViewById(R.id.p_tv_times);
        p_tv_beststeps=(TextView)findViewById(R.id.p_tv_beststeps);

        p_bt_origin.setOnClickListener(this);
        p_bt_reset.setOnClickListener(this);
        p_bt_back.setOnClickListener(this);

        if (select_type==1) {
            picSelectedTemp = BitmapFactory.decodeResource(getResources(), picSelectID);
/*            ImageView test_ig=(ImageView)findViewById(R.id.test_ig);
            test_ig.setImageBitmap(bitmap);*/
        }else{
            //camera
            try {
                FileInputStream fos=new FileInputStream(mPicpath);
                picSelectedTemp=BitmapFactory.decodeStream(fos);
/*                ImageView test_ig=(ImageView)findViewById(R.id.test_ig);
                test_ig.setImageBitmap(bitmap);*/
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 对图片处理
        hanlderPicture(picSelectedTemp);

        p_gridview=(GridView) findViewById(R.id.p_gridview);
        p_gridview.setNumColumns(mType);
        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(
                picSelectedTemp.getWidth(),
                picSelectedTemp.getHeight());
        // 水平居中
        gridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        // 其他格式属性
        gridParams.addRule(
                RelativeLayout.BELOW,
                R.id.p_ll_1);
        gridParams.addRule(
                RelativeLayout.ABOVE,
                R.id.p_ll_3);
        // Grid显示
        p_gridview.setLayoutParams(gridParams);
        p_gridview.setHorizontalSpacing(0);
        p_gridview.setVerticalSpacing(0);
        p_gridview.setOnItemClickListener(this);

        addImgView();
        gamesUtil=new GamesUtil();

        //处理最好记录
        bestSteps();
    }

    private void bestSteps() {
        //if(new File(getFilesDir(),"steps").exists()){
            if(getSharedPreferences("steps", MODE_PRIVATE).getBoolean("file",false))
            {
            Log.d("lixuan","file is exits .");
            SharedPreferences pref=getSharedPreferences("steps", MODE_PRIVATE);
             type2=pref.getString("select2", "");
             type3=pref.getString("select3","");
             type4=pref.getString("select4","");

                if (mType==2) {
                    Log.d("lixuan", "最好记录 类型2:" + type2);
                    p_tv_beststeps.setText(type2);
                }else if(mType==3){
                    Log.d("lixuan", "最好记录 类型3:"+type3);
                    p_tv_beststeps.setText(type3);
                }else{
                    Log.d("lixuan", "最好记录 类型4:"+type4);
                    p_tv_beststeps.setText(type4);
                }
           } else {
            Log.d("lixuan","file is not exits .."+getFilesDir());
            SharedPreferences.Editor editor = getSharedPreferences("steps", MODE_PRIVATE).edit();
            editor.putBoolean("file",true);
            editor.putString("select2", "0");
            editor.putString("select3", "0");
            editor.putString("select4", "0");
            editor.commit();
            p_tv_beststeps.setText("0");
                SharedPreferences pref=getSharedPreferences("steps", MODE_PRIVATE);
                type2=pref.getString("select2", "0");
                type3=pref.getString("select3","0");
                type4=pref.getString("select4","0");
        }
    }

    private void addImgView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(
                R.id.rl_puzzle_main_main_layout);
        mImageView = new ImageView(PuzzleMain.this);
        mImageView.setImageBitmap(picSelectedTemp);
        int x = (int) (picSelectedTemp.getWidth() * 0.9F);
        int y = (int) (picSelectedTemp.getHeight() * 0.9F);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(x, y);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mImageView.setLayoutParams(params);
        relativeLayout.addView(mImageView);
        mImageView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.p_bt_back:
                PuzzleMain.this.finish();
                break;
            case R.id.p_bt_origin:
                Animation animShow = AnimationUtils.loadAnimation(
                        PuzzleMain.this, R.anim.image_show_anim);
                Animation animHide = AnimationUtils.loadAnimation(
                        PuzzleMain.this, R.anim.image_hide_anim);
                if (mIsShowImg) {
                    mImageView.startAnimation(animHide);
                    mImageView.setVisibility(View.GONE);
                    mIsShowImg = false;
                } else {
                    mImageView.startAnimation(animShow);
                    mImageView.setVisibility(View.VISIBLE);
                    mIsShowImg = true;
                }
                break;
            case R.id.p_bt_reset:
                cleanConfig();

                generateGame();
                starttimeflag=true;
                recreateData();
                adapter.notifyDataSetChanged();
                p_tv_times.setText("" + TIMER_INDEX);
                p_tv_counts.setText("" + STEP_COUNTS);
                p_gridview.setEnabled(true);
                break;
            default:
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanConfig();
        this.finish();
    }

/*    @Override
    protected void onStop() {
        super.onStop();
        // 清空相关参数设置
        cleanConfig();
        this.finish();
    }*/

    private void cleanConfig() {
        GamesUtil.mItemBeans.clear();

        time.cancel();
        TIMER_INDEX=0;
        STEP_COUNTS=0;
        if (mPicpath != null) {
            // 删除照片
            File file = new File(MainActivity.TEMP_IMAGE_PATH);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("lixuan","onItenm :"+position);
        if(gamesUtil.isMoveable(position)){
            gamesUtil.swapItems(GamesUtil.mItemBeans.get(position), GamesUtil.mBlankItemBean);
            // 重新获取图片
            recreateData();
            // 通知GridView更改UI
            adapter.notifyDataSetChanged();
            if (starttimeflag) {

                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                    }
                }, 0, 1000);
                starttimeflag=false;
            }

           STEP_COUNTS++;
            p_tv_counts.setText(""+STEP_COUNTS);

            if (gamesUtil.isSuccess()){
                recreateData();
                mDataBitmap.remove(mType * mType - 1);
                mDataBitmap.add(mLastBitmap);
                // 通知GridView更改UI
                adapter.notifyDataSetChanged();
                Toast.makeText(PuzzleMain.this, "拼图成功!",
                        Toast.LENGTH_LONG).show();
                storageGrades();
                p_gridview.setEnabled(false);
                time.cancel();

            }
        }else {
            Log.d("lixuan", "Item don't move");
        }

    }

    private void storageGrades() {

        SharedPreferences.Editor editor = getSharedPreferences("steps", MODE_PRIVATE).edit();
        boolean result=compareSteps(Integer.parseInt(type2),STEP_COUNTS);
        if (mType==2){
            if(result){
                editor.putString("select2",""+STEP_COUNTS);
                editor.commit();
                p_tv_beststeps.setText(""+STEP_COUNTS);
            }

        } else if (mType==3){
            if(result){
                editor.putString("select3",""+STEP_COUNTS);
                editor.commit();
                p_tv_beststeps.setText("" + STEP_COUNTS);
            }
        } else if (mType==4) {
            if(result){
                editor.putString("select4",""+STEP_COUNTS);
                editor.commit();
                p_tv_beststeps.setText(""+STEP_COUNTS);
            }
        }

    }

    private boolean compareSteps(int best,int nowsteps) {
        Log.d("lixuan","best="+best+"   "+"STEP_COUNTS="+nowsteps);
        if (best==0){
            return true;
        }
        if (best<nowsteps){
            return false;
        }else
            return true;
    }

    private void recreateData() {
        mDataBitmap.clear();
        for (ItemBeans temp : GamesUtil.mItemBeans) {
            mDataBitmap.add(temp.getmBitmap());
           // Log.d("lixuan","temp的信息："+temp.getmBitmapId());
        }
    }
}
