package com.exama.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.exama.exam.net.MarvelApi;
import com.exama.exam.net.request.characters.model.Character;
import com.exama.exam.net.request.characters.model.CharacterDataWrapper;
import com.exama.exam.net.request.characters.model.Image;
import com.exama.exam.utils.CredentialsUtils;
import java.util.ArrayList;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import static com.exama.exam.utils.CredentialsUtils.public_key;
import static com.exama.exam.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity {
    ArrayList<Character> characters_data = new ArrayList<Character>();
    AdapterListView adapterListView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        getData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        menu.add(0,1,1,"Выход");
        menu.add(0,2,2,"O приложении");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){

            case 1 :
                preferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("is_auth",0);

                editor.apply();

                Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case 2 :

                Intent intent2 = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        ButterKnife.bind(this);

        MarvelApi marvelApi = MarvelApi.getInstance();
        Subscription subscription = marvelApi.getMarvel(ts, public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CharacterDataWrapper>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MainActivity", "onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", "onError => " + e.getMessage());
                               }

                               @Override
                               public void onNext(CharacterDataWrapper response) {
                                   Log.d("MainActivity", "onNext => " + response);
                                   try {
                                       if (response.getData().getResults().size() > 0) {
                                           for (int i = 0; i < 20; i++) {
                                               String name = response.getData().getResults().get(i).getName();
                                               Image image = response.getData().getResults().get(i).getThumbnail();
                                               String description = response.getData().getResults().get(i).getDescription();
                                               Integer pk = response.getData().getResults().get(i).getPk();
                                               Character character_item = new Character(pk, name, image, description);
                                               characters_data.add(character_item);
                                           }
                                       }
                                       initList();
                                   } catch (NullPointerException e) {
                                       Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
                                   }
                               }
                           }
                );
    }

    private void initList(){
        adapterListView = new AdapterListView(this,characters_data);
        final ListView listOfRating = (ListView)findViewById(R.id.ItemView);
        listOfRating.setAdapter(adapterListView);

        listOfRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CharacterDetail.class);
                Integer char_id = characters_data.get(position).getPk();
                Log.e("XXXX", char_id.toString());
                intent.putExtra("char_id", char_id);
                startActivity(intent);
            }
        });
    }
}