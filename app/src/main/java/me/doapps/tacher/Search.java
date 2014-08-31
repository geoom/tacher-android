package me.doapps.tacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by jnolascob on 31/08/2014.
 */
public class Search extends ActionBarActivity {
    private ArrayList<Teacher_DTO> teacher_dtos = new ArrayList<Teacher_DTO>();
    private ListView list_rankking;
    ProgressDialog pDialog;
    TaskBusquedaEmpresa taskEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));
        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));
        teacher_dtos.add(new Teacher_DTO("Javier", "Gamboa"));
        teacher_dtos.add(new Teacher_DTO("Dinosaurio", "Salinas"));

        list_rankking = (ListView) findViewById(R.id.list_ranking);
        list_rankking.setAdapter(new teacher_adapter(teacher_dtos, Search.this));
        String parametros[] = { "" };
        taskEmpresa = new TaskBusquedaEmpresa();
        //taskEmpresa.execute(parametros);


        list_rankking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Search.this, Profile.class));
            }
        });
    }

    private class TaskBusquedaEmpresa extends
            AsyncTask<String, Teacher_DTO, ArrayList<Teacher_DTO>> {
        private static final String WS_BUSCAR_EMPRESA = "http://tacher.herokuapp.com/api/teachers.json";
        private JSONParser jsonParser = new JSONParser();
        ArrayList<Teacher_DTO> Empresas = new ArrayList<Teacher_DTO>();

        @Override
        protected ArrayList<Teacher_DTO> doInBackground(String... args) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("empresa_nombre", args[0]));

                // Log.e("Empresas", params.toString());
                JSONObject object = jsonParser.makeHttpRequest(
                        WS_BUSCAR_EMPRESA, "POST", params);

                Log.e("BUSQUEDA EMPRESAS", object.toString());

                JSONArray result = object.getJSONArray("EMPRESAS");

                for (int i = 0; i < 10; i++) {
                    // Log.e("EMPRESAS",
                    // result.getJSONObject(i).getString("RazonSocial"));

                    Empresas.add(new Teacher_DTO(result.getJSONObject(i).getString("model"), ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Empresas;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Search.this);
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Obteniendo datos...");
            pDialog.show();
        }

        protected void onProgressUpdate(Teacher_DTO... values) {
            Empresas.add(values[0]);
        }

        protected void onPostExecute(ArrayList<Teacher_DTO> result) {
            list_rankking.setAdapter(new teacher_adapter(result, Search.this));
            pDialog.dismiss();
        }
    }
}
