package com.example.addresslist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    Button button_back, button_submit;
    EditText edit_name, edit_tel, edit_email, edit_com;
    String personId, personName, personTel, personEmail, personCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();

    }


    private void init() {
        ActionBar actionBar = getSupportActionBar();
        // 隐藏标题栏
        if (actionBar != null)
            actionBar.hide();
        button_back = (Button) findViewById(R.id.button_back3);
        button_submit = (Button) findViewById(R.id.button_submit3);
        edit_name = (EditText) findViewById(R.id.edit_name3);
        edit_tel = (EditText) findViewById(R.id.edit_tel3);
        edit_email = (EditText) findViewById(R.id.edit_mail3);
        edit_com = (EditText) findViewById(R.id.edit_com3);
        button_back.setOnClickListener(listener);
        button_submit.setOnClickListener(listener);


        Bundle bundle = getIntent().getExtras();
        personId = bundle.getString("id");
        personName = bundle.getString("name");
        personTel = bundle.getString("tel");
        personEmail = bundle.getString("email");
        personCom = bundle.getString("company");
        edit_name.setText(personName);
        edit_tel.setText(personTel);
        edit_email.setText(personEmail);
        edit_com.setText(personCom);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_back3:   //切换界面
                    finish();
                    break;
                case R.id.button_submit3:   //提交按钮
                    SQLiteDatabase db = new DataBase(getApplicationContext()).getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("id", personId);
                    values.put("name", edit_name.getText().toString().trim());
                    values.put("tel", edit_tel.getText().toString().trim());
                    values.put("email", edit_email.getText().toString().trim());
                    values.put("company", edit_com.getText().toString().trim());
                    // 待增加
                    // 更新
                    db.update("person", values, "id=?", new String[]{personId});
                    db.close();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
//                    finish();
                    break;
            }
        }
    };
}
