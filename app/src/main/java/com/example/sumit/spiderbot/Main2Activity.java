package com.example.sumit.spiderbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    public static String url = "192.168.43.168:8080";
    private GridView grid;
    private int ids[]={10,9,11,6,8,7,13,14,12,3,5,4,-1,-1,15,2,1,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        CustomGrid adapter = new CustomGrid(Main2Activity.this,ids);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(Main2Activity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });
        final EditText et = findViewById(R.id.et_url);
        et.setText(url);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = et.getText().toString();
            }
        });
    }
}

