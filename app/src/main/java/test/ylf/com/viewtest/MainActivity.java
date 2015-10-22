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
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
    @ViewInject(R.id.et_input)
    private EditText input;
    @ViewInject(R.id.iv)
    private ImageView iv;
    @ViewInject(R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        Spanned charSequence=Html.
        fromHtml("<img id=\"qqmail_send_skyscraper_showed\" src=\"https://stockweb.mail.qq.com/201508/06/bcymq_qss_201508060905.jpg?pdomain=wa.gtimg.com\" width=\"550\" height=\"100\" onload=\"getTop().AD.handleImgSucc('qqmail_send_skyscraper', window)\" onerror=\"getTop().AD.handleImgError('qqmail_send_skyscraper', window)\">", new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int identifier = getResources().getIdentifier(source, "drawable", getPackageName());
                Log.e("identifier", "identifier=" + identifier);
                Drawable drawable = getResources().getDrawable(identifier);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return null;
            }
        }, null);
        SpannableString spanStr = new SpannableString(charSequence);

        URLSpan[] urlspans = spanStr.getSpans(0, spanStr.length(), URLSpan.class);
        for (URLSpan urlspan : urlspans)
        {
            Log.d("url", "url="+urlspan.getURL());
            spanStr.setSpan(new URLSpan(urlspan.getURL()){
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置删除线
                    ds.setFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                    //设置下划线
                    ds.setUnderlineText(true);
                    //设置颜色
                    ds.setColor(0xfff90202);
                }
                @Override
                public void onClick(View widget) {
                    Log.e("widget", "url="+getURL());
                    try {
                        URI uri = new URI(getURL());
                        if("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                        {
                            //网络请求
                        }else if("tel".equalsIgnoreCase(uri.getScheme())){
                            //电话
                        }
                        else if("mailto".equalsIgnoreCase(uri.getScheme()))
                        {
                            //邮箱
                        }else if("page".equalsIgnoreCase(uri.getScheme())){
                            //Activity 跳转
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }, spanStr.getSpanStart(urlspan), spanStr.getSpanEnd(urlspan), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanStr);

        // initView();
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
