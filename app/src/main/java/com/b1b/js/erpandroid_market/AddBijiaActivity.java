package com.b1b.js.erpandroid_market;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.b1b.js.erpandroid_market.adapter.PopAdapter;
import com.b1b.js.erpandroid_market.entity.ProviderInfo;
import com.b1b.js.erpandroid_market.utils.MyToast;
import com.b1b.js.erpandroid_market.utils.WebserviceUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class AddBijiaActivity extends AppCompatActivity {

    private CheckBox cboNewProvider;
    private CheckBox cboHasFapiao;
    private LinearLayout newProviderInfo;
    private Button btnProvider;
    private List<ProviderInfo> providerInfos;
    private PopAdapter popAdapter;
    private ProviderInfo currentProvider;
    private Spinner fkSpinner;
    private Button btnOk;
    private Button btnCancel;
    private int did;
    private String pid;
    private String userName;
    private EditText edPrice;
    private Button btnDate;
    private EditText edMark;
    private CheckBox cboFaPiao;
    private EditText newProviderName;
    private EditText newProviderPhone;
    private EditText newProviderPeople;
    private final int INSERT_SUCCESS = 0;
    private final int INSERT_ERROR = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INSERT_SUCCESS:
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBijiaActivity.this);
                    builder.setMessage("插入成功");
                    break;
                case INSERT_ERROR:

                    break;

                case 3:
                    popAdapter.notifyDataSetChanged();
                    break;
                case 5:
                    MyToast.showToast(AddBijiaActivity.this, "搜索失败");
                    break;
            }
        }
    };
    private Calendar calendar;// 用来装日期的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bijia);
        cboNewProvider = (CheckBox) findViewById(R.id.add_bijia_new_provider);
        cboHasFapiao = (CheckBox) findViewById(R.id.add_bijia_fapiao);
        newProviderInfo = (LinearLayout) findViewById(R.id.add_bijia_new_providerinfo);
        btnProvider = (Button) findViewById(R.id.add_bijia_select_provider);
        fkSpinner = (Spinner) findViewById(R.id.add_bijia_fukuantype);
        btnOk = (Button) findViewById(R.id.add_bijia_ok);
        btnCancel = (Button) findViewById(R.id.add_bijia_cancel);
        btnDate = (Button) findViewById(R.id.add_bijia_jzdate);
        edPrice = (EditText) findViewById(R.id.add_bijia_money);
        edMark = (EditText) findViewById(R.id.add_bijia_mark);
        newProviderName = (EditText) findViewById(R.id.add_bijia_new_name);
        newProviderPeople = (EditText) findViewById(R.id.add_bijia_new_people);
        newProviderPhone = (EditText) findViewById(R.id.add_bijia_new_phone);
        calendar = Calendar.getInstance();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyTimePick(AddBijiaActivity.this);
            }
        });
        pid = getIntent().getStringExtra("pid");
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        did = sp.getInt("did", -1);
        userName = sp.getString("oprName", "");
        //初始化付款方式的spinner
        String[] fktypeArray = getResources().getStringArray(R.array.jiezhang_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBijiaActivity.this, R.layout.spinner_simple_item, R.id.spinner_item_tv, fktypeArray);
        fkSpinner.setAdapter(adapter);

        cboNewProvider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newProviderInfo.setVisibility(View.VISIBLE);
                    btnProvider.setEnabled(false);
                } else {
                    newProviderInfo.setVisibility(View.GONE);
                    btnProvider.setEnabled(true);
                }
            }
        });
        providerInfos = new ArrayList<>();
        popAdapter = new PopAdapter(providerInfos, AddBijiaActivity.this);
        btnProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddBijiaActivity.this);
                final AlertDialog providerDialog = builder.create();
                View view = LayoutInflater.from(AddBijiaActivity.this).inflate(R.layout.popwindow_view, null);
                providerDialog.setView(view);
                providerDialog.show();
                ListView listView = (ListView) view.findViewById(R.id.popwindow_lv);
                Button btnS = (Button) view.findViewById(R.id.popwindow_search);
                final EditText editText = (EditText) view.findViewById(R.id.popwindow_leibie);
                btnS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String keyword = editText.getText().toString();
                        if (did == -1) {
                            MyToast.showToast(AddBijiaActivity.this, "部门号不存在，请清理缓存");
                            return;
                        }
                        if (providerInfos.size() != 0) {
                            providerInfos.clear();
                            popAdapter.notifyDataSetChanged();
                        }
                        getMyProvider("", MyApp.id, did, keyword, mHandler);
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currentProvider = providerInfos.get(position);
                        if (currentProvider != null) {
                            MyToast.showToast(AddBijiaActivity.this, "选择供应商：" + currentProvider.getName());
                            btnProvider.setText(currentProvider.getName());
                            if (currentProvider.getHasKaipiao().equals("1")) {
                                cboHasFapiao.setEnabled(true);
                            } else {
                                cboHasFapiao.setEnabled(false);
                            }
                            providerDialog.dismiss();
                        }
                    }
                });
                listView.setAdapter(popAdapter);
                int did = getSharedPreferences("UserInfo", MODE_PRIVATE).getInt("did", -1);
                if (did == -1) {
                    MyToast.showToast(AddBijiaActivity.this, "部门号不存在，请清理缓存");
                }
                providerInfos.clear();
                getMyProvider("", MyApp.id, did, "", mHandler);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String price = edPrice.getText().toString();
                if (price.equals("")) {
                    MyToast.showToast(AddBijiaActivity.this, "请输入价格");
                    return;
                }
                final String date = btnDate.getText().toString();
                if (date.equals("点击选择日期")) {
                    MyToast.showToast(AddBijiaActivity.this, "请选择日期");
                    return;
                }
                final String providerId;
                final String providerState;
                final String providerPeople;
                final String providerPhone;
                final String providerName;
                if (cboNewProvider.isChecked()) {
                    providerId = "0";
                    providerState = "1";
                    providerPeople = newProviderPeople.getText().toString();
                    providerPhone = newProviderPhone.getText().toString();
                    providerName = newProviderName.getText().toString();

                } else {
                    if (currentProvider == null) {
                        MyToast.showToast(AddBijiaActivity.this, "请选择供应商");
                        return;
                    } else {
                        providerState = currentProvider.getHasKaipiao();
                        providerId = currentProvider.getId();
                    }
                    providerPeople = "";
                    providerPhone = "";
                    providerName = "";
                }

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            String payType = fkSpinner.getSelectedItem().toString();
                            String mark = edMark.getText().toString();
                            String fapiao = "0";
                            if (cboHasFapiao.isChecked()) {
                                fapiao = "1";
                            }
                            insertBijia("", did, pid, mark, price, providerId, providerName, providerPhone, providerPeople, payType, date, MyApp.id, userName, providerState, fapiao);
                            mHandler.sendEmptyMessage(INSERT_SUCCESS);
                        } catch (IOException e) {
                            mHandler.sendEmptyMessage(INSERT_ERROR);
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            mHandler.sendEmptyMessage(INSERT_ERROR);
                            e.printStackTrace();
                        }
                    }
                }.start();
                MyToast.showToast(AddBijiaActivity.this, "功能暂时不可用");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showMyTimePick(Context context) {
        final kankan.wheel.widget.WheelView wwYear, wwMonth, wwDay, wwHour, wwMinute, wwSecond;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.date_time_second_picker, null);
        dialogView.setLayoutParams(layoutParams);
        wwYear = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_year);
        wwMonth = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_month);
        wwDay = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_day);
        wwHour = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_hour);
        wwMinute = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_minute);
        wwSecond = (kankan.wheel.widget.WheelView) dialogView.findViewById(R.id.ww_second);
        Button btnOk = (Button) dialogView.findViewById(R.id.date_time_ok);
        Button btnCancel = (Button) dialogView.findViewById(R.id.date_time_cancle);

        final String[] years;
        final String[] months;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = 500;
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(attributes);
        //        dialog.show();
        final PopupWindow popupWindow = new PopupWindow(dialogView, 500, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.wheel_bg));
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        View mainView = findViewById(R.id.activity_add_bijia);
        popupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        years = new String[3];
        final int curentYear = date.getYear() + 1900;
        years[0] = String.valueOf(curentYear - 1);
        years[1] = String.valueOf(curentYear);
        years[2] = String.valueOf(curentYear + 1);
        final ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, years);
        wwYear.setViewAdapter(adapter);
        wwYear.setCurrentItem(1);
        wwYear.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(kankan.wheel.widget.WheelView wheel, int oldValue, int newValue) {
                wwMonth.setCurrentItem(0);
            }
        });
        months = produceArray(12);
        ArrayWheelAdapter<String> monAdapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, months);
        wwMonth.setViewAdapter(monAdapter);
        int currentMonth = date.getMonth();
        wwMonth.setCurrentItem(currentMonth);
        wwMonth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(kankan.wheel.widget.WheelView wheel, int oldValue, int newValue) {
                int currentItem = wwMonth.getCurrentItem();
                wwDay.setCurrentItem(0);
                ArrayWheelAdapter<String> adapter;
                String[] days;
                switch (currentItem + 1) {
                    case 2:
                        int selYear = Integer.parseInt(years[wwYear.getCurrentItem()]);
                        Log.e("zjy", "MainActivity->onChanged(): year==" + selYear);
                        boolean isRunYear = false;
                        if (selYear % 100 == 0) {
                            if (selYear % 400 == 0) {
                                isRunYear = true;
                            }
                        } else {
                            if (selYear % 4 == 0) {
                                isRunYear = true;
                            }
                        }
                        if (isRunYear) {
                            days = produceArray(29);
                        } else {
                            days = produceArray(28);
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        days = produceArray(30);
                        break;
                    default:
                        days = produceArray(31);
                        break;
                }
                adapter = new ArrayWheelAdapter<String>(AddBijiaActivity.this, days);
                wwDay.setViewAdapter(adapter);
            }
        });
        final String[] days = produceArray(31);
        ArrayWheelAdapter<String> dayAdapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, days);
        final String[] hours = produceArray(24);
        final String[] minute = produceArray(59);
        final String[] second = produceArray(59);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = years[wwYear.getCurrentItem()];
                String month = months[wwMonth.getCurrentItem()];
                String day = days[wwDay.getCurrentItem()];
                String hour = hours[wwHour.getCurrentItem()];
                String minute1 = minute[wwMinute.getCurrentItem()];
                String cSecond = second[wwSecond.getCurrentItem()];
                String text = year + "/" + month + "/" + day + "\t" + hour + ":" + minute1 + ":" + cSecond;
                btnDate.setText(text);
                popupWindow.dismiss();
            }
        });
        ArrayWheelAdapter<String> hourAdapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, hours);
        ArrayWheelAdapter<String> minuteAdapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, minute);
        ArrayWheelAdapter<String> secondAdapter = new ArrayWheelAdapter<>(AddBijiaActivity.this, second);
        wwDay.setViewAdapter(dayAdapter);
        wwDay.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

        Log.e("zjy", "AddBijiaActivity->showMyTimePick(): date_day==" + date.getDay());

        wwHour.setViewAdapter(hourAdapter);
        wwHour.setCurrentItem(date.getHours() - 1);
        wwMinute.setViewAdapter(minuteAdapter);
        wwMinute.setCurrentItem(date.getMinutes() - 1);
        wwSecond.setViewAdapter(secondAdapter);
        wwSecond.setCurrentItem(date.getSeconds() - 1);
    }

    private String[] produceArray(int days) {
        String[] tempDays;
        tempDays = new String[days];
        for (int i = 0; i < days; i++) {
            if (i < 9) {
                tempDays[i] = 0 + String.valueOf(i + 1);
            } else {
                tempDays[i] = String.valueOf(i + 1);
            }

        }
        return tempDays;
    }

    public void getMyProvider(String checkWord, String userID, int myDeptID, String providerName, final Handler mHandler) {
        //GetMyProvider
        // paramName="checkWord"  type="string"
        //paramName="userID" type="int"
        //paramName="myDeptID" type="int"
        //paramName="providerName"  type="string"
        if (userID == null || userID.equals("")) {
            MyToast.showToast(AddBijiaActivity.this, "请重新启动程序，登录id为空");
            return;
        }
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("checkWord", checkWord);
        map.put("userID", Integer.valueOf(userID));
        map.put("myDeptID", myDeptID);
        map.put("providerName", providerName);
        final SoapObject request = WebserviceUtils.getRequest(map, "GetMyProvider");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.MartService);
                    String res = response.toString();
                    if (res.length() > 7) {
                        res = res.substring(7);
                        String[] items = res.split(",");
                        for (int i = 0; i < items.length; i++) {
                            String[] s = items[i].split("-");
                            ProviderInfo info = new ProviderInfo(s[0], s[1], s[2]);
                            providerInfos.add(info);
                        }
                        mHandler.sendEmptyMessage(3);
                    } else {
                        mHandler.sendEmptyMessage(5);
                    }
                } catch (IOException e) {
                    mHandler.sendEmptyMessage(5);
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    mHandler.sendEmptyMessage(5);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //   InsertCompare"
    //    name="checkWord"  type=string"
    //    name="deptID" type=int"
    //    name="martStockID" type=int"
    //    name="note"  type=string"
    //    name="price" type=decimal"
    //    name="providerID" type=int"
    //    name="tempProviderName"  type=string"
    //    name="tempProviderPhone"  type=string"
    //    name="tempProviderLinkMen"  type=string"
    //    name="payType"  type=string"
    //    name="jzDate"  type=string"
    //    name="userID" type=int"
    //    name="userName"  type=string"
    //    name="providerEnableKP" type=int"
    //    name="faPiao" type=int"
    public void insertBijia(String checkWord, int deptID, String pid,
                            String note, String price, String providerID, String tempProviderName
            , String tempProviderPhone, String tempProviderLinkMen, String payType, String jzDate, String userID,
                            String userName, String providerEnableKP, String faPiao) throws IOException, XmlPullParserException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("checkWord", checkWord);
        map.put("deptID", deptID);
        map.put("martStockID", Integer.valueOf(pid));
        map.put("note", note);
        map.put("price", price);
        map.put("providerID", Integer.valueOf(providerID));
        map.put("tempProviderName", tempProviderName);
        map.put("tempProviderPhone", tempProviderPhone);
        map.put("tempProviderLinkMen", tempProviderLinkMen);
        map.put("payType", payType);
        map.put("jzDate", jzDate);
        map.put("userID", Integer.valueOf(userID));
        map.put("userName", userName);
        map.put("providerEnableKP", Integer.valueOf(providerEnableKP));
        map.put("faPiao", Integer.valueOf(faPiao));
        SoapObject request = WebserviceUtils.getRequest(map, "InsertCompare");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.MartService);
        Log.e("zjy", "AddBijiaActivity.java->insertBijia(): reponse==" + response.toString());
    }
}
