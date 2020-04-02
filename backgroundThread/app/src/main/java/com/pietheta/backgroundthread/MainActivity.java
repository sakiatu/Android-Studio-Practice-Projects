package com.pietheta.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button button;
    Handler mainHandler = new Handler();
    private volatile boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.start);
        button.setOnClickListener(this::startThread);
        findViewById(R.id.stop).setOnClickListener(this::stopThread);
    }

    private void startThread(View view) {
        stop=false;
      /*  ExampleThread thread = new ExampleThread(10);

        thread.start();*/

      /*  ExampleRunable runable = new ExampleRunable(10);
        new Thread(runable).start();*/

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    if (stop) return;
                    Log.d(TAG, "startThread: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void stopThread(View view) {
        stop = true;
    }

    class ExampleThread extends Thread {
        int seconds;

        public ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {

            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunable implements Runnable {
        int seconds;

        public ExampleRunable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                if (i == 5) {
                    button.post(new Runnable() {
                        @Override
                        public void run() {
                            button.setText("50%");
                        }
                    });
                    /* mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            button.setText("50%");
                        }
                    });*/
                }
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
