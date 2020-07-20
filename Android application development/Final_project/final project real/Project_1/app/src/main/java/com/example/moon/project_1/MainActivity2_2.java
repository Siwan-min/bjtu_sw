package com.example.moon.project_1;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2_2 extends AppCompatActivity {
    int count;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    ImageView image1,image2,image3,image4,image5,image6,image7;
    ImageButton btn8,btn9,btn10;
    TextView text;
    Chronometer chr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_2);
        btn1=(Button)findViewById(R.id.button4); btn2=(Button)findViewById(R.id.button5); btn3=(Button)findViewById(R.id.button6); btn4=(Button)findViewById(R.id.button7);
        btn5=(Button)findViewById(R.id.button8); btn6=(Button)findViewById(R.id.button9); btn7=(Button)findViewById(R.id.button10);
        btn8=(ImageButton)findViewById(R.id.imageButton2); btn9=(ImageButton)findViewById(R.id.imageButton3); btn10=(ImageButton)findViewById(R.id.imageButton4);
        image1=(ImageView)findViewById(R.id.squat); image2=(ImageView)findViewById(R.id.lunge); image3=(ImageView)findViewById(R.id.Plank);
        image4=(ImageView)findViewById(R.id.dead); image5=(ImageView)findViewById(R.id.side); image6=(ImageView)findViewById(R.id.crunch);
        image7=(ImageView)findViewById(R.id.burpee); text =(TextView)findViewById(R.id.textView4);
        chr=(Chronometer)findViewById(R.id.chronometer2);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.VISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("SQUANT");
                Toast.makeText(getApplicationContext(), "Lower body workout", Toast.LENGTH_SHORT).show();


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.VISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("LUNGE");
                Toast.makeText(getApplicationContext(),"Lower body workout",Toast.LENGTH_SHORT ).show();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.VISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("PLANK");
                Toast.makeText(getApplicationContext(),"Full-body core workout",Toast.LENGTH_SHORT ).show();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.VISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("DEADLIFT");
                Toast.makeText(getApplicationContext(),"Waist workout",Toast.LENGTH_SHORT ).show();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.VISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("SIDE BEND");
                Toast.makeText(getApplicationContext(),"Side workout",Toast.LENGTH_SHORT ).show();

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.VISIBLE); image7.setVisibility(View.INVISIBLE); text.setText("CRUNCH");
                Toast.makeText(getApplicationContext(),"Abs workout",Toast.LENGTH_SHORT ).show();

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image1.setVisibility(View.INVISIBLE); image2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE); image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.INVISIBLE); image6.setVisibility(View.INVISIBLE); image7.setVisibility(View.VISIBLE); text.setText("BURPEES");
                Toast.makeText(getApplicationContext(),"Go to hell!",Toast.LENGTH_SHORT ).show();

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count++;
                if(count==1) {
                    chr.setBase(SystemClock.elapsedRealtime());
                }
                chr.start();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chr.stop();
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chr.setBase(SystemClock.elapsedRealtime());
            }
        });






    }
}
