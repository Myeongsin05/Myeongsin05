package com.example.firebaseex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Findtheway extends AppCompatActivity {
    TextView userNameTextView, carNumTextView, carLocationTextView;
    Button gate1_button, gate2_button;
    //화살표 이미지
    ImageView up_arrow1, up_arrow2, up_arrow4;
    ImageView down_arrow1, down_arrow3, down_arrow4;
    ImageView right_arrowA1, right_arrowA2, right_arrowD1, right_arrowD2;
    ImageView left_arrowB1, left_arrowB2, left_arrowC1, left_arrowC2;
    ImageView side_arrow1, side_arrow2, side_arrow3, side_arrow4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findtheway);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String recive_username = bundle.getString("username");
        String recive_carnum = bundle.getString("carnum");
        String recive_carlocation = bundle.getString("carlocation");

        userNameTextView = findViewById(R.id.userNameTextView);
        carNumTextView  = findViewById(R.id.carNumTextView);
        carLocationTextView = findViewById(R.id.carLocationTextView);

        userNameTextView.setText(recive_username);
        carNumTextView.setText(recive_carnum);
        carLocationTextView.setText(recive_carlocation);

        //아래 작업을 해야 in/Visible이 가능함.
        up_arrow1 = (ImageView)findViewById(R.id.up_arrow1);
        up_arrow2 = (ImageView)findViewById(R.id.up_arrow2);
        up_arrow4 = (ImageView)findViewById(R.id.up_arrow4);


        down_arrow1 = (ImageView)findViewById(R.id.down_arrow1);
        down_arrow3 = (ImageView)findViewById(R.id.down_arrow3);
        down_arrow4 = (ImageView)findViewById(R.id.down_arrow4);


        right_arrowA1 = (ImageView)findViewById(R.id.right_arrowA1);
        right_arrowA2 = (ImageView)findViewById(R.id.right_arrowA2);
        right_arrowD1 = (ImageView)findViewById(R.id.right_arrowD1);
        right_arrowD2 = (ImageView)findViewById(R.id.right_arrowD2);

        left_arrowB1 = (ImageView)findViewById(R.id.left_arrowB1);
        left_arrowB2 = (ImageView)findViewById(R.id.left_arrowB2);
        left_arrowC1 = (ImageView)findViewById(R.id.left_arrowC1);
        left_arrowC2 = (ImageView)findViewById(R.id.left_arrowC2);

        side_arrow1 = (ImageView)findViewById(R.id.side_arrow1);
        side_arrow2 = (ImageView)findViewById(R.id.side_arrow2);
        side_arrow3 = (ImageView)findViewById(R.id.side_arrow3);
        side_arrow4 = (ImageView)findViewById(R.id.side_arrow4);

        //화살표 전체 안보임
        up_arrow1.setVisibility(View.INVISIBLE);
        up_arrow2.setVisibility(View.INVISIBLE);
        up_arrow4.setVisibility(View.INVISIBLE);

        down_arrow1.setVisibility(View.INVISIBLE);
        down_arrow3.setVisibility(View.INVISIBLE);
        down_arrow4.setVisibility(View.INVISIBLE);

        right_arrowA1.setVisibility(View.INVISIBLE);
        right_arrowA2.setVisibility(View.INVISIBLE);
        right_arrowD1.setVisibility(View.INVISIBLE);
        right_arrowD2.setVisibility(View.INVISIBLE);

        left_arrowB1.setVisibility(View.INVISIBLE);
        left_arrowB2.setVisibility(View.INVISIBLE);
        left_arrowC1.setVisibility(View.INVISIBLE);
        left_arrowC2.setVisibility(View.INVISIBLE);

        side_arrow1.setVisibility(View.INVISIBLE);
        side_arrow2.setVisibility(View.INVISIBLE);
        side_arrow3.setVisibility(View.INVISIBLE);
        side_arrow4.setVisibility(View.INVISIBLE);

        gate1_button = findViewById(R.id.gate1_button);
        gate1_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //A1, Gate1일 때 보여지는 화살표
                if(recive_carlocation.equals("A1")){
                    right_arrowA1.setVisibility(View.VISIBLE);
                }

                //A2, gate1
                else if(recive_carlocation.equals("A2")){
                    down_arrow4.setVisibility(View.VISIBLE);
                    right_arrowA2.setVisibility(View.VISIBLE);
                }

                //B1, gate1
                else if(recive_carlocation.equals("B1")){
                    right_arrowA1.setVisibility(View.VISIBLE);
                    //--A1, gate1까지의 경로 & 양방향 화살표, 이하 다른 경로도 동일한 방법으로 진행
                    side_arrow1.setVisibility(View.VISIBLE);
                }

                //B2, gate1
                else if(recive_carlocation.equals("B2")){
                    down_arrow4.setVisibility(View.VISIBLE);
                    right_arrowA2.setVisibility(View.VISIBLE);
                    side_arrow2.setVisibility(View.VISIBLE);
                }

                //C1, gate1
                else if(recive_carlocation.equals("C1")){
                    down_arrow4.setVisibility(View.VISIBLE);
                    down_arrow3.setVisibility(View.VISIBLE);
                    right_arrowD1.setVisibility(View.VISIBLE);
                    side_arrow3.setVisibility(View.VISIBLE);
                }

                //C2, gate1
                else if(recive_carlocation.equals("C2")){
                    down_arrow1.setVisibility(View.VISIBLE);
                    down_arrow3.setVisibility(View.VISIBLE);
                    down_arrow4.setVisibility(View.VISIBLE);
                    right_arrowD2.setVisibility(View.VISIBLE);
                    side_arrow4.setVisibility(View.VISIBLE);
                }

                //D1, gate 1
                else if(recive_carlocation.equals("D1")){
                    down_arrow1.setVisibility(View.VISIBLE);
                    down_arrow3.setVisibility(View.VISIBLE);
                    right_arrowD1.setVisibility(View.VISIBLE);
                }

                //D2, gate1
                else if(recive_carlocation.equals("D2")){
                    down_arrow1.setVisibility(View.VISIBLE);
                    down_arrow3.setVisibility(View.VISIBLE);
                    down_arrow4.setVisibility(View.VISIBLE);
                    right_arrowD2.setVisibility(View.VISIBLE);
                }
            }
        });

        gate2_button = findViewById(R.id.gate2_button);
        gate2_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //A1, gate2
                if(recive_carlocation.equals("A1")) {
                    up_arrow1.setVisibility(View.VISIBLE);
                    up_arrow2.setVisibility(View.VISIBLE);
                    up_arrow4.setVisibility(View.VISIBLE);
                    left_arrowB1.setVisibility(View.VISIBLE);
                    side_arrow1.setVisibility(View.VISIBLE);
                }

                //A2, gate2
                else if (recive_carlocation.equals("A2")){
                    up_arrow1.setVisibility(View.VISIBLE);
                    up_arrow2.setVisibility(View.VISIBLE);
                    left_arrowB2.setVisibility(View.VISIBLE);
                    side_arrow2.setVisibility(View.VISIBLE);
                }

                //B1, gate2
                else if (recive_carlocation.equals("B1")) {
                    up_arrow1.setVisibility(View.VISIBLE);
                    up_arrow2.setVisibility(View.VISIBLE);
                    up_arrow4.setVisibility(View.VISIBLE);
                    left_arrowB1.setVisibility(View.VISIBLE);
                }

                //B2, gate2
                else if(recive_carlocation.equals("B2")){
                    up_arrow1.setVisibility(View.VISIBLE);
                    up_arrow2.setVisibility(View.VISIBLE);
                    left_arrowB2.setVisibility(View.VISIBLE);
                }

                //C1, gate2
                else if(recive_carlocation.equals("C1")){
                    up_arrow1.setVisibility(View.VISIBLE);
                    left_arrowC1.setVisibility(View.VISIBLE);
                }

                //C2, gate2
                else if(recive_carlocation.equals("C2")){
                    left_arrowC2.setVisibility(View.VISIBLE);
                }
                //D1, gate 2
                else if(recive_carlocation.equals("D1")){
                    up_arrow1.setVisibility(View.VISIBLE);
                    left_arrowC1.setVisibility(View.VISIBLE);
                    side_arrow3.setVisibility(View.VISIBLE);
                }

                //D2, gate2
                else if(recive_carlocation.equals("D2")){
                    left_arrowC2.setVisibility(View.VISIBLE);
                    side_arrow4.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
//망할 carlocation이 아니라 recieve_carlocation아님.??
