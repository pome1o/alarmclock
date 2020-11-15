package com.example.lee.alarmclock;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ListView li;
    private TextView tt, txt_en_title, txt_ch_title;
    private EditText etxt_en_t, etxt_ch_t;
    private Button btn_insert, btn_add;
    private View edv;
    LayoutInflater edly;
    adapter adp;
    SQLiteDatabase sql;
    List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setho();
        newdb();
        itemini();
        list = new ArrayList<Map<String, String>>();
        adp = new adapter();
        li.setAdapter(adp);
        edly = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        edv = edly.inflate(R.layout.listitem, null);
        button_intsert();
        setdata();


    }

    public void setdata() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","eee@"+adp.getCount());
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("en",etxt_en_t.getText().toString() );
                item.put("ch",etxt_ch_t.getText().toString());
                list.add(item);
                adp.additem(adp.getCount()+1);
                adp.notifyDataSetChanged();
                Log.d("TAG","eee#"+adp.getCount());
                Log.d("TAG","eee##"+list.size());
            }
        });

    }

    public void play(View v) {
        Intent go = new Intent(MainActivity.this, enquiz.class);
        startActivity(go);

    }

    public void itemini() {
        tt = (TextView) findViewById(R.id.tt);

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_add = (Button) findViewById(R.id.btn_add);

        txt_ch_title = (TextView) findViewById(R.id.txt_ch_title);
        txt_en_title = (TextView) findViewById(R.id.txt_en_title);

        etxt_ch_t = (EditText) findViewById(R.id.etxt_ch_t);
        etxt_en_t = (EditText) findViewById(R.id.etxt_en_t);

        li = (ListView) findViewById(R.id.listview);
    }

    public void button_intsert() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                li.requestFocus();
                indata();

            }
        });
    }

    public void newdb() {
        sql = openOrCreateDatabase("english.db", 4, null);
        sql.execSQL("CREATE TABLE IF NOT EXISTS " + "vocabulary" + " (" +
                "english varchar PRIMARY KEY NOT NULL ," +
                "chinese varchar NOT NULL" +
                ")");
        sql.close();
    }

    public void indata() {
        //sql = openOrCreateDatabase("english.db", 4, null);
        for (int i = 0; i < list.size(); i++) {

        }


    }



    public void setho() {
        Stetho.initializeWithDefaults(this);
    }

    public class adapter extends BaseAdapter {
        private LayoutInflater lyin;
        Holder holder;
        private ArrayList<Integer> plist;
        public  adapter(){
            plist = new ArrayList<Integer>();
        }
        public void additem(Integer i){
                plist.add(i);
        }

        public void removeitem(int index){
                plist.remove(index);
        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            lyin = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                holder = new Holder();
                convertView = lyin.inflate(R.layout.listitem, null);

                holder.txt_en = (TextView) convertView.findViewById(R.id.txt_en);
                holder.txt_ch = (TextView) convertView.findViewById(R.id.txt_ch);
                holder.btn_remove = (Button) convertView.findViewById(R.id.btn_remove);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.btn_remove.setId(position);
            holder.txt_en.setText(list.get(position).get("en"));
            holder.txt_ch.setText(list.get(position).get("ch"));
            holder.btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int reV = v.getId();
                    Log.d("TAG","eee#"+reV);
                    list.remove(reV);
                    removeitem(reV);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    class Holder {
        Button btn_remove;
        TextView txt_en;
        TextView txt_ch;
    }


}
