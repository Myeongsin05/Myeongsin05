package com.example.firebaseex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ResultActivity extends AppCompatActivity {
    private DatabaseReference database;
    //전역변수 선언
    TextView TextView_viewUsername, TextView_viewCarnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //xml의 id와 연결
        TextView_viewUsername = findViewById(R.id.viewUsername);
        TextView_viewCarnum   = findViewById(R.id.viewCarnum);

        //값을 받을 intent 만들기 : getIntent();
        Intent intent = getIntent();
        //Bundle로 값 받은 다음에 받은거 string으로 다시꺼내기
        Bundle bundle = intent.getExtras();
        //추가설명 : putExtra 로 값을 넣었으니까 extras 에서 값을 가져온 뒤, 거기서 아까 정해준 key값으로 꺼내야함
        String recive_username = bundle.getString("username");
        String recive_carnum = bundle.getString("carnum");

        //받아온 값을 화면에서 확인하게 함
        TextView_viewUsername.setText("입력하신 사용자 이름은 " + recive_username + " 입니다");
        TextView_viewCarnum.setText("입력하신 차 번호는 " + recive_carnum + " 입니다");

        setValueTest();
    }

    //firebase 에서 값을 .setValue 로 넘김
    public void setValueTest(){ //db레퍼런스 변수명.setValue() 하면 지정된 패스 밑 다바뀜
        database = FirebaseDatabase.getInstance().getReference();//key
        database.child("users").child("username").setValue("34567890"); //value
        database.child("users").child("carnum").setValue("34567"); //value


    }
}