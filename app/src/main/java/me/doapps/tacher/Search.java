package me.doapps.tacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.doapps.adapter.teacher_adapter;
import me.doapps.beans.Teacher_DTO;
import me.doapps.utils.JSONParser;
import me.doapps.views.View_Tacher;

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Search extends ActionBarActivity {
    private ArrayList<Teacher_DTO> teacher_dtos = new ArrayList<Teacher_DTO>();
    ProgressDialog pDialog;
    private LinearLayout frame_teachers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));
        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));
        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));*/

        frame_teachers = (LinearLayout)findViewById(R.id.frame_teachers);
        for (int i = 0; i < 10; i++) {
            frame_teachers.addView(new View_Tacher(Search.this, new Teacher_DTO()));
        }


    }

}
