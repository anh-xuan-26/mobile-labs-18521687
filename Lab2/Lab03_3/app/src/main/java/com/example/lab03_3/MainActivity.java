package com.example.lab03_3;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText etName;
    TextView tvSelection;
    Button btnSubmit;
    ListView lvPerson;
    ArrayList<String> names = null;
    ArrayAdapter<String> adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.et_name);
        tvSelection = (TextView) findViewById(R.id.tv_selection);

        lvPerson = (ListView) findViewById(R.id.lv_person);
        //1. Tạo ArrayList object
        names = new ArrayList<String>();
        //2. Gán Data Source (ArrayList object) vào ArrayAdapter
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, names);
        //3. gán Adapter vào ListView
        lvPerson.setAdapter(adapter);

        btnSubmit = (Button) findViewById(R.id.btn_nhap);
        //4. Xử lý sự kiện nhấn nút Nhập
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                names.add(etName.getText().toString());
                etName.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        //5. Xử lý sự kiện chọn một phần tử trong ListView
        lvPerson.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {

                tvSelection.setText("position : " + arg2 +
                        "; value =" + names.get(arg2));
            }
        });
        //6. xử lý sự kiện Long click
        lvPerson.setOnItemLongClickListener(new AdapterView
                .OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                names.remove(arg2);//xóa phần tử thứ arg2
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
