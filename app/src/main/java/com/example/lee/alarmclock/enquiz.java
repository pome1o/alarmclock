package com.example.lee.alarmclock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class enquiz extends AppCompatActivity {
    EditText etxt_q;
    TextView txt_q;
    Button ck;

    SQLiteDatabase sql;
    ArrayList<String> enal;
    ArrayList<String> chal;
    int quiznum = 3;
    int size = 0;
    int correct = 0;
    String an;
    int[] rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquiz);
        itemini();
        sql = openOrCreateDatabase("english.db", 4, null);
        Cursor cs = sql.rawQuery("SELECT * FROM vocabulary", null);
        enal = new ArrayList<String>();
        chal = new ArrayList<String>();

        cs.moveToFirst();
        do {
            enal.add(cs.getString(0));
            chal.add(cs.getString(1));
        } while (cs.moveToNext());
        cs.close();
        sql.close();
        rn = rand();
        quiz();
    }

    public void itemini() {
        etxt_q = (EditText) findViewById(R.id.etxt_q);
        txt_q = (TextView) findViewById(R.id.txt_q);
        ck = (Button) findViewById(R.id.ck);
    }

    public int[] rand() {
            size = enal.size();
        Random random = new Random();
        int[] rn = new int[size];

        for (int i = 0; i < size; i++) {
            rn[i] = i ;
        }
        for (int i = 0; i < size; i++) {
            int pos = random.nextInt(size);
            int temp = rn[i];
            rn[i] = rn[pos];
            rn[pos] = temp;
            Log.d("TAG","eee@"+pos);
        }
        for (int i = 0; i < size; i++) {
              Log.d("TAG","eee"+rn[i]);
        }
        return rn;
    }

    public void quiz() {
        int titlenum = rn[correct];
        an = chal.get(titlenum);
        txt_q.setText(enal.get(titlenum));
    }

    public void check(View v) {
        if(correct != quiznum) {
            if (an.equals(etxt_q.getText().toString()) && etxt_q.getText().length() > 0) {
                Toast.makeText(enquiz.this, "答對", Toast.LENGTH_SHORT).show();
                etxt_q.setText("");
                correct += 1;
                if(correct != quiznum)
                    quiz();
                else {
                    Toast.makeText(enquiz.this, "過關", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(enquiz.this, "錯誤", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(enquiz.this, "過關別再按了操", Toast.LENGTH_SHORT).show();
        }
    }
}
