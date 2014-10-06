package me.doapps.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.doapps.tacher.R;

/**
 * Created by jnolascob on 05/10/2014.
 */
public class Dialog_Rate extends AlertDialog{
    public Dialog_Rate(Context context) {
        super(context);
        initDialog();
    }

    protected Dialog_Rate(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    protected Dialog_Rate(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog();
    }

    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_rate, null);
        setView(view);

        Button btn_rate_ok = (Button)view.findViewById(R.id.btn_rate_ok);
        final TextView select_rate_jalador = (TextView)view.findViewById(R.id.select_rate_jalador);
        FrameLayout frame_jalador = (FrameLayout)view.findViewById(R.id.frame_jalador);

        frame_jalador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_rate_jalador.setVisibility(View.VISIBLE);
            }
        });


        //setCancelable(false);
    }
}
