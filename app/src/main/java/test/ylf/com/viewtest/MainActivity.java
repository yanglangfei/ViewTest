package test.ylf.com.viewtest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    @ViewInject(R.id.et_input)
    private EditText input;
    @ViewInject(R.id.iv)
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initView();
    }
    @OnClick(R.id.btn_search)
    public  void onSearch(View view){
        input.setText("you are searching...");
        LogUtils.customTagPrefix=MainActivity.class.getSimpleName();
        LogUtils.allowI=true;
        LogUtils.i("you want to search:" + input.getText().toString());
    }

    private void initView() {
        try {
        InputStream is=getAssets().open("login.png");
        //获取图片的宽高
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(is,null,options);
        int width=options.outWidth;
        int heigh=options.outHeight;
        //设置显示区域
            BitmapRegionDecoder decoder=BitmapRegionDecoder.newInstance(is,false);
            BitmapFactory.Options options1=new BitmapFactory.Options();
            options1.inPreferredConfig=Bitmap.Config.RGB_565;
            Bitmap bm=decoder.decodeRegion(new Rect(width/2-100,heigh/2-100,width/2+100,heigh/2+100),options1);
            iv.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public InputStream  createInputStream(Drawable drawable){
        Bitmap bm=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565);
        Canvas canvas=new Canvas(bm);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,bos);
        InputStream is=new ByteArrayInputStream(bos.toByteArray());
        return is;
    }


}
