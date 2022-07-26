package com.example.moyu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moyu.databinding.ActivityScrollingBinding;
import com.example.moyu.util.BasicConstants;
import com.example.moyu.api.CaiHongPi;
import com.example.moyu.util.DateUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;

    private static StringBuilder pis = new StringBuilder();

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
                String txt = new CaiHongPi().getPi();
                Snackbar.make(view, txt, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //每次点击 记录结果
                this.initPis(pis.append(txt).append("\n").toString());
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
                showError(e.toString());
            }

        });


        try {
            this.init();
        } catch (ParseException e) {
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


    public void init() throws ParseException {
        this.initDateNow();
        this.initCountDown();
        this.initLineSplit();
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
    }

    //显示错误信息
    public void showError(String text) {
        @SuppressLint("ShowToast")
        Toast toast = Toast.makeText(ScrollingActivity.this, text, Toast.LENGTH_LONG);
        toast.show();

    }
}