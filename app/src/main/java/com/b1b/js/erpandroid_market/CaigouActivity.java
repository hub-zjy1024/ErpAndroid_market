package com.b1b.js.erpandroid_market;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.b1b.js.erpandroid_market.entity.Caigoudan;
import com.b1b.js.erpandroid_market.utils.MyDecoration;
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

public class CaigouActivity extends AppCompatActivity {

    private RecyclerView mRview;

    private MyRviewAdapter adapter;
    private List<Caigoudan> caigouList;
    private EditText editPartNo;
    private Button btnSearch;
    private RadioGroup radioGroup;
    private View dialogView;
    private Caigoudan currentCaigoudan;
    int times = 0;

    interface OnItemClickListener {
        void onClick(int position);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (caigouList.size() != 0)
                        adapter.notifyDataSetChanged();
                    break;
                case 1:
                    MyToast.showToast(CaigouActivity.this, "当前网络状态不佳");
                    break;
                case 2:
                    MyToast.showToast(CaigouActivity.this, "条件有误，请重新输入");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigou);
        mRview = (RecyclerView) findViewById(R.id.caigou_rview);
        editPartNo = (EditText) findViewById(R.id.caigou_ed_partNo);
        btnSearch = (Button) findViewById(R.id.caigou_serach);

        dialogView = LayoutInflater.from(CaigouActivity.this).inflate(R.layout.dialog_view_typeinfo, null);
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.dialog_caigou_rg);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partNo = editPartNo.getText().toString().trim();
                if (caigouList.size() != 0) {
                    caigouList.clear();
                    adapter.notifyDataSetChanged();
                }
                if (TextUtils.isEmpty(MyApp.id)) {
                    MyToast.showToast(CaigouActivity.this, "登录id为空，请重新登录");
                    return;
                }
                getData("", Integer.parseInt(MyApp.id), partNo);
            }
        });
        //创建LayoutManager，并设置方向，水平或竖直
        LinearLayoutManager lManager = new LinearLayoutManager(CaigouActivity.this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRview.setLayoutManager(lManager);
        mRview.addItemDecoration(new MyDecoration(CaigouActivity.this, MyDecoration.VERTICAL));
        caigouList = new ArrayList<>();
        adapter = new MyRviewAdapter(caigouList, CaigouActivity.this, new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(CaigouActivity.this, CaigoudanEditActivity.class);
                currentCaigoudan = caigouList.get(position);
                if (currentCaigoudan != null) {
                    intent.putExtra("pid", currentCaigoudan.getPid());
                    intent.putExtra("partNo", currentCaigoudan.getPartNo());
                    startActivity(intent);
                }
            }
        }
        );
        mRview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(MyApp.id)) {
            caigouList.clear();
            adapter.notifyDataSetChanged();
            getData("", Integer.parseInt(MyApp.id), "");
        } else {
            MyToast.showToast(CaigouActivity.this, "用户id不存在，请重新登录");
        }
    }

    private void getData(final String checkWord, final int buyerId, final String partNo) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String res = getCaigoudan(checkWord, buyerId, partNo);
                    String json = "";
                    if (res.length() > 7) {
                        json = res.substring(7);
                    }
                    JSONObject object = new JSONObject(json);
                    JSONArray array = object.getJSONArray("RES");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String pid = obj.getString("单据编号");
                        String createdDate = obj.getString("制单日期");
                        String state = obj.getString("单据状态");
                        String ywName = obj.getString("业务员");
                        String caigouName = obj.getString("采购员");
                        String partNo1 = obj.getString("型号");
                        Caigoudan caigoudan = new Caigoudan(state, pid, createdDate, ywName, caigouName, partNo1);
                        caigouList.add(caigoudan);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (IOException e) {
                    mHandler.sendEmptyMessage(1);
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    mHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class MyRviewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Caigoudan> data;
        private Context mContext;
        private MyViewHolder mHolder;
        private OnItemClickListener itemClickListener;

        public MyRviewAdapter(List<Caigoudan> data, Context mContext, OnItemClickListener itemClickListener) {
            this.data = data;
            this.mContext = mContext;
            this.itemClickListener = itemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_caigou_simpleitem, parent, false);
            //            view.setBackgroundColor(Color.RED);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(((RecyclerView) parent).getChildAdapterPosition(view));
                }
            });
            mHolder = new MyViewHolder(view);
            return mHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Caigoudan s = data.get(position);
            if (s != null) {
                holder.tv1.setText(s.toString());
            }
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.item_caigou_tv);
        }
    }

    public String getCaigoudan(String checkWord, int buyerId, String partNo) throws IOException, XmlPullParserException {
        //        GetBillByPartNo
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("checkWord", checkWord);
        map.put("buyerID", buyerId);
        map.put("partNo", partNo);
        SoapObject request = WebserviceUtils.getRequest(map, "GetBillByPartNo");
        SoapPrimitive response = WebserviceUtils.getSoapPrimitiveResponse(request, SoapEnvelope.VER11, WebserviceUtils.MartService);
        return response.toString();
    }

}
