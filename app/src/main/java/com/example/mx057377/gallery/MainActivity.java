package com.example.mx057377.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listImages);

        final ArrayList<Image> imagesList = loadJSONFromAsset();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");


        Collections.sort(imagesList, new Comparator<Image>(){
            public int compare(Image o1, Image o2){
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = formatter.parse(o1.getCreateDate());
                    date2 = formatter.parse(o2.getCreateDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    return date1.compareTo(date2);
            }
        });



        RecipeAdapter adapter = new RecipeAdapter(this, imagesList);
        mListView.setAdapter(adapter);


    }
    public ArrayList<Image> loadJSONFromAsset() {
        ArrayList<Image> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("storage.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("images");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                locList.add(new Image(jo_inside.getString("description"),
                        jo_inside.getInt("id"),jo_inside.getJSONObject("file"),
                        jo_inside.getInt("likes"), jo_inside.getJSONObject("user").getString("name"),
                        jo_inside.getString("created_at"), jo_inside.getJSONArray("comments")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }
}
