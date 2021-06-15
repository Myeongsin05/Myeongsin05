package com.example.firebaseex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/***********************************************
EnteruserinformActivity.java
1.이름과 차번호를 입력
2.save버튼 클릭
3.서버 전송 후 intent이용 다음 액티비티로 값 전달

**********************************************/



//사용자 정보를 담아서 서버에 등록
//사용자 이름 editText id : username
//차 넘버 editText id : carnum
//등록버튼 button id : enterinform


public class EnteruserinformActivity extends AppCompatActivity {
    //user id 만들기 위한 코드
    //string과 int를 더하면 string이 되기 때문에 db에 넣을 수 있음

    //firebase 객체 생성
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database = firebaseDatabase.getReference();

    EditText EditText_username, EditText_carnum;

    Button Button_enterinformbutton;
    String enterusername, entercarnum, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enteruserinform);

        EditText_username = findViewById(R.id.username);
        EditText_carnum = findViewById(R.id.carnum);

        Button_enterinformbutton = findViewById(R.id.enterinform);
        Button_enterinformbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//버튼을 누르면 사용자 정보 전역변수로 지정, 사용자정보를 확인하는 페이지로 넘김

                enterusername = EditText_username.getText().toString(); //사용자가 입력한 값 가져와서 문자열로 변환
                entercarnum = EditText_carnum.getText().toString();

                //버튼 누르면 값 받아와서 서버로 넘김
                writeNewUser();

                Intent intent = new Intent(EnteruserinformActivity.this, ViewuserinformActivity.class);

                intent.putExtra("username", enterusername);
                intent.putExtra("carnum", entercarnum);
                intent.putExtra("userid", userid);
                //값을 들고있는 activity를 다음 activity로 던져야함 => startActivity(값을 담은 intent);

                startActivity(intent);
                //화면 전환 하고 받은 값 서버에 넘김
            }
        });



    }

     /***
      *  서버에 값 전송하는 함수 --> 재사용!
      *
      */
//주차 여부 아두이노쪽 정보 넘어오면 값 추가
    //주차 상태를 db에 저장하고 변동되는 값을 listener로 불러와서 동작 연결하기
    public void writeNewUser(){//firebase에 사용자 정보 넣기
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(enterusername);
        userInfo.setCarnum(entercarnum);

        //Integer userId = ID ;

        //database.child("users").child(String.valueOf(userId)).setValue(userInfo);
        userid = database.child("users").push().getKey();
        database.child("users").child(userid).setValue(userInfo);

        //ID++;
    }
}
