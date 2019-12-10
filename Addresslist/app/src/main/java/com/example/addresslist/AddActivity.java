package com.example.addresslist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Button cancel, submit;
    EditText edit_name, edit_tel, edit_email, edit_com;
    String personId, personName, personTel, personEmail, personCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }

    void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        cancel = (Button) findViewById(R.id.cancel2);
        submit = (Button) findViewById(R.id.submit2);
        edit_name = (EditText) findViewById(R.id.edit_name2);
        edit_tel = (EditText) findViewById(R.id.edit_tel2);
        edit_email = (EditText) findViewById(R.id.edit_mail2);
        edit_com = (EditText) findViewById(R.id.edit_com2);
        cancel.setOnClickListener(listener);
        submit.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancel2:
                    Toast.makeText(AddActivity.this, "取消添加", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.submit2:
                    // 转成string类型,并且去掉前后空白
                    personName = edit_name.getText().toString().trim();
                    personTel = edit_tel.getText().toString().trim();
                    personEmail = edit_email.getText().toString().trim();
                    personCom = edit_com.getText().toString().trim();
                    if (personName.isEmpty()) {
                        Toast.makeText(AddActivity.this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (personTel.isEmpty()) {
                        Toast.makeText(AddActivity.this, "电话不能为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        SQLiteDatabase sqLiteDatabase = new DataBase(getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("id", personId);
                        values.put("name", personName);
                        System.out.println("Add "+personName);
                        values.put("tel", personTel);
                        values.put("email", personEmail);
                        values.put("company", personCom);
                        sqLiteDatabase.insert("person", null, values);
                        Toast.makeText(getApplicationContext(), "成功添加", Toast.LENGTH_SHORT).show();
                        sqLiteDatabase.close();
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
//                    finish();
                    break;

            }
        }
    };

}
