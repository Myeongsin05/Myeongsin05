package com.example.firebaseex1;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//어플의 첫번째 화면
//프로젝트 이름이 메인
//사용자가 처음 들어온거면 자신의 정보 입력하는 액티비티로 연결

public class AppmainActivity extends AppCompatActivity {
    Button Button_user_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appmain);

        //나의 정보 등록 버튼 클릭시 액티비티 전환
        Button_user_input = findViewById(R.id.user_input);
        Button_user_input.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//버튼을 클릭했을 때
                Intent intent = new Intent(AppmainActivity.this, EnteruserinformActivity.class);
                startActivity(intent);
            }
        });


    }

}
