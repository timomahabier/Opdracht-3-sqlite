package com.example.sqliteinsert;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass, NameDel, NameOld, NameNew;
    myDbAdapter helper;
    Context context;
    Button button;
    Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText) findViewById(R.id.editName);
        Pass = (EditText) findViewById(R.id.editPass);
        button = (Button) findViewById(R.id.button);
        buttonView = (Button) findViewById(R.id.button2);
        NameDel = (EditText) findViewById(R.id.delname);
        NameOld = (EditText) findViewById(R.id.editText3);
        NameNew = (EditText) findViewById(R.id.editText5);
        context = this;

        helper = new myDbAdapter(this);
        viewdata();
    }

    public void viewdata() {
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = helper.getAllData();
                        if (res.getCount() == 0) {
                            Message.message(context, "No data to show");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Password : " + res.getString(2) + "\n\n");
                        }
                        showAllMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showAllMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void deleteUser(View view) {
        Toast.makeText(this, "Running", Toast.LENGTH_LONG).show();
        String t1 = NameDel.getText().toString();

        long identity = helper.deleteData(t1);
        if (identity <= 0) {
            Message.message(context, "No user found");
        } else {
            Message.message(context, identity + "user(s) with the name: " + t1 + " successfully deleted");
        }
    }

    public void updateUser(View view) {
        Toast.makeText(this, "Running", Toast.LENGTH_LONG).show();
        String t1 = NameOld.getText().toString();
        String t2 = NameNew.getText().toString();

        if (t1.equals(t2)) {
            long identity = helper.updateData(t1, t2);
            if (identity <= 0) {
                Message.message(context, "Unsuccesfull");
            } else {
                Message.message(context, t1 + " is changed to " + t2);
            }
        }else{
            Message.message(context, "New name can't be the same");
        }
    }

    public void addUser(View view) {
        Toast.makeText(this, "Running", Toast.LENGTH_LONG).show();

        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();

        if (t1.isEmpty() || t2.isEmpty()) {
            Message.message(context, "Fill in both fields!");
        } else {

            helper.insertData(t1, t2);
            Message.message(context, "Successful");
        }
    }
}
