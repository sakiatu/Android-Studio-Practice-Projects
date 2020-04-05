package com.pietheta.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CountDown extends AppCompatActivity {

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        setToolbar();

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonSet.setOnClickListener(v -> {

            String input = mEditTextInput.getText().toString();
            if (input.length() == 0)   //Check if the input text is empty.
            {
                Toast.makeText(CountDown.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                return;  //Don't execute below it.
            }

            long millisInput = Long.parseLong(input) * 60000;  //Pass the milliseconds from input text.
            // *60000 is for converting minutes to millis.
            if (millisInput == 0)  //Now check if input millis is 0
            {
                Toast.makeText(CountDown.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;  //Don't execute below it.
            }

            setTime(millisInput);
            mEditTextInput.setText("");
        });

        mButtonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) pauseTimer();
            else startTimer();
        });


        mButtonReset.setOnClickListener(v -> resetTimer());
        //We delete updateCountDownText() from here, because onStart will be called after onCreate() every time.
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton done = toolbar.findViewById(R.id.done);
        title.setText(R.string.titleCountdown);
        cancel.setVisibility(View.VISIBLE);
        done.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        cancel.setOnClickListener(this::onClicked);
    }

    private void onClicked(View view) {
        if (view.getId() == R.id.cancel) {
            finish();
        }
    }

    private void setTime(long milliseconds)  //This will be the amount of milliseconds that we want set our timer to.
    {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;  //We save our time where our timer is supposed to end.

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) { //Every 1000 millis (1 second) onTick will be called.
            //Thus update our TextView.
            @Override
            public void onTick(long millisUntilFinished) {

                mTimeLeftInMillis = millisUntilFinished; //If we cancel the timer, and create a new one.
                //We continue at where we stopped.
                updateCountDownText(); //This will update the TextView.

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();      //Because our timer is not running, we set back 'Start'.
                //Because we first have to reset our timer,
                //since we can't start it when it's at 0.

            }
        }.start(); //As soon as we click the button and call startTimer(), the timer will create and start immediately.

        mTimerRunning = true;  //True, because now our timer is running.
        updateButtons();   //When our timer is running, it will set the text to 'Pause'.
        //When we pause our timer 'Reset' will be visible.
        //When we resume it, it will be invisible.
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;  //Set back our default time which is START_TIME_IN_MILLIS.
        updateCountDownText();  //Because we want to reset a text as well.
        updateButtons();    //Since we made our start button invisible in onFinish(),
        //We have to turn it back to visible after we reset our timer.
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;   //1 hour = 3600 sec
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;   //Convert the millis left (%3600) into seconds (/1000) and then into minutes (/60).
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;   //Return what is left after calculating our minutes.

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds); //Convert the hours, minutes & seconds into time string.
        } else       //if the hour is zero.
        {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds); //Convert the minutes & seconds into time string.

        }


        mTextViewCountDown.setText(timeLeftFormatted);  //This will update our countdown text.
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);  //Because we only can reset when it is not running.
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonReset.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000)  //1000 millis = 1 sec
            {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis)  //mStartTimeInMillis is our maximum time.
            {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }

    }

    private void closeKeyboard()   //For closing the keyboard after taking input in edit text.
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();  //We can use this editor to save our data.

        //We will save these when the app is closed or device is rotated, that we saved in onSaveInstanceState method earlier
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply(); //To actually save these variables.

        if (mCountDownTimer != null)  //if we try to cancel the mCountDownTimer without ever started the timer, the app crashes.
        {
            mCountDownTimer.cancel();  //Because we are restarting it on start anyways.
        }
    }

    @Override
    protected void onStart() {  //will be called after onCreate() method
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);   //600000 will be the default value when the app is first opened.
        // We can change it later.
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);  //mStartTimeInMillis is default time
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        //Same as we did in onRestoreInstanceState
        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);  //Restore our end time. We only do this if timer is running.
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();  //Time left in millis to reach our end time.

            if (mTimeLeftInMillis < 0)  //Overdue; in case of value is negative.
            {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();   //If we are not below 0, and our timer is running, we simply want to start our timer.
            }

        }
    }
}
