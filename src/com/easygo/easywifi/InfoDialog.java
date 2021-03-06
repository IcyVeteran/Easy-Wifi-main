package com.easygo.easywifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;



/**
 * Created by TrixZ on 2014/11/8.
 */
public class InfoDialog extends DialogFragment {
    Boolean encrypt;
    String SSID1;
    int up,down,signal;
    WifiTest wifiadmin;
    EditText passwd;
    //LayoutInflater test=LayoutInflater.from(EasyWifiMain.class);

    private TextView WifiSSid;
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,String SSID,String passwd,Boolean encrypt1);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public void show(FragmentManager manager, String tag,Bundle savedinfo) {
        encrypt =savedinfo.getBoolean("encrypt");
        SSID1=savedinfo.getString("SSID");
        up=savedinfo.getInt("up");
        down=savedinfo.getInt("down");
        signal=savedinfo.getInt("signal");
        System.out.println("Encrypt-->"+encrypt);
        super.show(manager, tag);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if(encrypt==true) {
        View layout=inflater.inflate(R.layout.infowin, null);
            builder.setView(layout)
                    .setPositiveButton(R.string.Button_Connect, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText passedit=(EditText)InfoDialog.this.getDialog().findViewById(R.id.password);
                            String pass=passedit.getText().toString();
                            mListener.onDialogPositiveClick(InfoDialog.this,SSID1,pass,true);
                            System.out.println("Clicked-->OK");   // sign in the user ...
                        }
                    })
                    .setNegativeButton(R.string.Button_Cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            InfoDialog.this.getDialog().cancel();
                        }
                    });
            TextView SSID = (TextView)layout.findViewById(R.id.ssid1);
            SSID.setText(SSID1+"");
            TextView upload=(TextView)layout.findViewById(R.id.up1);
            upload.setText(up*100+"KB/S");
            TextView download=(TextView)layout.findViewById(R.id.down1);
            download.setText(down*100+"KB/S");
            ProgressBar proc = (ProgressBar)layout.findViewById(R.id.progressBar);
            proc.setProgress(signal);



            return builder.create();


        }
        else
        {
            View layout=inflater.inflate(R.layout.infowin2, null);

            builder.setView(layout)
                .setPositiveButton(R.string.Button_Connect, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    System.out.println("Clicked-->OK");
                    mListener.onDialogPositiveClick(InfoDialog.this,SSID1,"",false);
                }
            })
                .setNegativeButton(R.string.Button_Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InfoDialog.this.getDialog().cancel();
                    }
                });
            TextView SSID = (TextView)layout.findViewById(R.id.ssid2);
            SSID.setText(SSID1 + "");
            TextView upload=(TextView)layout.findViewById(R.id.up2);
            upload.setText(up*100+"KB/S");
            TextView download=(TextView)layout.findViewById(R.id.down2);
            download.setText(down*100+"KB/S");
            ProgressBar proc = (ProgressBar)layout.findViewById(R.id.progressBar);
            proc.setProgress(signal);

            return builder.create();
        }



    }
}
