package br.diogo_julia.financialcontrol.app.activities.categoria;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;



public class CategoriaPopupActivity extends Activity {

    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_popup);

        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);
        but = new Button(this);
        but.setText("Click Me");
        but.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }

        });
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params);
    }

    @Override
    protected void onPause() {
        Log.d("ACTIVITY_PAUSE", "Atividade " + CategoriaPopupActivity.class + " pausada.");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("ACTIVITY_RETORNADA", "Atividade " + CategoriaPopupActivity.class + " retomada.");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("ACTIVITY_DESTRUIDA", "Atividade " + CategoriaPopupActivity.class + " destruida.");
        super.onDestroy();
    }

}
