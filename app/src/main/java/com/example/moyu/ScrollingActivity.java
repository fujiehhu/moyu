package com.example.moyu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moyu.api.CaiHongPi;
import com.example.moyu.api.JinRiShiCiApi;
import com.example.moyu.api.WeiBoApi;
import com.example.moyu.api.YiYan;
import com.example.moyu.databinding.ActivityScrollingBinding;
import com.example.moyu.util.BasicConstants;
import com.example.moyu.util.DateUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //页面上方内容
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        //邮件图标
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            try {
                StringBuilder pis = new StringBuilder();
                String txt = new CaiHongPi().getPi();
                showInfo(view, txt);
                //每次点击 记录结果
                this.initPis(pis.append(txt).append("\n").toString());
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
                showError(e.toString());
            }

        });


        try {
            this.init();
        } catch (ParseException | ExecutionException | InterruptedException | TimeoutException e) {
            showError(e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //右上角菜单
        if (id == R.id.action_settings) {
            showError("🙂正在向外太空发送消息......");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //    页面初始化
    public void init() throws ParseException, ExecutionException, InterruptedException, TimeoutException {
        this.initDateNow();
        this.initCountDown();
        this.initLineSplit();
        this.initWeiBo();
//        this.initShiCi();
    }

    private void initShiCi() {
        try {
            TextView tv = findViewById(R.id.shici);
            tv.setText(tv.getText().toString().replace("param", new JinRiShiCiApi().getShiCi()));
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            showError(e.toString());
        }
    }

    private void initLineSplit() {
        TextView tv = findViewById(R.id.line_split);
        tv.setGravity(Gravity.CENTER);
    }

    private void initPis(String pis) {
        TextView tv = findViewById(R.id.pis);
        tv.setGravity(Gravity.CENTER);
        tv.setText(pis);
    }


    public void initDateNow() throws ParseException {
        TextView tv = findViewById(R.id.dateNow);
        tv.setText(tv.getText().toString()
                .replace("param1", DateUtil.getDateNow())
                .replace("param2", "")
                .replace("param3", DateUtil.getDuringDay())
                .replace("param4", DateUtil.getWeekOfDate())
        );
    }

    //    倒计时
    public void initCountDown() throws ParseException {
        TextView tv = findViewById(R.id.tips3);
        tv.setText(tv.getText().toString()
                .replace("param1", DateUtil.countDown(BasicConstants.ZHOUMO.getName()).equals("-1")
                        ? "（算算你还有几个小时下班）" : DateUtil.countDown(BasicConstants.ZHOUMO.getName()) + " 天")
                .replace("param2", DateUtil.countDown(BasicConstants.ZHONGQIU.getName()))
                .replace("param3", DateUtil.countDown(BasicConstants.GUOQING.getName()))
                .replace("param4", DateUtil.countDown(BasicConstants.YUANDAN.getName()))
                .replace("param5", DateUtil.countDown(BasicConstants.CHUNJIE.getName()))
                .replace("param6", DateUtil.countDown(BasicConstants.QINGMING.getName()))
                .replace("param7", DateUtil.countDown(BasicConstants.LAODONGJIE.getName()))
        );

        tv.setOnClickListener(arg0 -> {
            /*
            //调到拨号界面
            Uri uri = Uri.parse("tel:18764563501");
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);*/
            try {
                showInfo(tv, new YiYan().getYiYan());
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
                showError(e.toString());
            }
        });

    }

    //显示错误信息
    public void showError(String text) {
        if (text == null) {
            return;
        }
        @SuppressLint("ShowToast")
        Toast toast = Toast.makeText(ScrollingActivity.this, text, Toast.LENGTH_LONG);
        toast.show();

    }


    public void showInfo(View view, String info) {
        Snackbar.make(view, info, Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }


    /**
     * 　　* @Description: 对UI进行初始化操作
     */
    private void initWeiBo() throws ExecutionException, InterruptedException, TimeoutException {
        ViewFlipper viewfli = super.findViewById(R.id.weibo);

        // 为ViewFlipper设置内容
        List<TextView> list = this.getData();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            viewfli.addView(list.get(i));
        }

        // 设置文字in/out的动画效果
        viewfli.setInAnimation(this, R.anim.push_up_in);
        viewfli.setOutAnimation(this, R.anim.push_up_out);
        viewfli.startFlipping();
    }

    /**
     * @return list
     * @Description: 要显示的文字信息
     * 在实际开发中，此方法可为对服务器返回数据的解析操作
     */
    private List<TextView> getData() throws ExecutionException, InterruptedException, TimeoutException {
        ArrayList<String> weiBo = new WeiBoApi().getWeiBo();
        List<TextView> list = new ArrayList<>();
        for (String s : weiBo) {
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setGravity(Gravity.CENTER);
            list.add(tv);
        }

        return list;
    }
}