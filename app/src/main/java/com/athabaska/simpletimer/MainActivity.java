package com.athabaska.simpletimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer _timer;
    private MediaPlayer _mPlayer;
    private EditText _txtMinutes;
    private EditText _txtSeconds;
    private Button _btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        _mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep1);
        _txtMinutes = findViewById(R.id.txtMinutes);
        _txtSeconds = findViewById(R.id.txtSeconds);
        _btnMain = findViewById(R.id.btnMain);
    }

    public void mainButtonOnPress(View view) {
        if (_timer == null) {
            long totalMs = (Long.parseLong(_txtMinutes.getText().toString()) * 60 + Long.parseLong(_txtSeconds.getText().toString())) * 1000;
            if (totalMs == 0) return;
            _timer = createTimer(totalMs, 1000);
            _timer.start();
        } else {
            _timer.cancel();
            _timer = null;
            _btnMain.setText("Start");
            _btnMain.setTextColor(Color.BLACK);
        }
    }

    private CountDownTimer createTimer(long millisInFuture, long countDownInterval) {
        return new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                long totalSeconds = millisUntilFinished / 1000;
                long minutes = totalSeconds / 60;
                long seconds = totalSeconds - minutes * 60;

                _btnMain.setText(minutes + ":" + String.format("%02d", seconds));
                _btnMain.setTextColor(Color.RED);
            }

            @Override
            public void onFinish() {
                _mPlayer.start();
                _timer = null;
                _btnMain.setText("Start");
                _btnMain.setTextColor(Color.BLACK);
            }
        };
    }
}