package com.b1b.js.erpandroid_market;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class NahuoActivity extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nahuo);
        lv = (ListView) findViewById(R.id.activity_nahuo_lv);
    }
}
