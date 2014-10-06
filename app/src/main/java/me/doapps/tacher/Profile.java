package me.doapps.tacher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.doapps.adapter.comment_adapter;
import me.doapps.adapter.teacher_adapter;
import me.doapps.beans.Comment_DTO;
import me.doapps.beans.Teacher_DTO;
import me.doapps.utils.RoundedTransformation;

/**
 * Created by YUNIOR on 31/08/2014.
 */
public class Profile extends ActionBarActivity {
    private ArrayList<Comment_DTO> comment_dtos = new ArrayList<Comment_DTO>();

    private TextView txt_name;
    private TextView txt_description;
    private ImageView image_profile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String id = getIntent().getStringExtra("teacher_id");
        String name = getIntent().getStringExtra("teacher_name");
        String description = getIntent().getStringExtra("teacher_description");
        String image = getIntent().getStringExtra("teacher_image");

        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_description = (TextView)findViewById(R.id.txt_description);
        image_profile = (ImageView)findViewById(R.id.image_profile);

        txt_name.setText(name);
        txt_description.setText(description);
        Picasso.with(Profile.this).load(image).placeholder(R.drawable.teacher_profile_photo).centerCrop().fit().transform(new RoundedTransformation(133, 0)).into(image_profile);
    }

}
