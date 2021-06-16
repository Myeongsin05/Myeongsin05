package com.example.firebaseex1;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SaveCarInfo {
    public String A1;
    public String A2;
    public String B1;
    public String B2;
    public String C1;
    public String C2;
    public String D1;
    public String D2;

    public SaveCarInfo(){
        //datasnapshot사용하기 위한 클래스 인수를 사용하지 않는 기본생성자
        //public으로 변수를 접근할 수 있게 설정되어있음
        //Default constructor required for calls to DataSnapshot.getValue(SaveStatusInfo.class)
    }
    public SaveCarInfo(String a1, String a2, String b1,String b2,String c1,String c2,String d1,String d2){

        this.A1 = a1;
        this.A2 = a2;

        this.B1 = b1;
        this.B2 = b2;

        this.C1 = c1;
        this.C2 = c2;

        this.D1 = d1;
        this.D2 = d2;

    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("A1", A1);
        result.put("A2", A2);
        result.put("B1", B1);
        result.put("B2", B2);
        result.put("C1", C1);
        result.put("C2", C2);
        result.put("D1", D1);
        result.put("D2", D2);

        return result;
    }


}
