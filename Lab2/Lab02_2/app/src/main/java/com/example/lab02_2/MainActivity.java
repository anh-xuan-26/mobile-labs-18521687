package com.example.lab02_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. Khởi tạo dữ liệu cho mảng arr (còn gọi là data source)
        final String arr[]=getResources().getStringArray(R.array.names);

        //2. Lấy đối tượng Listview dựa vào id
        ListView lvPerson = (ListView) findViewById(R.id.lv_person);
        //3. Gán Data source vào ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arr);
        //4. Đưa Data source vào ListView
        lvPerson.setAdapter(adapter);
        final TextView tvSelection = (TextView) findViewById(R.id.tv_selection);
        //5. Thiết lập sự kiện cho Listview, khi chọn phần tử nào thì hiển thị lên TextView
        lvPerson.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        //đối số arg2 là vị trí phần tử trong Data Source (arr)
                        tvSelection.setText("position :" + arg2 + " ; value =" + arr[arg2]);
                    }
                });
    }

}