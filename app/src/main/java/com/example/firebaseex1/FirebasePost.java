package com.example.firebaseex1;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;



//데이터 구조에서 각 id 별로 가지고 있는 데이터를 읽거나 저장할 때 사용하는 용도로 클래스를 생성합니다. 데이터를 잠시 담아두는 역할을 수행합니다.

@IgnoreExtraProperties
public class FirebasePost {
    public String id;
    public String name;
    public String carnum;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String id, String name,String carnum) {
        this.id = id;
        this.name = name;
        this.carnum = carnum;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("carnum", carnum);
        return result;
    }
}