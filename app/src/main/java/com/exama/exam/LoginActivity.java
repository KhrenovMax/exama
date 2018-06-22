package com.exama.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login,
            pass;

    private SharedPreferences preferences;

    ControlsSQL dbHelper;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button enterButton = findViewById(R.id.inlet);
        Button openRegButton = findViewById(R.id.register);
        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);
        enterButton.setOnClickListener(this);
        openRegButton.setOnClickListener(this);
        dbHelper = new ControlsSQL(this);
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        int is_auth = preferences.getInt("is_auth", 0);
        if (is_auth == 1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {


        // opens the activity depending on the button you press
        switch (v.getId()) {
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.inlet:
                String strlogin = login.getText().toString();
                String strpass = pass.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                String sel = "login" + "=" + "'" + strlogin + "'";
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("table1", null, sel, null, null, null, null);
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int familyColIndex = c.getColumnIndex("family");
                    int surnameColIndex = c.getColumnIndex("surname");
                    int loginColIndex = c.getColumnIndex("login");
                    int birthdayColIndex = c.getColumnIndex("birthday");
                    int passColIndex = c.getColumnIndex("password");

                    do {
                        String userPass = c.getString(passColIndex);
                        String userLogin = c.getString(loginColIndex);
                        if (userPass.equalsIgnoreCase(strpass) && userLogin.equalsIgnoreCase(strlogin)) {
                            c.close();
                            dbHelper.close();
                            SharedPreferences.Editor editer = preferences.edit();
                            editer.putInt("is_auth", 1);
                            editer.putString("login", strlogin);
                            editer.apply();
                            Intent intent2 = new Intent(this, MainActivity.class);
                            startActivity(intent2);
                        } else {
                            Toast.makeText(this, "Пароль  неверный", Toast.LENGTH_LONG).show();
                        }

                    } while (c.moveToNext());

                } else {
                    Toast.makeText(this, "Логин  неверный", Toast.LENGTH_LONG).show();
                }


                break;
        }
    }

}