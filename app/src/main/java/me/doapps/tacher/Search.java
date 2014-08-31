package me.doapps.tacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.doapps.adapter.teacher_adapter;
import me.doapps.beans.Teacher_DTO;

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Search extends ActionBarActivity {
    private ArrayList<Teacher_DTO> teacher_dtos = new ArrayList<Teacher_DTO>();
    private ListView list_rankking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));

        list_rankking = (ListView)findViewById(R.id.list_ranking);
        list_rankking.setAdapter(new teacher_adapter(teacher_dtos, Search.this));



        list_rankking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Search.this, Profile.class));
            }
        });
    }
}
