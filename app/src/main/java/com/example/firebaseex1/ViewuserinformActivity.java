package com.example.firebaseex1;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * ViewuserinformActivity.java
 * 1. 사용자가 이전 페이지인 EnteruserinfomActivity.java 에서 입력한 username 과 carnum 왼쪽 상단에 표기
 * 2. 서버에서 자동차의 상태 정보를 불러와서 /주차 가능  = RED, 주차 불가능 = BLUE 로 색 변경
 * 3. 사용자가 주차할 위치의 버튼을 누르면 그 버튼이 주차 불가능 색인 RED로 바뀌고 서버에 그 위치의 값 1로 변경
 * 4. 다음 페이지에서도 사용할 값들 intent에 담고 화면 전환
 *
 */
public class ViewuserinformActivity extends AppCompatActivity {
    private static final String TAG = "firebase -------------";
    /**
     * 변수 선언공간
     */

    //changeButtonColor() 에서 사용자가 자리를 클릭하면 버튼상태 변하고 서버에 값 변화주기위한 변수
    public String save_carlocation;//차위치

    //intent에서 받을 저번 화면에서 넘어온 username, carnum
    String recive_username, recive_carnum;

    //차 상태에 따라 버튼색 다르게 띄울때 사용될 각 위치의 status 변수들
    String A1status, A2status, B1status, B2status, C1status, C2status, D1status, D2status;

    Button initButton;
    Handler handler2 = new Handler(); //스레드 내부에서 쓸 수 있는 핸들러 전역으로 정의
    boolean isRun = false;

    //xml의 id와 연결시켜줄 Textview 객체와 Button 객체 이름 정의
    TextView userNameTextView, carNumTextView;
    Button saveButton, A1button, A2button, B1button, B2button, C1button, C2button, D1button, D2button;

    ImageView alarmimg;



    //firebase 객체 선언
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference database = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuserinform);
        /**
         *  intent 값 꺼내기
         *  intent로 username, carnum 를 recive_username, recive_carnum 으로 받음
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); //Bundle로 값 받은 다음에 받은거 string으로 다시꺼내기
        //추가설명 : putExtra 로 값을 넣었으니까 extras 에서 값을 가져온 뒤, 거기서 아까 정해준 key값으로 꺼내야함
        recive_username = bundle.getString("username");
        recive_carnum = bundle.getString("carnum");

        floatingUserinfo(); //왼쪽 상단에 username 과 carnum 정보를 띄움

        /** xml에서  A1~ D2 까지의 버튼들 id연결 */
        A1button = findViewById(R.id.A1button);
        A2button = findViewById(R.id.A2button);
        B1button = findViewById(R.id.B1button);
        B2button = findViewById(R.id.B2button);
        C1button = findViewById(R.id.C1button);
        C2button = findViewById(R.id.C2button);
        D1button = findViewById(R.id.D1button);
        D2button = findViewById(R.id.D2button);
        /**-----------------------------------*/
        initButton = findViewById(R.id.newStatus);//누르면 새로운 스레드 동작 시작


        /**  사용자가 버튼 클릭시 버튼의 색은 red로 바뀌고 서버에 그 부분 주차중으로 값 변경  **/
        changeButtonColor();


        /** ----------------------------*/


        /** 다음 화면으로 넘어가기 위해 인텐트로 화면 전환   */
        saveButton = findViewById(R.id.saveButton); //다음 버튼 누르면 값 담고 다음 페이지로 이동
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//버튼을 누르면 사용자 정보 전역변수로 지정, 사용자정보를 확인하는 페이지로 넘김

                Intent intent = new Intent(ViewuserinformActivity.this, Findtheway.class);
                intent.putExtra("username", recive_username);
                intent.putExtra("carnum", recive_carnum);
                intent.putExtra("carlocation", save_carlocation);
                //값을 들고있는 activity를 다음 activity로 던져야함 => startActivity(값을 담은 intent);

                startActivity(intent);
                //화면 전환 하고 받은 값 서버에 넘김
            }
        });


        alarmimg = findViewById(R.id.alarmimg);
        alarmimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewuserinformActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


    }
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//
//    }

    /**
     * 상태확인 버튼 클릭 시 주차상태에 따라 버튼 색상 변경- 3초 마다 다시 받아옴
     */
    public void mOnClick(View v) {
        //Runable객체를 implent하는방법(이것을 구현함으로써 한번 실행될 객체를 정의가능)
        //스레드를 만들고 그안에 Runnable을 집어넣는데 스레드를 클래스로 별도로 만들었을떄와 차이가 없음.
        new Thread(new Runnable() {

            final String[] a = new String[2];
            final String[] b = new String[2];
            final String[] c = new String[2];
            final String[] d = new String[2];

            @Override
            public void run() {
                isRun = true;

                //
                while ((isRun)) {
                    DatabaseReference carRef = firebaseDatabase.getReference();
                    carRef.child("Cars").child("A1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            a[0] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "A1 value is " + a[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //A2
                    carRef.child("Cars").child("A2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            a[1] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "A2 value is " + a[1]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //B1
                    carRef.child("Cars").child("B1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            b[0] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "B1 value is " + b[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //B2
                    carRef.child("Cars").child("B2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            b[1] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "B2 value is " + b[1]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //C1
                    carRef.child("Cars").child("C1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            c[0] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "C1 value is " + c[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //C2
                    carRef.child("Cars").child("C2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            c[1] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "C2 value is " + c[1]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //D1
                    carRef.child("Cars").child("D1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            d[0] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "D1 value is " + d[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //D2
                    carRef.child("Cars").child("D2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot) {
                            d[1] = datasnapshot.getValue(String.class);
                            Log.d("TAG", "D2 value is " + d[1]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    //핸들러클래스로서 post로 던질수가있음.
                    //핸들러의 post 메소드를 호출하면 Runnable 객체를 전달할 수 있습니다.
                    //핸들러로 전달된 Runnable, 객체는 메인 스레드에서 실행될 수 있으며 따라서 UI를 접근하는 코드는 Runnable 객체 안에 넣어두면 됩니다.
                    //post 메소드 이외에도 지정된 시간에 실행하는 postAtTime 메소드와 지정된 시간만큼 딜레이된 시간후 실행되는 postDelayed 메소드가 있습니다.
                    handler2.post(new Runnable() {
                        //mainthread에서 실행될 수 있는 부분
                        @Override
                        public void run() {
                            //a구역 버튼색 변경
                            A1status = a[0];
                            A2status = a[1];
                            B1status = b[0];
                            B2status = b[1];
                            C1status = c[0];
                            C2status = c[1];
                            D1status = d[0];
                            D2status = d[1];
                            if ("1".equals(a[0])) {
                                A1button.setBackgroundColor(Color.RED);
                            } else {
                                A1button.setBackgroundColor(Color.BLUE);
                            }
                            if ("1".equals(a[1])) {
                                A2button.setBackgroundColor(Color.RED);
                            } else {
                                A2button.setBackgroundColor(Color.BLUE);
                            }
                            //b구역 버튼색 변경
                            if ("1".equals(b[0])) {
                                B1button.setBackgroundColor(Color.RED);
                            } else {
                                B1button.setBackgroundColor(Color.BLUE);
                            }
                            if ("1".equals(b[1])) {
                                B2button.setBackgroundColor(Color.RED);
                            } else {
                                B2button.setBackgroundColor(Color.BLUE);
                            }
                            //c구역 버튼색 변경
                            if ("1".equals(c[0])) {
                                C1button.setBackgroundColor(Color.RED);
                            } else {
                                C1button.setBackgroundColor(Color.BLUE);
                            }
                            if ("1".equals(c[1])) {
                                C2button.setBackgroundColor(Color.RED);
                            } else {
                                C2button.setBackgroundColor(Color.BLUE);
                            }
                            //d구역 버튼색 변경
                            if ("1".equals(d[0])) {
                                D1button.setBackgroundColor(Color.RED);
                            } else {
                                D1button.setBackgroundColor(Color.BLUE);
                            }
                            if ("1".equals(d[1])) {
                                D2button.setBackgroundColor(Color.RED);
                            } else {
                                D2button.setBackgroundColor(Color.BLUE);
                            }
                        }
                    });
                    try {
                        Thread.sleep(3000);//3초 대기
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.
    }


    /**
     * 상단에 사용자 정보 띄우는 함수
     */
    public void floatingUserinfo() {
        //xml 연결
        userNameTextView = findViewById(R.id.userNameTextView);
        carNumTextView = findViewById(R.id.carNumTextView);
        //textView에 띄우기
        userNameTextView.setText(recive_username);
        carNumTextView.setText(recive_carnum);
    }


    public void changeButtonColor() {
        A1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A1status  ="1";
                A1button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "A1";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 A1 입니다", Toast.LENGTH_SHORT).show();
                //토스트메세지로 자리 위치 띄워주기
                //서버에 A1자리 값 1로 변경
                //나머지 버튼도 기능 동일
            }
        });

        A2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A2status  ="1";
                A2button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "A2";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 A2 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        B1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B1status  ="1";
                B1button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "B1";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 B1 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        B2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B2status  ="1";
                B2button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "B2";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 B2 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        C1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C1status  ="1";
                C1button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "C1";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 C1 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        C2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C2status  ="1";
                C2button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "C2";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 C2 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        D1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D1status  ="1";
                D1button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "D1";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 D1 입니다", Toast.LENGTH_SHORT).show();
            }
        });

        D2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D2status  ="1";
                D2button.setBackgroundColor(Color.RED);
                inputNewCar();
                save_carlocation = "D2";
                Toast.makeText(getApplicationContext(), "현재 주차하신 위치는 D2 입니다", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void inputNewCar(){
        HashMap<String, Object> childUpdates;
        childUpdates = new HashMap<>();
        SaveCarInfo saveCarInfo = new SaveCarInfo(A1status, A2status, B1status, B2status, C1status, C2status, D1status, D2status);

        Map<String, Object> carValue = saveCarInfo.toMap();

        childUpdates.put("/Cars/", carValue);
        database.updateChildren(childUpdates);

        Log.d(TAG , "save_location is : "+ save_carlocation);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun = false;
    }

}

