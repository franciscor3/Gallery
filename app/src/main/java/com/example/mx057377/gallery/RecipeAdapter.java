package com.example.mx057377.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mx057377 on 11/13/2017.
 */

class RecipeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Image> items;
    private boolean pressed = false;
    private TextView likes;
    private int index;

    public RecipeAdapter(Context context, ArrayList<Image> listItems) {
        mContext = context;
        items = listItems;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.list_item, parent, false);

        index = position;
        final ImageView picture = (ImageView) rowView.findViewById(R.id.image);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView comments = (TextView) rowView.findViewById(R.id.commentsNum);
        TextView date = (TextView) rowView.findViewById(R.id.dateFormat);


        description.setText(items.get(position).getDescription());
        picture.setTag(position);

        comments.setText(Integer.toString(items.get(position).getComments().length()));
        date.setText(items.get(position).getCreateDate());
        try {
            int id = mContext.getResources().getIdentifier(items.get(position).getFile().getString("name"),
                                                            "drawable", mContext.getPackageName());
            picture.setImageResource(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Button testButton = (Button) rowView.findViewById(R.id.like);
        testButton.setText(Integer.toString(items.get(position).getLikes()));
        testButton.setTag(items.get(position).getLikes());
        testButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                System.out.println("Entro");
                if (pressed) {
                    testButton.setBackgroundResource(R.drawable.btn_star_big_off);
                    testButton.setText(Integer.toString((int) testButton.getTag()));
                    pressed = false;
                } else {
                    System.out.println("cambia");
                    testButton.setBackgroundResource(R.drawable.btn_star_big_on);
                    testButton.setText((Integer.toString((int) testButton.getTag() + 1)));
                    pressed = true;
                }
            }
        });


        final ImageButton image = (ImageButton) rowView.findViewById(R.id.image);
        image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                System.out.println("Entro");
                Intent k = new Intent(mContext, ImageDetails.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                k.putExtra("description", items.get((Integer) picture.getTag()).getDescription());
                k.putExtra("id", items.get((Integer) picture.getTag()).getId());
                k.putExtra("likes", items.get((Integer) picture.getTag()).getLikes());
                k.putExtra("date", items.get((Integer) picture.getTag()).getCreateDate());
                k.putExtra("userName", items.get((Integer) picture.getTag()).getUserName());
                k.putExtra("file", items.get((Integer) picture.getTag()).getFile().toString());
                k.putExtra("comments", items.get((Integer) picture.getTag()).getComments().toString());

                    mContext.startActivity(k);
            }
        });


        return rowView;
    }

    public static int getDrawable(Context context, String name)//method to get id
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name,    //return id
                "your drawable", context.getPackageName());
    }
}
