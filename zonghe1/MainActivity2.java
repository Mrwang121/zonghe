package com.example.zonghe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    //Fragment
    //计算器
    private Fragment Fragment_calculate = new CalculateFragment();
    private Fragment Fragment_chat =new ChatFragment();
    private Fragment Fragment_weather =new WeatherFragment();


    //底端菜单栏LinearLayout
    private LinearLayout linear_chat;
    private LinearLayout linear_contacts;
    private LinearLayout linear_ciecleoffriend;


    //底端菜单栏中的Imageview
    private ImageView imageView_chat;
    private ImageView imageView_contacts;
    private ImageView imageView_circleoffriend;

    //private ImageView imageView_computer;
    //FragmentManager
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_main2);

        initView(); //初始化各底端的LinearLayout、ImageView和TextView组件
        initFragment();  //初始化中间的部分的图层片段
        initEvent();  //初始化点击监听事件
        Fragment_select(0);
//    将第一个图标设为选中状态
        imageView_chat.setImageResource(R.drawable.calculate);
        imageView_contacts.setImageResource(R.drawable.chat1);
        imageView_circleoffriend.setImageResource(R.drawable.weather1);
        //imageView_chat.setImageResource(R.drawable.calculate1);
    }
    //监听函数，监听到底是哪一个图标被击中从而显示哪一个界面的内容
//  @Override
    public void onClick(View view) {
        //每次点击之后，将所有的ImageView和TextView设置为未选中
        restartButton();
        switch(view.getId())
        {
            case R.id.chat:
                //选择所点击的菜单对应的图层片段
                Fragment_select(0);
                //将该菜单的点击状态置为点击态
                imageView_chat.setImageResource(R.drawable.calculate);
                //getSupportFragmentManager().beginTransaction().add(R.id.frame_content,new CalculateFragment()).commit();
                //imageView_chat.setImageResource(R.drawable.calculate);
                imageView_contacts.setImageResource(R.drawable.chat1);
                imageView_circleoffriend.setImageResource(R.drawable.weather1);
                break;
            case R.id.contacts:
                Fragment_select(1);
                //imageView_contacts.setImageResource(R.drawable.chat1);
                imageView_contacts.setImageResource(R.drawable.chat);
                imageView_chat.setImageResource(R.drawable.calculate1);
                imageView_circleoffriend.setImageResource(R.drawable.weather1);
                break;
            case R.id.circle_friend:
                Fragment_select(2);
                //imageView_circleoffriend.setImageResource(R.drawable.weather1);
                imageView_circleoffriend.setImageResource(R.drawable.weather);
                imageView_contacts.setImageResource(R.drawable.chat1);
                imageView_chat.setImageResource(R.drawable.calculate1);
                break;

            default:
                break;
        }
    }

    //重置菜单的点击状态，设为未点击
    private void restartButton() {
        //设置为未点击状态
        imageView_chat.setImageResource(R.drawable.calculate1);
        imageView_chat.setImageResource(R.drawable.calculate);
        imageView_contacts.setImageResource(R.drawable.chat1);
        imageView_contacts.setImageResource(R.drawable.chat);

        imageView_circleoffriend.setImageResource(R.drawable.weather1);
        imageView_circleoffriend.setImageResource(R.drawable.weather);

    }

    //初始化中间的部分的图层片段
    private void initFragment(){
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content,Fragment_calculate);
        transaction.add(R.id.frame_content, Fragment_chat);
        transaction.add(R.id.frame_content, Fragment_weather);

        //transaction.add(R.id.frame_content,Fragment_computer);
        //提交事务
        transaction.commit();
    }

    //初始化各底端的LinearLayout、ImageView和TextView组件
    private  void initView(){
        //改变图标颜色
        linear_chat =findViewById(R.id.chat);
        linear_contacts =findViewById(R.id.contacts);
        linear_ciecleoffriend =findViewById(R.id.circle_friend);

        //linear_computer=findViewById(R.id.computer);
        imageView_chat =findViewById(R.id.imageView1);
        imageView_contacts =findViewById(R.id.imageView2);
        imageView_circleoffriend =findViewById(R.id.imageView3);

        //imageView_computer=findViewById(R.id.imageView7);
    }

    //初始化点击监听事件
    private void initEvent(){
        linear_chat.setOnClickListener(this::onClick);
        linear_contacts.setOnClickListener(this::onClick);
        linear_ciecleoffriend.setOnClickListener(this::onClick);


    }

    //把没有使用的界面的内容隐藏
    private void hideView(FragmentTransaction transaction){
        transaction.hide(Fragment_calculate);
        transaction.hide(Fragment_chat);
        transaction.hide(Fragment_weather);
        //transaction.hide(Fragment_computer);
    }

    //显示选中界面的内容，选中界面图标为绿色
    private void Fragment_select(int i){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //调用隐藏所有图层函数
        hideView(transaction);
        switch (i){
            case 0:
                transaction.show(Fragment_calculate);
                break;
            case 1:
                transaction.show(Fragment_chat);
                break;
            case 2:
                transaction.show(Fragment_weather);
                break;
            default:
                break;
        }
        //提交转换事务
        transaction.commit();
    }
}