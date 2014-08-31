package me.doapps.tacher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

import me.doapps.adapter.comment_adapter;
import me.doapps.adapter.teacher_adapter;
import me.doapps.beans.Comment_DTO;
import me.doapps.beans.Teacher_DTO;

/**
 * Created by YUNIOR on 31/08/2014.
 */
public class Profile extends ActionBarActivity {
    private ArrayList<Comment_DTO> comment_dtos = new ArrayList<Comment_DTO>();
    private ListView list_comments;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        comment_dtos.add(new Comment_DTO("Ese profe es una mierda", 1));
        comment_dtos.add(new Comment_DTO("me aprobo con 11 el csm", 2));
        comment_dtos.add(new Comment_DTO("me aprobo con 13 el csm", 2));
        comment_dtos.add(new Comment_DTO("me jalo con 10 el csm", 2));

        list_comments = (ListView)findViewById(R.id.list_comments);
        list_comments.setAdapter(new comment_adapter(comment_dtos, Profile.this));


    }

}
