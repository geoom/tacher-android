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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.doapps.utils.Fonts;
import me.doapps.utils.InternetUtil;
import me.doapps.utils.JSONParser;
import me.doapps.utils.SessionManager;


public class Main extends ActionBarActivity {
    private static final long SPLASH_SCREEN_DELAY = 4000;

    private Context context;
    private String[] APP_PERMISIONS = {"email"};
    private SessionManager manager;

    private static final String ID = "465266160281012";
    Facebook mFacebook;
    Button btn_login_fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActionBar().hide();

        /*sincronizamos*/
        Button btn_login_fb = (Button)findViewById(R.id.btn_login_fb);

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
        if (manager.getEstadoSession()) {
            startActivity(new Intent(Main.this, Search.class));
            finish();
        }

        btn_login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean internet = new InternetUtil(Main.this).isConnectingToInternet();
                if (internet){
                    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(
                            mFacebook);
                    mFacebook.authorize(Main.this, APP_PERMISIONS,
                            new LoginDialogListener());
                }
                else{
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


    /** Inner Class Facebook **/
    private class LoginDialogListener implements Facebook.DialogListener {

        public void onComplete(Bundle values) {
            Log.d("FACEBOOK LOGIN ", "LoginONComplete");
            // Intent intent = new Intent(LoginFb.this, Map.class);
            // startActivity(intent);
            new TaskLoginFacebook().execute();

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

    /**Async Task Facebook**/
    private class TaskLoginFacebook extends AsyncTask<Void, Void, Message> {

        private static final String WS_ENTRAR_FACEBOOK = "http://cuscoguia.com/jonathan/webservices/signin.php";

        private String USUARIO_FACEBOOK;
        private String USUARIO_NICK;
        private String USUARIO_NOMBRES;
        private String USUARIO_APELLIDOS;
        private String USUARIO_SEXO;
        private String USUARIO_EMAIL;
        private String USUARIO_IMAGEN;

        private String response = "";
        private JSONParser parser = new JSONParser();
        private ProgressDialog pDialog;
        JSONObject objectFacebook;

        @Override
        protected Message doInBackground(Void... args) {

            Message message = new Message();

            try {
                response = mFacebook.request("me");
                objectFacebook = new JSONObject(response);

                USUARIO_FACEBOOK = "" + objectFacebook.getLong("id");
                USUARIO_NICK = objectFacebook.getString("first_name");
                USUARIO_NOMBRES = objectFacebook.getString("first_name");
                USUARIO_SEXO = objectFacebook.getString("gender");
                USUARIO_APELLIDOS = objectFacebook.getString("last_name");
                USUARIO_EMAIL = objectFacebook.getString("email");
                USUARIO_IMAGEN = "https://graph.facebook.com/"
                        + USUARIO_FACEBOOK + "/picture";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nombres", USUARIO_NOMBRES));
                params.add(new BasicNameValuePair("apellidos",
                        USUARIO_APELLIDOS));
                params.add(new BasicNameValuePair("nick", USUARIO_NICK));
                params.add(new BasicNameValuePair("mail", USUARIO_EMAIL));
                params.add(new BasicNameValuePair("sexo", USUARIO_SEXO));
                params.add(new BasicNameValuePair("facebook", USUARIO_FACEBOOK));

                /*Session session = Session.getActiveSession();
                if(session == null){
                    session = Session.openActiveSessionFromCache(Main.this);
                }
                if(session != null && session.getState().isOpened()){
                    Log.e("sessionToken", session.getAccessToken());
                    Log.e("sessionTokenDueDate", session.getExpirationDate().toLocaleString());
                }
                else{
                    Log.e("session token", "nada");
                }*/

                Log.e("params ws", params.toString());

                JSONObject object = parser.makeHttpRequest(WS_ENTRAR_FACEBOOK,
                        "POST", params);

                message.arg1 = object.getInt("success");
                message.obj = object.getJSONArray("USUARIOS").getJSONObject(0)
                        .getString("facebok");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(Message result) {
            SessionManager manager = new SessionManager(Main.this);
            int success = result.arg1;
            String nombre = "", id = "", apellidos = "";

            try {
                id = objectFacebook.getString("id");
                nombre = objectFacebook.getString("first_name");
                apellidos = objectFacebook.getString("last_name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            manager.crearSessionOn(id, nombre, apellidos);
            Main.this.finish();

            Intent MainActivityIntent = new Intent(Main.this,
                    Search.class);
            MainActivityIntent.putExtra("first_name", nombre);
            MainActivityIntent.putExtra("id_facebook", id);

            startActivity(MainActivityIntent);
            pDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Ingresando a Tacher App...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

}
