package test.ylf.com.viewtest.test.ylf.com;

import android.app.Activity;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import test.ylf.com.viewtest.R;
import test.ylf.com.viewtest.test.ylf.com.model.VisitSummaryData;
import test.ylf.com.viewtest.test.ylf.com.view.ChartTableView;

/**
 * Created by Administrator on 2015/10/22.
 */
public class Main extends Activity {
    @ViewInject(R.id.charTable)
    private ChartTableView charTable;
    private  ArrayList<VisitSummaryData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        ViewUtils.inject(this);
        initData();

    }

    private void initData() {
        dataList = new ArrayList<>();
        dataList.add(new VisitSummaryData("涛涛",23,35,3.5f));
        dataList.add(new VisitSummaryData("萌萌",22,42,2.5f));
        dataList.add(new VisitSummaryData("秀秀",14,24,5.5f));
        dataList.add(new VisitSummaryData("光光",66,347,3.0f));
        dataList.add(new VisitSummaryData("瑞瑞",42,34,3.9f));
        dataList.add(new VisitSummaryData("美美",24,44,0.5f));
        dataList.add(new VisitSummaryData("笑笑",24,77,2.3f));
        dataList.add(new VisitSummaryData("狒狒",87,69,9.8f));
        dataList.add(new VisitSummaryData("飞飞",97,45,3.2f));
        dataList.add(new VisitSummaryData("肥肥",69,38,4.5f));
        dataList.add(new VisitSummaryData("小明",28,68,7.5f));
        dataList.add(new VisitSummaryData("小红",27,34,9.5f));
        //将数据传入自定义控件里
        charTable.setDataList(dataList);
    }
}
