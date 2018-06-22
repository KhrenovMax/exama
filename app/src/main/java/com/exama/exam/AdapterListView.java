package com.exama.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.exama.exam.net.request.characters.model.Character;
import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;

    ArrayList<Character> characters_data;

    public AdapterListView(Context context, ArrayList<Character> _characters) {
        ctx = context;
        characters_data = _characters;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return characters_data.size();
    }

    @Override
    public Object getItem(int position) {
        return characters_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Character getCharacter(int position){
        return((Character)getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Character character = getCharacter(position);

        TextView namefield = (TextView) view.findViewById(R.id.characterName);

        ImageView imagefield = (ImageView) view.findViewById(R.id.characterImage);

        namefield.setText(character.getName());

        String imagePath = character.getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  character.getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(imagefield);

        return view;
    }
}