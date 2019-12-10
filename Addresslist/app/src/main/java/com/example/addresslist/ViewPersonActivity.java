package com.example.addresslist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPersonActivity extends AppCompatActivity {

    Button button_back, button_edit, button_delete;
    TextView text_name, text_tel, text_email, text_com;
    String personId, personName, personTel, personEmail, personCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);
        init();
    }

    public void init() {
        ActionBar actionBar = getSupportActionBar();
        // 隐藏标题栏
        if (actionBar != null)
            actionBar.hide();
        button_back = (Button) findViewById(R.id.button_back1);
        button_edit = (Button) findViewById(R.id.button_edit1);
        button_delete = (Button) findViewById(R.id.button_delete1);
        text_name = (TextView) findViewById(R.id.text_name1);
        text_tel = (TextView) findViewById(R.id.text_tel1);
        text_email = (TextView) findViewById(R.id.text_email1);
        text_com = (TextView) findViewById(R.id.text_com1);
        button_back.setOnClickListener(listener);
        button_edit.setOnClickListener(listener);
        button_delete.setOnClickListener(listener);

        // 设置不可编辑
//        edit_name.setFocusableInTouchMode(false);
//        edit_name.setKeyListener(null);   //不可粘贴，长按不会弹出粘贴框
//        edit_name.setClickable(false);
//        edit_name.setFocusable(false);
//        edit_name.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        personId = bundle.getString("id");
        personName = bundle.getString("name");
        personTel = bundle.getString("tel");
        personEmail = bundle.getString("email");
        personCom = bundle.getString("company");
        text_name.setText(personName);
        text_tel.setText(personTel);
        text_email.setText(personEmail);
        text_com.setText(personCom);
    }


//    public static void actionStart(Context context, String id, String name, String tel, String email, String com) {
//        Intent intent = new Intent(context, ViewPersonActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putString("name", name);
//        bundle.putString("tel", tel);
//        bundle.putString("email", email);
//        bundle.putString("com", com);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_back1:   //image_back
                    finish();
                    break;
                case R.id.button_delete1:    //删除按钮
//                    Toast.makeText(getApplicationContext(), "点击", Toast.LENGTH_SHORT).show();
                    // 警告框
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewPersonActivity.this);
                    builder.setIcon(R.drawable.image_book);
                    builder.setTitle("警告");
                    builder.setMessage("确定删除" + personName + "吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SQLiteDatabase sqLiteDatabase = new DataBase(getApplicationContext()).getWritableDatabase();
                            // bug
                            long id = sqLiteDatabase.delete("person", "id=" + personId, null);
                            if (id > 0) {
                                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(ViewPersonActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                    break;
                case R.id.button_edit1:
                    // 传递数据
                    Bundle bundle = new Bundle();
                    bundle.putString("id", personId);
                    bundle.putString("name", personName);
                    bundle.putString("tel", personTel);
                    bundle.putString("email", personEmail);
                    bundle.putString("com", personCom);
                    Intent intent = new Intent(ViewPersonActivity.this, EditActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };
}
