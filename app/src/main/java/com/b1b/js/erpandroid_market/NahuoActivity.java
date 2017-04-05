package com.b1b.js.erpandroid_market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.b1b.js.erpandroid_market.adapter.NahuoAdapter;
import com.b1b.js.erpandroid_market.entity.NahuoDan;
import com.b1b.js.erpandroid_market.entity.NahuoInfo;
import com.b1b.js.erpandroid_market.utils.MyToast;
import com.b1b.js.erpandroid_market.utils.WebserviceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NahuoActivity extends AppCompatActivity {

    private ListView lv;
    private NahuoAdapter nahuoAdapter;
    private List<NahuoDan> data;
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyToast.showToast(NahuoActivity.this, "获取到" + data.size() + "条数据");
                    nahuoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nahuo);
        lv = (ListView) findViewById(R.id.activity_nahuo_lv);
        Button btnSearch = (Button) findViewById(R.id.activity_nahuo_btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                nahuoAdapter.notifyDataSetChanged();
                beginSearch();
            }
        });
        data = new ArrayList<>();
        nahuoAdapter = new NahuoAdapter(data, NahuoActivity.this, R.layout.item_simple_tv);
        lv.setAdapter(nahuoAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NahuoActivity.this, NahuoEditActivity.class);
                intent.putExtra("pid", data.get(position).getPid());
                startActivity(intent);
            }
        });
    }

    private void beginSearch() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    getNahuoMainInfo("810934");
                    getNahuoDetailTableInfo("810934");
//                    getFinishNahuoInfo("810934", "", "");
                    getNahuoList(MyApp.id, "");
                    mHander.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getNahuoMainInfo(String pid) throws IOException, XmlPullParserException, JSONException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", pid);
        SoapObject request = WebserviceUtils.getRequest(map, "GetDataInfoByNaHuoMainID");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.ChuKuServer);
        JSONObject object = new JSONObject(response.toString());
        JSONArray table = object.getJSONArray("表");
        for (int i = 0; i < table.length(); i++) {
            JSONObject tJobj = table.getJSONObject(i);
            String createDate = tJobj.getString("CreateDate");
            String corpId = tJobj.getString("CorpID");
            String state = tJobj.getString("State");
            String depteId = tJobj.getString("DeptID");
            String clientInvoice = tJobj.getString("ClientInvoice");
            String employeeID = tJobj.getString("EmployeeID");
            String note = tJobj.getString("Note");
            String invoiceType = tJobj.getString("invoiceType");
            String providerID = tJobj.getString("ProviderID");
            String providerName = tJobj.getString("ProviderName");
            String childCompany = tJobj.getString("分公司");
            String deptName = tJobj.getString("部门");
            String saleMan = tJobj.getString("业务员");
            String kaipiaoType = tJobj.getString("客户开票");
            String kaipiaoCompany = tJobj.getString("开票公司");
            String kaipiaoFlag = tJobj.getString("是否开票");
            NahuoInfo info = new NahuoInfo(createDate, corpId, state, depteId, clientInvoice, employeeID, note, invoiceType, providerID, providerName, childCompany, deptName, saleMan, kaipiaoType, kaipiaoCompany, kaipiaoFlag);
        }
        Log.e("zjy", "NahuoActivity->getNahuoMainInfo(): response==" + response.toString());
    }

    /**
     得到拿货完成列表
     @param uid
     @param partNo
     @throws IOException
     @throws XmlPullParserException
     @throws JSONException           */
    public void getNahuoList(String uid, String partNo) throws IOException, XmlPullParserException, JSONException {
        //        <param name = "uid" > 用户ID </param >
        //        <param name="part"></param>
        //
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("uid", uid);
        map.put("part", partNo);
        SoapObject request = WebserviceUtils.getRequest(map, "GetDataListForNaHuo");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.ChuKuServer);
        JSONObject root = new JSONObject(response.toString());
        JSONArray table = root.getJSONArray("表");
        for (int i = 0; i < table.length(); i++) {
            JSONObject tempJ = table.getJSONObject(i);
            String pid = tempJ.getString("PID");
            String createDate = tempJ.getString("CreateDate");
            String fhDate = tempJ.getString("FahuoDate");
            String state = tempJ.getString("State");
            String buyerID = tempJ.getString("BuyerID");
            String clientID = tempJ.getString("ClientID");
            String employeeID = tempJ.getString("EmployeeID");
            String branchCompanyName = tempJ.getString("分公司");
            String deptName = tempJ.getString("部门");
            String saleMan = tempJ.getString("业务员");
            String buyerName = tempJ.getString("采购员");
            String corpID = tempJ.getString("CorpID");
            String deptID = tempJ.getString("DeptID");
            NahuoDan nahuodan = new NahuoDan(pid, createDate, fhDate, state, buyerID, clientID, employeeID, branchCompanyName, deptName, saleMan, buyerName, corpID, deptID);
            data.add(nahuodan);
        }
        Log.e("zjy", "NahuoActivity->getNahuoList(): response==" + response.toString());
    }

    public void getFinishNahuoInfo(String pid, String main, String detail) throws IOException, XmlPullParserException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", pid);
        map.put("main", main);
        map.put("detail", detail);
        SoapObject request = WebserviceUtils.getRequest(map, "GetDataInfoForNaHuoByID");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.ChuKuServer);
        Log.e("zjy", "NahuoActivity->getFinishiInfo(): response==" + response.toString());
    }

    public void getNahuoDetailTableInfo(String pid) throws IOException, XmlPullParserException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", pid);
        SoapObject request = WebserviceUtils.getRequest(map, "GetDataInfoByNaHuoDetailID");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.ChuKuServer);
        Log.e("zjy", "NahuoActivity->getNahuoDetailTableInfo(): response==" + response.toString());
    }
    //              "PID": "101",
    //            "CreateDate": "2006/9/26 10:53:45",
    //            "CorpID": "1000",
    //            "State": "已入库,完成",
    //            "DeptID": "1011",
    //            "ClientInvoice": "0",
    //            "EmployeeID": "605",
    //            "Note": "",
    //            "invoiceType": "0",
    //            "ProviderID": "30383",
    //            "分公司": "ALLIC分公司",
    //            "部门": "北京华欣威",
    //            "业务员": "孔维泗",
    //            "客户开票": "其他",
    //            "开票公司": "",
    //            "ProviderName": "洋通",
    //            "是否开票": ""
}
