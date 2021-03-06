package com.example.hele.jizhangben;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCostBeanList=new ArrayList<>();
        mDatabaseHelper=new DatabaseHelper(this);
        ListView costList=(ListView) findViewById(R.id.lv);
        initCostData();
        madapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(madapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);
                final EditText title=(EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money=(EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("新建账单");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        CostBean costBean=new CostBean();
                        costBean.costTitle=title.getText().toString();
                        costBean.costMoney=money.getText().toString();
                        costBean.costDate=date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth();
                        mDatabaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        madapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
    }

    private void initCostData() {

        Cursor cursor=mDatabaseHelper.getAllCostData();
        if(cursor!=null){
            while (cursor.moveToNext()){
                CostBean costBean=new CostBean();
                costBean.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
                costBean.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            Intent intent=new Intent(MainActivity.this,ChartsActivity.class);
            intent.putExtra("cost_list",(Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
