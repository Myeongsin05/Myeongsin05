package com.example.firebaseex1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * 알람 기능 설명
 * 처음 차 상태를 보고 만약 주차할 위치가 없으면 알람 실행 버튼 클릭
 * 계속 5초마다 동기화가 되거나 상태가 변하면 알람으로 현재 주차장의 상태를 알려줌
 * 여기서 뒤로가기 버튼을 누르면 다시 자신이 주차할 위치를 선택할 수 있는 화면이 나옴
 *
 */
public class AlarmActivity extends AppCompatActivity {
    public Button button2;
    String A1status, A2status, B1status, B2status, C1status, C2status, D1status, D2status;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    String a1text, a2text,b1text, b2text,c1text, c2text,d1text, d2text;

    String alarm_text;
    private static final String TAG = "AlarmActivity---";
    Handler handler2 = new Handler();

    boolean isRun = false;

    //서비스할 메세지

    String msg[] = {"A1자리가 비었습니다.", "A2자리가 비었습니다.",
            "B1자리가 비었습니다.", "B2자리가 비었습니다.",
            "C1자리가 비었습니다.", "C2자리가 비었습니다.",
            "D1자리가 비었습니다.", "D2자리가 비었습니다."
    };
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    //알람 시작 버튼과 취소 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        button2 = findViewById(R.id.button2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "알림 시작", Toast.LENGTH_SHORT).show();
                check();
            }
        });


    }



    public void check() {
        new Thread(new Runnable() {
            //boolean isRun = false;
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
                            //msg index 설정
                            A1status = a[0];
                            A2status = a[1];
                            B1status = b[0];
                            B2status = b[1];
                            C1status = c[0];
                            C2status = c[1];
                            D1status = d[0];
                            D2status = d[1];

                            //빈자리 텍스트 변경
                            if ("0".equals(a[0])) {
                                //alarm_text_index_num= new Integer(A1status);
                                a1text = "a1 주차가능\n";

                            }else{
                                a1text = " ";
                            }
                            if ("0".equals(a[1])) {
                                a2text = "a2 주차가능\n";

                            }else{
                                a2text = " ";
                            }
                            //b구역
                            if ("0".equals(b[0])) {
                                b1text = "b1 주차가능\n";

                            }else{
                                b1text = " ";
                            }
                            if ("0".equals(b[1])) {
                                b2text = "b2 주차가능\n";
                            }else{
                                b2text = " ";
                            }
                            //c구역
                            if ("0".equals(c[0])) {
                                c1text = "c1 주차가능\n";
                            }else{
                                c1text = " ";
                            }
                            if ("0".equals(c[1])) {
                                c2text = "c2 주차가능\n";
                            }else{
                                c2text = " ";
                            }
                            //d구역
                            if ("0".equals(d[0])) {
                                d1text = "d1 주차가능\n";
                            }else{
                                d1text = " ";
                            }
                            if ("0".equals(d[1])) {
                                d2text = "d2 주차가능\n";
                            }else{
                                d2text = " ";
                            }


                            alarm();


                        }
                    });
                    try {
                        Thread.sleep(5000);//5초 대기
                    } catch (Exception e) {
                    }

                }
            }
        }).start(); //start()붙이면 바로실행시킨다.
    }

    //알람 기능 넣을 함수
    public void alarm() {
        alarm_text = a1text+a2text+b1text+b2text+c1text+c2text+d1text+d2text;
        if (alarm_text.equals("        ")){
            alarm_text="주차가능한자리 없음";
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.putExtra("notificationId", alarm_text); //전달할 값
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)) //BitMap 이미지 요구
                //.setContentTitle("PLA")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(alarm_text))
                .setContentText(alarm_text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 클릭 시 MainActivity로 이동하도록 설정
                .setAutoCancel(true);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            CharSequence channelName = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        } else
            builder.setSmallIcon(R.mipmap.ic_launcher);

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun = false;
    }
}
