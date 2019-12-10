package com.example.addresslist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.addresslist.azlist.AZSideBarView;
import com.example.addresslist.azlist.AZTitleDecoration;
import com.example.addresslist.azlist.AZWaveSideBarView;
import com.example.addresslist.azlist.LettersComparator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SearchView mSearchView;
    private TextView text_1;
    private FloatingActionButton button_add;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DataBase db;
    private List<Person> personList, filterQuery;
    private ItemAdapter mItemAdapter;
    private AZSideBarView mBarList;
    private Person selectPerson;
    private boolean isquery;    //当前是否在查询状态

    private void initEvent() {
        mBarList.setOnLetterChangeListener(new AZSideBarView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mItemAdapter.getSortLettersFirstPosition(letter);
                if (position != -1) {
                    if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                        manager.scrollToPositionWithOffset(position, 0);
                    } else {
                        mRecyclerView.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });
    }

//    private void initData(){
//        Collections.sort(personList,new LettersComparator());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initEvent();
    }

    //初始化
    private void init() {
        // 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        mContext = this;
        mSearchView = (SearchView) findViewById(R.id.search_view);
        text_1 = (TextView) findViewById(R.id.text_1);
        button_add = (FloatingActionButton) findViewById(R.id.button_add);
        mRecyclerView = findViewById(R.id.recycler_view);

        mBarList = findViewById(R.id.bar);

        button_add.setOnClickListener(listener);
        // 设置submit显示在searchview里面
        mSearchView.setSubmitButtonEnabled(true);
        //设置搜索图标是否显示在搜索框内
        mSearchView.setIconifiedByDefault(true);

        isquery = false;
        //创建数据库
        db = new DataBase(this);
        // 从数据库获取信息
        ViewPerson();
        if (personList.size() == 0) {   // 若列表为空，则显示无联系人
            mRecyclerView.setVisibility(View.INVISIBLE);
            text_1.setVisibility(View.VISIBLE);
            mBarList.setVisibility(View.INVISIBLE);
        } else {
            mBarList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            text_1.setVisibility(View.INVISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            // 添加索引 // bug
            mRecyclerView.addItemDecoration(new AZTitleDecoration(new AZTitleDecoration.TitleAttributes(mContext)));
            // 添加拼音
            personList = fillData(personList);
            // 排序
            Collections.sort(personList, new LettersComparator());
            mItemAdapter = new ItemAdapter(personList);
            mItemAdapter.setOnListener(new ItemAdapter.OnItemListener() {
                // 点击事件
                @Override
                public void onItemClick(View view, int position) {
                    Person person = personList.get(position);
                    Log.d(TAG, "name " + person.getName());
                    Intent intent = new Intent(MainActivity.this, ViewPersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", person.getId());
                    Log.d(TAG, "id " + person.getId());
                    bundle.putString("name", person.getName());
                    bundle.putString("tel", person.getTel());
                    bundle.putString("email", person.getEmail());
                    bundle.putString("com", person.getCompany());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                // 长按事件
                @Override
                public void onItemLongClick(View view, int position) {
                    Log.d(TAG, "onItemLongClick: position: "+position);
                    if(!isquery)selectPerson = personList.get(position);
                    else selectPerson = filterQuery.get(position);
                }
            });
            mRecyclerView.setAdapter(mItemAdapter);
        }

        // 搜索框初始化
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 点击搜索按钮时
            @Override
            public boolean onQueryTextSubmit(String query) {    //提交时无任何操作
                return false;
            }

            // 搜索内容改变时
            @Override
            public boolean onQueryTextChange(String query) {
                Log.d(TAG, "onQueryTextChange: " + query);
                if (personList.size() > 0) {
                    if (query.isEmpty()) {
                        isquery = false;
                        text_1.setText("无联系人");
                        text_1.setVisibility(View.INVISIBLE);
                        mBarList.setVisibility(View.VISIBLE);
                        Collections.sort(personList, new LettersComparator());
                        mItemAdapter.setPersonList(personList);
                        filterQuery.clear();
                    } else {
                        isquery = true;
                        filterQuery = filter(personList, query);
                        if (filterQuery.size() == 0) {
                            text_1.setText("未找到");
                            text_1.setVisibility(View.VISIBLE);
                            mBarList.setVisibility(View.INVISIBLE);
                        } else {
                            text_1.setText("无联系人");
                            text_1.setVisibility(View.INVISIBLE);
                            mBarList.setVisibility(View.VISIBLE);
                            Collections.sort(filterQuery, new LettersComparator());
                            mItemAdapter.setPersonList(filterQuery);
                        }
                    }
                }
                return false;
            }
        });

        //注册上下文菜单
        registerForContextMenu(mRecyclerView);
    }

    // 获取数据库中的数据
    public void ViewPerson() {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("person", null, null, null, null, null, null);
        personList = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Person person = new Person();
                // 待优化
                person.setId(cursor.getString(0).toString());
                person.setName(cursor.getString(1).toString());
                person.setTel(cursor.getString(2).toString());
                person.setEmail(cursor.getString(3).toString());
                person.setCompany(cursor.getString(4).toString());
                personList.add(person);
                person = null;
            }
        }
        sqLiteDatabase.close();
        cursor.close();
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_add:   //切换界面
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
//                    RightHintActivity.startUp(MainActivity.this);
                    break;
            }
        }
    };


    // 筛选
    private List<Person> filter(List<Person> list, String query) {
        filterQuery = new ArrayList<>();
        for (Person person : list) {
            if (person.getName().contains(query))
                filterQuery.add(person);
        }
        return filterQuery;
    }

    private List<Person> fillData(List<Person> list) {
        List<Person> sortList = new ArrayList<>();
        for (Person mperson : list) {
            String pinyin = PinyinUtils.getPinyin(mperson.getName());
            String letters = pinyin.substring(0, 1).toUpperCase();
            if (letters.matches("[A-Z]")) {
                mperson.setSortLetters(letters.toUpperCase());
            } else {
                mperson.setSortLetters("#");
            }
            sortList.add(mperson);
        }
        return sortList;
    }

    // 上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (menuInfo == null) {
            Log.d(TAG, "onCreateContextMenu: menuInfo null");
        }
//        int position = (int) v.getTag();
//        selectPerson = personList.get(position);
        menu.setHeaderTitle("请选择对" + selectPerson.getName() + "的操作");
        menu.add(1, 1, 1, "拨打电话");
        menu.add(1, 2, 1, "发送短信");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 获取位置
        if (item.getMenuInfo() == null) {
            Log.d(TAG, "onContextItemSelected: menuInfo null");
        }

        String tel = selectPerson.getTel();
        switch (item.getItemId()) {
            // 打开拨号界面
            case 1:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tel));
                    Log.d(TAG, "onContextItemSelected: dial: "+tel);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                break;
            // 发送短信
            case 2:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+tel));
                Log.d(TAG, "onContextItemSelected: sendto: "+tel);
                startActivity(intent);
                break;
            default:
                //nothing
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(mRecyclerView);
    }

}
