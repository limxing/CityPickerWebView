package me.leefeng.citypickerwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class MainActivity extends AppCompatActivity implements CityPickerListener {

    private TextView textView;
    private CityPicker cityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityPicker = new CityPicker(MainActivity.this, this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityPicker.show();
            }
        });
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityPicker.close();
            }
        });
    }

    @Override
    public void getCity(final String name) {
        textView.setText(name);
    }

    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()){
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }
}
