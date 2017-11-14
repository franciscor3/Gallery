package com.example.mx057377.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mx057377 on 11/13/2017.
 */

public class ImageDetails extends Activity {
    private JSONObject file;
    private JSONArray  comments;
    private String desc;
    private String dateCreated;
    private String user;
    private int id;
    private int likes;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        System.out.println("new Activity");

        try {
            file = new JSONObject(getIntent().getStringExtra("file"));
            comments = new JSONArray(getIntent().getStringExtra("comments"));
            id = getIntent().getIntExtra("id",0);
            desc = getIntent().getStringExtra("description");
            user = getIntent().getStringExtra("userName");
            likes = getIntent().getIntExtra("likes",0);
            dateCreated = getIntent().getStringExtra("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView picture = (ImageView) findViewById(R.id.image);
        TextView description = (TextView) findViewById(R.id.description);
        TextView username = (TextView) findViewById(R.id.userName);
        TextView date = (TextView) findViewById(R.id.dateFormat);
        TextView commentNum = (TextView) findViewById(R.id.commentsNum);
        Button likesNum = (Button) findViewById(R.id.like);

        description.setText(desc);
        username.setText( user);
        date.setText(dateCreated);
        commentNum.setText(Integer.toString(comments.length()));
        likesNum.setText(Integer.toString(likes));

        try {
            int id = this.getResources().getIdentifier(file.getString("name"),"drawable", this.getPackageName());
            picture.setImageResource(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListView = (ListView) findViewById(R.id.commentList);
        final ArrayList<String> listItems = new ArrayList();

        if(comments.length() > 0) {
            for (int i = 0; i < comments.length(); i++) {
                JSONObject comment = null;
                try {
                    comment = comments.getJSONObject(i);
                    listItems.add(comment.getJSONObject("user").getString("name") + ": "
                            + comment.getString("comment") + " ["
                            + comment.getString("created_at") + "]");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            listItems.add("No comments written");
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);


        final ImageButton newComment = (ImageButton) findViewById(R.id.sendComment);
        newComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                System.out.println("Entro");

                EditText comm = findViewById(R.id.newComment);
                String textToast = "";
                if(comm.getText().length()> 0){
                    textToast = "Comment submitted";
                    comm.setText("");
                }else{
                   textToast = "Empty comment not allowed";
                }

                Toast toast = Toast.makeText(getBaseContext(), textToast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });

        final Button back = (Button) findViewById(R.id.fab);
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                System.out.println("Entro");
                finish();
            }
        });



    }
}
