package com.example.zonghe;
//登录界面
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etname,etpassword;
    Button btnlogin,btnregedit;
    CheckBox cb;
    DBHelper helper;
    SharedPreferences spSettings = null;
    static final String PREFS_NAME = "NamePwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname = (EditText) findViewById(R.id.etname);
        etpassword = (EditText) findViewById(R.id.etpwd);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnregedit = (Button) findViewById(R.id.btnregedit);
        cb = (CheckBox) findViewById(R.id.cb);
        setListener();
        getData();
    }
    private void setListener(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strname = etname.getText().toString().trim();
                String strpassword = etpassword.getText().toString().trim();
                helper = new DBHelper(MainActivity.this);
                User user = helper.query(strname);
                if(user!=null){
                    if(user.getStrPassword().equals(strpassword)){
                        if(cb.isChecked()){
                            spSettings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor edit = spSettings.edit();
                            edit.putBoolean("isKeep",true);
                            edit.putString("username",strname);
                            edit.putString("userpassword",strpassword);
                            edit.commit();
                        }
                        else{
                            spSettings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor edit = spSettings.edit();
                            edit.putBoolean("isKeep",false);
                            edit.putString("username","");
                            edit.putString("userpassword","");
                            edit.commit();
                        }
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
//                        intent.putExtra("name", etname.getText().toString());
//                        intent.putExtra("password",etpassword.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"密码不正确，请重新输入！",Toast.LENGTH_LONG).show();
                        etpassword.setText("");
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"用户名不正确，请重新输入！",Toast.LENGTH_LONG).show();
                    etname.setText("");
                }
            }
        });
        btnregedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strname = etname.getText().toString().trim();
                String strpassword = etpassword.getText().toString().trim();
                User usename = helper.query(strname);
                if(usename!=null){
                    Toast.makeText(MainActivity.this,"用户名已被注册！",Toast.LENGTH_SHORT).show();
                }
                else{
                    User user = new User(strname,strpassword);
                    helper = new DBHelper(MainActivity.this);
                    helper.insert(user);
                    Toast.makeText(MainActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void onResume(){
        super.onResume();
        getData();
    }
    private void getData(){
        spSettings =getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(spSettings.getBoolean("isKeep",false)){
            etname.setText(spSettings.getString("username",""));
            etpassword.setText(spSettings.getString("userpassword",""));
        }
        else{
            etname.setText("");
            etpassword.setText("");
        }
    }

}