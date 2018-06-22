package com.exama.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"О нас");
        menu.add(0,2,1,"Выход");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case 1 :
                Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case 2 :
                SharedPreferences preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("is_auth",0);

                editor.apply();

                Intent intent1 = new Intent(AboutActivity.this,LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}