package test.ylf.com.viewtest.test.ylf.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/22.
 */
public class NineGridView extends View{
    private int height;//该控件的高
    private  int width;  //该空间的宽度
    private Paint mPaintBigCircle;//用于画外圆
    private Paint mPaintSmallCircle;//用于画内圆
    private Paint mPaintLine;//用于画线
    private Paint mPaintText;//用于画文本
    private Path path;//手势划线时需要用到它
    private Map<Integer, Float[]> pointContainer;//存储九个点的坐标
    private List<Integer> pointerSlipped;//存储得到的九宫格密码


    private float pivotX;//触屏得到的x坐标
    private float pivotY;//触屏得到的y坐标
    private float selectedX;//当前选中的圆点的x坐标
    private float selectedY;//当前选中的圆点的y坐标
    private float selectedXOld;//从前选中的圆点的x坐标
    private float selectedYOld;//从前选中的圆点的y坐标
    private boolean isHasMoved = false;//用于判断path是否调用过moveTo()方法

    private String text = "请绘制解锁图案";
    private float x;//绘制的圆形的x坐标
    private float y;//绘制圆形的纵坐标
    private float added;//水平竖直方向每个圆点中心间距
    private float patternMargin = 100;//九宫格距离边界距离
    private float bigCircleRadius = 90;//外圆半径
    private float smallCircleRadius = 25;//内圆半径
    private int index;//圆点的序号

    public  List<Integer> getPointerSlipped(){
        return  pointerSlipped;


    }
    public void setPointerSlipped(List<Integer> pointerSlipped) {
        this.pointerSlipped = pointerSlipped;
    }

    public NineGridView(Context context) {
        super(context);
    }

    public NineGridView(Context context,AttributeSet attr) {
        super(context,attr);
        mPaintBigCircle = new Paint();
        mPaintBigCircle.setColor(Color.BLUE);
        mPaintBigCircle.setStyle(Paint.Style.STROKE);//不充满
        mPaintBigCircle.setAntiAlias(true);//抗锯齿打开

        mPaintSmallCircle = new Paint();
        mPaintSmallCircle.setColor(Color.GREEN);
        mPaintSmallCircle.setStyle(Paint.Style.FILL);//充满，即画的几何体为实心
        mPaintSmallCircle.setAntiAlias(true);

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.GREEN);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(20);
        mPaintLine.setAntiAlias(true);

        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextAlign(Paint.Align.CENTER);//向中央对齐
        mPaintText.setTextSize(50);
        mPaintText.setAntiAlias(true);

        path = new Path();
        pointContainer = new HashMap<>();
        pointerSlipped = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                pivotX=event.getX();
                pivotY=event.getY();
                //每次触屏时需要清空一下pointerSlipped，即重置密码
                pointerSlipped.clear();
                Log.d("pointTouched", pivotX + "," + pivotY);
                getSelectedPointIndex(pivotX, pivotY);
                invalidate();//重绘
                break;
            case MotionEvent.ACTION_MOVE:
                pivotY=event.getY();
                pivotX=event.getX();
                getSelectedPointIndex(pivotX,pivotY);
                invalidate();
                break;
            case  MotionEvent.ACTION_UP:
                //手指离开时重绘
                path.reset();
                isHasMoved=false;
                String indexSequence = "";
                //打印出上一次手势密码的值
                for(int index:pointerSlipped){
                    indexSequence += "/"+index;
                }
                Log.d("index", indexSequence);
                break;
        }
        invalidate();
        return true;
    }

    /**
     * @param pivotX
     * @param pivotY
     *    得到并存储经过的圆点的序号
     */
    private void getSelectedPointIndex(float pivotX, float pivotY) {

        int index = 0;
        if (pivotX > patternMargin && pivotX < patternMargin + bigCircleRadius * 2) {
            if (pivotY > height / 2 && pivotY < height / 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(1)[0];
                selectedY = pointContainer.get(1)[1];
                index = 1;
                Log.d("selectedPoint", selectedX + "," + selectedY);
            } else if (pivotY > height / 2 + added && pivotY < height / 2 + added + bigCircleRadius * 2) {
                selectedX = pointContainer.get(4)[0];
                selectedY = pointContainer.get(4)[1];
                index = 4;
            } else if (pivotY > height / 2 + added * 2 && pivotY < height / 2 + added * 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(7)[0];
                selectedY = pointContainer.get(7)[1];
                index = 7;
            }
        } else if (pivotX > patternMargin + added && pivotX < patternMargin + added + bigCircleRadius * 2) {
            if (pivotY > height / 2 && pivotY < height / 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(2)[0];
                selectedY = pointContainer.get(2)[1];
                index = 2;
            } else if (pivotY > height / 2 + added && pivotY < height / 2 + added + bigCircleRadius * 2) {
                selectedX = pointContainer.get(5)[0];
                selectedY = pointContainer.get(5)[1];
                index = 5;
            } else if (pivotY > height / 2 + added * 2 && pivotY <height / 2 + added * 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(8)[0];
                selectedY = pointContainer.get(8)[1];
                index = 8;
            }
        } else if (pivotX > patternMargin + added * 2 && pivotX < patternMargin + added * 2 + bigCircleRadius * 2) {
            if (pivotY > height / 2 && pivotY < height / 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(3)[0];
                selectedY = pointContainer.get(3)[1];
                index = 3;
            } else if (pivotY > height / 2 + added && pivotY < height / 2 + added + bigCircleRadius * 2) {
                selectedX = pointContainer.get(6)[0];
                selectedY = pointContainer.get(6)[1];
                index = 6;
            } else if (pivotY > height / 2 + added * 2 && pivotY < height / 2 + added * 2 + bigCircleRadius * 2) {
                selectedX = pointContainer.get(9)[0];
                selectedY = pointContainer.get(9)[1];
                index = 9;
            }
        }

        if (selectedX!=selectedXOld||selectedY!=selectedYOld){
            //当这次的坐标与上次的坐标不同时存储这次点序号
            pointerSlipped.add(index);
            selectedXOld = selectedX;
            selectedYOld = selectedY;
            if (!isHasMoved){
                //当第一次触碰到九个点之一时，path调用moveTo;
                path.moveTo(selectedX,selectedY);
                isHasMoved = true;
            }else{
                //path移动至当前圆点坐标
                path.lineTo(selectedX,selectedY);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        added = (width - patternMargin * 2) / 3;
        x = patternMargin + added / 2;
        y = added / 2 + height / 2;
        index = 1;
        canvas.drawColor(Color.BLACK);
        canvas.drawText(text, width / 2, height / 4, mPaintText);
        /**
         * 绘制九个圆点图案
         */
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                canvas.drawCircle(x, y, bigCircleRadius, mPaintBigCircle);
                canvas.drawCircle(x, y, smallCircleRadius, mPaintSmallCircle);
                pointContainer.put(index, new Float[]{x, y});
                index++;

                x += added;
            }
            y += added;
            x = patternMargin + added / 2;
        }
        x = patternMargin + added / 2;
        y = added / 2 + height / 2;

        canvas.drawPath(path, mPaintLine);
    }
}
