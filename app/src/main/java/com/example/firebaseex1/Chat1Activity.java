package com.example.firebaseex1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


//이용 x 코드 참고만

public class Chat1Activity extends AppCompatActivity {
    private static final String TAG = "Chat1Activity";
    //databaseReference 가져오기
    private DatabaseReference testDatabase, messageDatabase;

    //전역변수로 사용할 거 가져오기- 컴포넌트종류 변수명;
    //변수를 만들어 놓고 onCreate()에서 activity_chat1.xml을 실행하면
    //id를 변수에 할당해서 사용하게 함
    //이 변수를 이용해서 어떤 action을 취하면 지역변수로 가져다가 활용
    //EditText_username 은 사용자 이름 입력받는 변수
    //EditText_carnum 은 차 넘버 입력받는 변수
    //Button_button 은 사용자가 다 입력하고 등록하게하는 버튼
    //변수명 이렇게 하는 이유는 getText()하고 입력한 값 넘겨받을 변수를 원래 username으로 사용하기 위함
    EditText EditText_username, EditText_carnum;
    Button Button_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);

        //전역변수로 선언한 변수명에 activity에 있는 id 대입해서 연결해주기
        EditText_username = findViewById(R.id.username);
        EditText_carnum   = findViewById(R.id.carnum);
        Button_button   = findViewById(R.id.button);

        //1. 값을 가져온다 .getText().toString()
        //2. 클릭을 감지 .setClick~
        //3. 값을 다른 액티비티로 넘긴다(화면 이동)

        //클릭을 감지하려면 감지할 버튼변수.setClick~ 에서 setClickable: 클릭 가능여부 설정, setOnClickListener : 클릭 리스너 설정
        Button_button.setClickable(true); //버튼 클릭 가능하게 함
        Button_button.setOnClickListener(new View.OnClickListener() { //클릭 리스너를 달건데 그 리스너는 밑에 있음
            @Override
            public void onClick(View v) { //클릭했을 때 일어날 action을 넣어줌
                //이 동작이 실행되는 때는 사용자가 editText 에 값을 넣은 다음에 버튼을 누르면 값을 가져오게 하면 됨
                //사용자가 입력한 값을 가져오는 함수 이름은 get 또는 set
                String username = EditText_username.getText().toString(); //사용자가 입력한 값 가져와서 문자열로 변환
                String carnum = EditText_carnum.getText().toString();

                //##### 다른 액티비티로 값을 넘겨주는 거는 Intent를 사용함
                Intent intent = new Intent(Chat1Activity.this, ResultActivity.class);
                //새로운 인텐트를 intent로 만들고 Chat1Activity.this(현재 액티비티) > ResultActivity.class(이동할 액티비티) 로 보내줌줌                intent.putExtra("username", username);              // intent.putExtra() : 값 넣기, putExtra(key, value) : 값을 찾아올때 쓸 이름 = key, 값 = value
                intent.putExtra("username", username);
                intent.putExtra("carnum", carnum);
                //값을 들고있는 activity를 다음 activity로 던져야함 => startActivity(값을 담은 intent);
                startActivity(intent);
            }
        });

        //setValueTest()

    }

    //firebase 관련 코드
//firebase 에서 값을 .setValue 로 넘김
    public void setValueTest(){ //db레퍼런스 변수명.setValue() 하면 지정된 패스 밑 다바뀜
        testDatabase = FirebaseDatabase.getInstance().getReference("test");//key
        testDatabase.setValue("3"); //value

        messageDatabase = FirebaseDatabase.getInstance().getReference("message");
        messageDatabase.setValue("test3");
        // 지정된 곳에 test에 데이터를 저장하고 해당 경로의 기존 데이터를 모두 바꿈

    }
//    @IgnoreExtraProperties
//    public class User {

//        public String username;
  //      public String email;
//
  //      public User() {
    //        // Default constructor required for calls to DataSnapshot.getValue(User.class)
      //  }
//
  //      public User(String username, String email) {
    //        this.username = username;
      //      this.email = email;
        //}
//
  //  }

  //  public void writeNewUser(String userId, String name, String email) {
    //    User user = new User(name, email);
//
  //      testDatabase.child("users").child(userId).setValue(user);
    //}


    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("data test 1");

        // [END write_message]

        // [start read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}