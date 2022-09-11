package demo.app.venus;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Dialog {
    Activity activity;
    AlertDialog dialog;
    Dialog(Activity a){
        this.activity = a;
    }
    void startDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog,null));

        dialog = builder.create();
        dialog.show();
    }
    void  dismiss(){
        dialog.dismiss();
    }
}
