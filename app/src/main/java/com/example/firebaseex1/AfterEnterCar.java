package com.example.firebaseex1;



import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AfterEnterCar extends AppCompatActivity {


    TextView userNameTextView,carNumTextView,carLocationTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterentercar);

        Intent intent = getIntent();
        //Bundle로 값 받은 다음에 받은거 string으로 다시꺼내기
        Bundle bundle = intent.getExtras();
        //추가설명 : putExtra 로 값을 넣었으니까 extras 에서 값을 가져온 뒤, 거기서 아까 정해준 key값으로 꺼내야함
        String recive_username = bundle.getString("username");
        String recive_carnum = bundle.getString("carnum");
        String recive_carlocation = bundle.getString("carlocation");

        userNameTextView = findViewById(R.id.userNameTextView);
        carNumTextView = findViewById(R.id.carNumTextView);
        carLocationTextView = findViewById(R.id.carLocationTextView);

        userNameTextView.setText(recive_username);
        carNumTextView.setText(recive_carnum);
        carLocationTextView.setText(recive_carlocation);
    }

}
