package test.ylf.com.viewtest.test.ylf.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.ylf.com.viewtest.test.ylf.com.model.VisitSummaryData;
import test.ylf.com.viewtest.test.ylf.com.utils.SizeConvert;

/**
 * Created by Administrator on 2015/10/22.
 */
public class ChartTableView  extends View{
    private int width;//控件宽
    private int height;//控件高
    private int dataNum;//数据量

    private int tableItemWidth; //表格宽度
    private int tableItemHeight = SizeConvert.dip2px(getContext(),36);//表格单元高
    /**
     * 表格左上角的横纵坐标
     */
    private float startX;
    private float startY;


    private Paint mPaintText;//用于绘制文本
    private Paint mPaintWhiteBg;//用于绘制白色背景
    private Paint mPaintGreyBg;//用于绘制灰色背景
    private Paint mPaintLightGrey;//用于绘制浅灰色背景
    private Paint mPaintLine;//用于画表格的列线

    private int visitSum;//总访问量
    private int workerSum;//员工总数目
    private float visitAverage;//平均访问量
    private int textSize = SizeConvert.dip2px(getContext(),13);//文本尺寸，dp转px

    private ArrayList<VisitSummaryData> dataList;//总数据

    public ChartTableView(Context context) {
        super(context);
    }
    public ChartTableView(Context context,AttributeSet attr) {
        super(context,attr);
        mPaintLine=new Paint();
        mPaintLine.setColor(Color.LTGRAY);
        mPaintLine.setStrokeWidth(1);
        mPaintLine.setAntiAlias(true);

        mPaintText=new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);

       mPaintWhiteBg=new Paint();
        mPaintWhiteBg.setColor(Color.WHITE);
        mPaintWhiteBg.setStyle(Paint.Style.FILL);
        mPaintWhiteBg.setAntiAlias(true);

        mPaintGreyBg=new Paint();
        mPaintGreyBg.setColor(Color.argb(255, 240, 240, 240));
        mPaintGreyBg.setStyle(Paint.Style.FILL);
        mPaintGreyBg.setAntiAlias(true);

        mPaintLightGrey=new Paint();
        mPaintLightGrey.setColor(Color.argb(255, 250, 250, 250));
        mPaintLightGrey.setStyle(Paint.Style.FILL);
        mPaintLightGrey.setAntiAlias(true);

        dataList=new ArrayList<VisitSummaryData>();
    }
/*
  设置数据
 */
    public void setDataList(ArrayList<VisitSummaryData> dataList){
        this.dataList=dataList;
        dataNum=dataList.size();
        for(VisitSummaryData data :dataList){
             visitSum+=data.getVisitTotal();
            workerSum+=data.getWorkerTotal();
            visitAverage+=data.getVisitDaily();
        }
        visitAverage/=dataNum;
        visitAverage = (float)(Math.round(visitAverage*10))/10;//让平均访问量只保留一位小数
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        //根据数据数量来得到控件高
        if (dataNum != 0) {
            height = (dataNum+1)*tableItemHeight;
        }
        //表格单元宽
        tableItemWidth = width/4;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制统计表表第一列
        canvas.drawRect(startX, startY, startX + tableItemWidth, startY + tableItemHeight, mPaintLightGrey);  //绘制边框
        canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintGreyBg);     //绘制背景
        canvas.drawText("合计(" + dataNum + ")", startX + tableItemWidth / 2, startY + tableItemHeight / 2+textSize/2, mPaintText);
        canvas.drawText(visitSum+"",startX+tableItemWidth*3/2,startY+tableItemHeight/2+textSize/2,mPaintText);
        canvas.drawText(workerSum + "", startX + tableItemWidth * 5 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
        canvas.drawText(visitAverage+"",startX+tableItemWidth*7/2,startY+tableItemHeight/2+textSize/2,mPaintText);

        for(int i=0;i<dataNum;i++){
            startY+=tableItemHeight;
            canvas.drawRect(startX, startY, startX + tableItemWidth, startY + tableItemHeight, mPaintLightGrey);
            if(i%2==1) {
                canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintGreyBg);
            }else {
                canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintWhiteBg);
            }
            canvas.drawText(dataList.get(i).getName(), startX + tableItemWidth / 2, startY + tableItemHeight / 2+textSize/2, mPaintText);
            canvas.drawText(dataList.get(i).getVisitTotal()+"",startX+tableItemWidth*3/2,startY+tableItemHeight/2+textSize/2,mPaintText);
            canvas.drawText(dataList.get(i).getWorkerTotal()+"",startX+tableItemWidth*5/2,startY+tableItemHeight/2+textSize/2,mPaintText);
            canvas.drawText(dataList.get(i).getVisitDaily()+"",startX+tableItemWidth*7/2,startY+tableItemHeight/2+textSize/2,mPaintText);

            canvas.drawLine(0, tableItemHeight * (i + 1), width, tableItemHeight * (i + 1), mPaintLine);
        }
        startY=0;
        startX=0;
    }
}
