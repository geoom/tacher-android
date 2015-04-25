package me.doapps.tacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.doapps.modules.Module_Tacher;
import me.doapps.utils.Fonts;
import me.doapps.utils.InternetUtil;
import me.doapps.utils.JSONParser;
import me.doapps.utils.SessionManager;
import me.doapps.ws.WS_Tacher;


public class Main extends ActionBarActivity {
    private static final long SPLASH_SCREEN_DELAY = 4000;

    private Context context;
    private String[] APP_PERMISIONS = {"email"};
    private SessionManager manager;

    private static final String ID = "465266160281012";
    Facebook mFacebook;
    Button btn_login_fb;

    private ProgressDialog dialog;
    private String user_id;
    private String user_name;
    private String user_lastname;
    private String user_access_token;
    private String user_facebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActionBar().hide();

        SessionManager manager = new SessionManager(Main.this);
        Log.e("session_manager", manager.getEstadoSession()+","+manager.getNomUsuario());
        if(manager.getEstadoSession()){
            startActivity(new Intent(Main.this, Search.class));
            finish();
        }

        /*sincronizamos*/
        Button btn_login_fb = (Button) findViewById(R.id.btn_login_fb);

        TextView txt_titulo = (TextView) findViewById(R.id.txt_titulo);
        txt_titulo.setTypeface(Fonts.setAngryBird(Main.this));

        TextView txt_subtitulo = (TextView) findViewById(R.id.txt_subtitulo);
        txt_subtitulo.setTypeface(Fonts.setAngryBird(Main.this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progress_splash).setVisibility(View.GONE);
                findViewById(R.id.btn_login_fb).setVisibility(View.VISIBLE);
                /*findViewById(R.id.btn_login_fb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Main.this, Search.class);
                        startActivity(i);
                        finish();
                    }
                });*/

            }
        }, 3000);

        /*preparing facebook*/
        context = Main.this;
        manager = new SessionManager(context);

        mFacebook = new Facebook(ID);

        /*verify session*/
        /*if (manager.getEstadoSession()) {
            startActivity(new Intent(Main.this, Search.class));
            finish();
        }*/

        btn_login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean internet = new InternetUtil(Main.this).isConnectingToInternet();
                if (internet) {
                    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(
                            mFacebook);
                    mFacebook.authorize(Main.this, APP_PERMISIONS,
                            new LoginDialogListener());
                } else {
                    Toast.makeText(Main.this, "Necesitas conexión a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebook.authorizeCallback(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Inner Class Facebook *
     */
    private class LoginDialogListener implements Facebook.DialogListener {

        public void onComplete(Bundle values) {
            Log.d("FACEBOOK LOGIN ", "LoginONComplete");
           /*AsyncTask Login with facebook*/


            Log.e("access_token", mFacebook.getAccessToken());
            Log.e("access_expires", mFacebook.getAccessExpires()+"");

            new sendRequestLoginFacebook().execute(mFacebook.getAccessToken());
        }

        public void onFacebookError(FacebookError e) {
            Log.d("FACEBOOK LOGIN", "Facebook ERROR ");
            Log.d("FACEBOOK LOGIN", e.toString());
            Toast.makeText(getApplicationContext(),
                    "LOGIN ERROR:\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        public void onError(DialogError e) {
            Log.d("FACEBOOK LOGIN", "ON ERROR ");
            Log.d("FACEBOOK LOGIN", e.toString());
            Toast.makeText(getApplicationContext(),
                    "LOGIN ERROR:\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        public void onCancel() {
            Log.d("FACEBOOK LOGIN", "CANCELADO ");
            Toast.makeText(getApplicationContext(), "LOGIN ERROR CANCELADO",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**Asynctask WebServices**/
    private class sendRequestLoginFacebook extends AsyncTask<String, Void, String>{
        private JSONObject objectFacebook;
        private JSONParser parser = new JSONParser();
        private String response = "";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Main.this, null, "Ingresando a Tacher ...", true, false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                response = mFacebook.request("me");
                Log.e("response", response);
                try {
                    JSONObject object_response = new JSONObject(response);
                    user_facebook = object_response.getString("id");
                    user_name = object_response.getString("first_name");
                    user_lastname = object_response.getString("last_name");
                    user_access_token = strings[0];
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("access_token", strings[0]));

                JSONObject object = parser.makeHttpRequest(WS_Tacher.WS_LOGIN, "GET", params);
                try {
                    boolean success = object.getBoolean("status");
                    if(success){
                        user_id = object.getJSONObject("user").getString("id");
                        SessionManager manager = new SessionManager(Main.this);
                        manager.crearSessionOn(user_id, user_name, user_lastname, user_access_token);
                    }
                    else{
                        user_id = "-1";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("result", object.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user_id;
        }

        @Override
        protected void onPostExecute(String user_id) {
            dialog.dismiss();
            if(Integer.parseInt(user_id)>0){

                startActivity(new Intent(Main.this, Search.class));
                finish();
            }

        }
    }
}
