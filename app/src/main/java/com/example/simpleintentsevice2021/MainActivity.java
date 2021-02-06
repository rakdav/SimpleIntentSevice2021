package com.example.simpleintentsevice2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button start,stop;
    private TextView textViewPercent;
    private Intent serviceIntent;
    private ResponseReciver recever=new ResponseReciver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        start=findViewById(R.id.button_start);
        stop=findViewById(R.id.button_stop);
        textViewPercent=findViewById(R.id.textView_percent);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                if(serviceIntent!=null)
                {
                    SimpleIntentService.shouldStop=true;
                }
                else {
                    serviceIntent = new Intent(MainActivity.this, SimpleIntentService.class);
                    startService(serviceIntent);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serviceIntent!=null)
                {
                    SimpleIntentService.shouldStop=true;
                }
            }
        });
    }

    public class ResponseReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(SimpleIntentService.ACTION_1))
            {
                int value=intent.getIntExtra(SimpleIntentService.PARAM_PERCENT,0);
                new ShowProgressBarTask().execute(value);
            }
        }
    }

    class ShowProgressBarTask extends AsyncTask<Integer,Integer,Integer>
    {
        @Override
        protected Integer doInBackground(Integer... integers) {
            return integers[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setProgress(integer);
            textViewPercent.setText(integer+" % Loaded");
            if(integer==100)
            {
                textViewPercent.setText("Copmleted");
                start.setEnabled(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(recever,new IntentFilter(SimpleIntentService.ACTION_1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(recever);
    }
}