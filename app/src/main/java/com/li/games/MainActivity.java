package com.li.games;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.li.Adapter.GirdViewAdapter;
import com.li.Adapter.Picture;
import com.li.Tools.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_22;
    private GridView gridView;
    private static String level2="2 X 2";
    private static String level3="3 X 3";
    private static String level4="4 X 4";
    private boolean flagdisplaylevel=false;
    private List<Picture> pictures;
    public  String mTypes="2 X 2";
    private static final String IMAGE_TYPE="image/*";
    private static final int RESULT_IMAGE=100;

    public static final String TEMP_IMAGE_PATH=Environment.getExternalStorageDirectory().getPath()+"/temp.png";
    private static final int Result_CAMERA=200;
    private int mType;
    private static int select_desk=1;
    private static int select_camera=2;
    private static int select_photo=3;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private LayoutInflater mLayoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_2=(TextView)findViewById(R.id.tv_2);
/*        tv_3=(TextView)findViewById(R.id.tv_3);
        tv_4=(TextView)findViewById(R.id.tv_4);
        tv_22=(TextView)findViewById(R.id.tv_22);*/
        // mType view
        mLayoutInflater = (LayoutInflater) getSystemService(
                LAYOUT_INFLATER_SERVICE);
        mPopupView = mLayoutInflater.inflate(
                R.layout.xpuzzle_main_type_selected, null);
        tv_22 = (TextView) mPopupView.findViewById(R.id.tv_22);
        tv_3 = (TextView) mPopupView.findViewById(R.id.tv_3);
        tv_4 = (TextView) mPopupView.findViewById(R.id.tv_4);
        // 监听事件


        gridView=(GridView)findViewById(R.id.gridview);

        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_22.setOnClickListener(this);
        pictures=new ArrayList<Picture>();
        initPicture();

        GirdViewAdapter adapter=new GirdViewAdapter(this,pictures);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new GriviewOnitemclick());


    }

    private void initPicture() {

        Picture bean1=new Picture();bean1.setPic(R.drawable.pic1);
        Picture bean2=new Picture();bean2.setPic(R.drawable.pic2);
        Picture bean3=new Picture();bean3.setPic(R.drawable.pic3);
        Picture bean4=new Picture();bean4.setPic(R.drawable.pic4);
        Picture bean5=new Picture();bean5.setPic(R.drawable.pic5);
        Picture bean6=new Picture();bean6.setPic(R.drawable.pic6);
        Picture bean7=new Picture();bean7.setPic(R.drawable.pic7);
        Picture bean8=new Picture();bean8.setPic(R.drawable.pic8);
        Picture bean9=new Picture();bean9.setPic(R.drawable.pic9);
        Picture bean10=new Picture();bean10.setPic(R.drawable.pic10);
        Picture bean11=new Picture();bean11.setPic(R.drawable.pic11);
        Picture bean12=new Picture();bean12.setPic(R.drawable.pic12);
        Picture bean13=new Picture();bean13.setPic(R.drawable.pic13);
        Picture bean14=new Picture();bean14.setPic(R.drawable.pic14);
        Picture bean15=new Picture();bean15.setPic(R.drawable.pic15);
        Picture bean0=new Picture();bean0.setPic(R.drawable.plus);
        pictures.add(bean1);pictures.add(bean2);pictures.add(bean3);pictures.add(bean4);
        pictures.add(bean5);pictures.add(bean6);pictures.add(bean7);pictures.add(bean8);
        pictures.add(bean9);pictures.add(bean10);pictures.add(bean11);pictures.add(bean12);
        pictures.add(bean13);pictures.add(bean14);pictures.add(bean15);pictures.add(bean0);

/*        Log.d("lixuan", "pictures;1=" + pictures.get(3));
        Log.d("lixuan", "pictures;1=" + pictures.get(2));
        Log.d("lixuan", "pictures;1=" + pictures.get(1));*/
    }

    @Override
    public void onClick(View v) {
        Log.d("lixuan", "click view " + v.getId());
        switch (v.getId()){
            case R.id.tv_2:
                //DisplaySelect();
                popupShow(v);
                break;
            case R.id.tv_3:
                //Log.d("lixuan","text view is onclicking---3");
                ChangeSelect(3);
                mPopupWindow.dismiss();
                break;
            case R.id.tv_4:
                //Log.d("lixuan","text view is onclicking---4");
                ChangeSelect(4);
                mPopupWindow.dismiss();
                break;
            case R.id.tv_22:
                //Log.d("lixuan","text view is onclicking---22");
                ChangeSelect(2);
                mPopupWindow.dismiss();
                break;
            default:
                break;
        }

    }

    private void ChangeSelect(int level) {
        switch (level){
            case 2:
               tv_2.setText(level2);
                break;
            case 3:
                tv_2.setText(level3);
                break;
            case 4:
                tv_2.setText(level4);
                break;
            default:
                break;
        }
    }

    private void DisplaySelect() {
        if(flagdisplaylevel) {
            tv_22.setVisibility(View.VISIBLE);
            tv_3.setVisibility(View.VISIBLE);
            tv_4.setVisibility(View.VISIBLE);
            flagdisplaylevel=false;
        }else{
            tv_22.setVisibility(View.GONE);
            tv_3.setVisibility(View.GONE);
            tv_4.setVisibility(View.GONE);
            flagdisplaylevel=true;
        }
    }

    class GriviewOnitemclick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mTypes=tv_2.getText().toString();
            mTypes=mTypes.trim();
            if (mTypes.equals("3 X 3")){
                mType=3;
                //Log.d("lixuan","选了3"+mType);
            }else if (mTypes.equals("4 X 4")){
                mType=4;
            }else {
                mType=2;
            }
           // Log.d("lixuan", "onItemclick" + position + "   " + mType);
            if (position==pictures.size()-1){
                showDialogCustom();
            }else {
                Intent intent=new Intent(MainActivity.this,PuzzleMain.class);
                intent.putExtra("picSelectID",pictures.get(position).getPic());
                intent.putExtra("myType", mType);
                intent.putExtra("mpicpath", "Select Item");
                intent.putExtra("from",select_desk);
                startActivity(intent);
            }
        }
    }

    private void showDialogCustom() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View myLoginView = layoutInflater.inflate(R.layout.customselect, null);
        Button bt_photo=(Button)myLoginView.findViewById(R.id.bt_photo);
        Button bt_selectcamera=(Button)myLoginView.findViewById(R.id.bt_selectcamera);

        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
       // builder.setView(R.layout.customselect);
        builder.setView(myLoginView);
/*        builder.create();
        builder.show();*/
        final AlertDialog aDialog=builder.create();
        aDialog.show();
        //aDialog.dismiss();
        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();
                //Log.d("lixuan", "bt_photo'");
                Intent intent=new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_TYPE);
                startActivityForResult(intent, RESULT_IMAGE);

            }
        });
        bt_selectcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();
               // Log.d("lixuan", "bt_selectcamera'");
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri=Uri.fromFile(new File(TEMP_IMAGE_PATH));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent,Result_CAMERA);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==Result_CAMERA){
                Intent intent=new Intent(MainActivity.this,PuzzleMain.class);
                intent.putExtra("mpicPath",TEMP_IMAGE_PATH);
                intent.putExtra("myType", mType);
                Log.d("lixuan", "相机拍摄:   "  + mType);
                intent.putExtra("from",select_camera);
                startActivity(intent);
            }
            if (requestCode==RESULT_IMAGE&&data!=null){
                Cursor cursor = this.getContentResolver().query(data.getData(),null,null,null,null);
                cursor.moveToFirst();
                String imagePath=cursor.getString(cursor.getColumnIndex("_data"));
                Log.d("lixuan", "从相册获取照片:  "+imagePath+"   "+mType);
                Intent intent=new Intent(this,PuzzleMain.class);
                intent.putExtra("mpicPath",imagePath);
                intent.putExtra("myType",mType);
                intent.putExtra("from",select_photo);
                startActivity(intent);
            }
        }
    }

    /**
     * 显示popup window
     *
     * @param view popup window
     */
    private void popupShow(View view) {
        int density = (int) ScreenUtil.getDevicesDesity(this);
        // 显示popup window
        mPopupWindow = new PopupWindow(mPopupView,
                250 * density, 50 * density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 透明背景
        Drawable transpent = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(transpent);
        // 获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(
                view,
                Gravity.NO_GRAVITY,
                location[0] - 40 * density,
                location[1] + 30 * density);
    }
}
