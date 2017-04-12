package com.b1b.js.erpandroid_market;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.b1b.js.erpandroid_market.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    private ListView menuList;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, String>> listItems = new ArrayList<>();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        menuList = (ListView) findViewById(R.id.lv);
        simpleAdapter = new SimpleAdapter(this, listItems, R.layout.menu_items, new String[]{"title"}, new int[]{R.id.menu_title});
        // 为菜单项设置点击事件
        setItemOnclickListener();
        addItem();
        // 设置adapter
        menuList.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setItems(new String[]{"拍照", "从手机选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
//                    case 0:
//                        Intent intent1 = new Intent(MenuActivity.this, TakePicActivity.class);
//                        startActivity(intent1);
//                        break;
//                    case 1:
//                        Intent intent2 = new Intent(MenuActivity.this, ObtainPicFromPhone.class);
//                        startActivity(intent2);
//                        break;
                }
            }
        });
        dialog = builder.create();
    }

    private void setItemOnclickListener() {
        menuList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> item = listItems.get(position);
                String value = item.get("title");
                Intent intent = new Intent();
                switch (value) {
                    case "出库单":
//                        intent.setClass(MenuActivity.this, ChuKuActivity.class);
                        startActivity(intent);
                        break;
                    case "出库审核(拍照)":
//                        intent.setClass(MenuActivity.this, CheckActivity.class);
                        startActivity(intent);
                        break;
                    case "采购审核":
                        intent.setClass(MenuActivity.this, CaigouActivity.class);
                        startActivity(intent);
                        break;
                    case "考勤":
//                        intent.setClass(MenuActivity.this, KaoQinActivity.class);
                        startActivity(intent);
                        break;
                    case "上传图片(必须有单据号)":
                        if (!dialog.isShowing() && dialog != null) {
                            dialog.show();
                        }
                        break;
                    case "比价单":
                        intent.setClass(MenuActivity.this, BijiaActivity.class);
                        startActivity(intent);
                        break;
                    case "查看单据关联图片":
//                        intent.setClass(MenuActivity.this, ViewPicByPidActivity.class);
                        startActivity(intent);
                        break;
                    case "上传服务":
//                        intent.setClass(MenuActivity.this, UploadActivity.class);
//                        startActivity(intent);
//                        intent.setClass(MenuActivity.this, TakePic2Activity.class);
                        startActivity(intent);
                        break;
                    case "拿货单":
                        intent.setClass(MenuActivity.this, NahuoActivity.class);
                        startActivity(intent);
                        break;
                    case "取消自动登录":
                        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
                        boolean atuoLogin = sp.getBoolean("autol", false);
                        if (!atuoLogin) {
                            MyToast.showToast(MenuActivity.this, "当前已是非自动登录状态");
                        } else {
                            SharedPreferences.Editor editor = sp.edit();
                            if (editor.putBoolean("autol", false).commit()) {
                                MyToast.showToast(MenuActivity.this, "取消登录成功");
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 添加菜单项
    private void addItem() {
        Map<String, String> map = new HashMap<>();
//        map.put("title", "出库单");
//        listItems.add(map);
//        map = new HashMap<>();
//        map.put("title", "出库审核(拍照)");
//        listItems.add(map);
//        map = new HashMap<>();
        map.put("title", "采购审核");
        listItems.add(map);
        //        map = new HashMap<>();
        //        map.put("title", "入库");
        //        listItems.add(map);
//        map = new HashMap<>();
//        map.put("title", "考勤");
//        listItems.add(map);
//        map = new HashMap<>();
//        map.put("title", "上传图片(必须有单据号)");
//        listItems.add(map);
        map = new HashMap<>();
        map.put("title", "比价单");
        listItems.add(map);
//        map = new HashMap<>();
//        map.put("title", "取消自动登录");
//        listItems.add(map);
//        map = new HashMap<>();
//        map.put("title", "上传服务");
//        listItems.add(map);
    }
}
