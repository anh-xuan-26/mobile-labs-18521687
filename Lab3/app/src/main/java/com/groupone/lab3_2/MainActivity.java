package com.groupone.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvPerson;
    FloatingActionButton btnAdd;
    ArrayAdapter<Contact> adapter = null;

    DatabaseHandler db;
    List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);


        // Thêm liên hệ
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Trinh", "01274916935"));
        db.addContact(new Contact("Tâm", "01659137696"));
        db.addContact(new Contact("Tuyền", "01638213420"));
        db.addContact(new Contact("Duyên", "01263270465"));

        // Đọc các liên hệ
        Log.d("Reading: ", "Reading all contacts..");

        contacts = db.getAllContacts();

        lvPerson = (ListView) findViewById(R.id.lv_person);
        btnAdd = findViewById(R.id.btn_add);
        adapter = new ArrayAdapter<Contact>
                (this, android.R.layout.simple_list_item_1, contacts);
        lvPerson.setAdapter(adapter);
        for (Contact cn : contacts) {

            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Ghi các liên hệ đến log
            Log.d("Name: ", log);

        }

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEditDialog(contacts.get(i), i);

            }
        });

        lvPerson.setOnItemLongClickListener(new AdapterView
                .OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                db.deleteContact(contacts.get(arg2));
                contacts.remove(arg2);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });
    }

    private void openEditDialog(Contact contact, int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etPhone = dialog.findViewById(R.id.et_phone);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);

        etName.setText(contact.getName());
        etPhone.setText(contact.getPhoneNumber());

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Contact contact1 = new Contact(position+1, etName.getText().toString(), etPhone.getText().toString());
                db.updateContact(contact1);
                contacts.set(position, contact1);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void openAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etPhone = dialog.findViewById(R.id.et_phone);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel);
        Button btnConfirm = dialog.findViewById(R.id.bt_confirm);


        btnCancel.setOnClickListener(view -> dialog.dismiss());

        btnConfirm.setOnClickListener(view -> {
            if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //tvHello.setText("Hi " + etUsername.getText().toString());
                Contact contact1 = new Contact(etName.getText().toString(), etPhone.getText().toString());
                db.addContact(contact1);
                contacts.add(contact1);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }

        });

        dialog.show();
    }
}