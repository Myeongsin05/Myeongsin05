package com.example.firebaseex1;

import com.google.android.gms.auth.api.signin.internal.HashAccumulator;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

public class CarStatus {
    public String carlocation; //차위치는 string으로 넣고
    public String carstatus; //주차 상태는 0 과 1로 표현

    public CarStatus(){
        //default constructor
    }
    public CarStatus(String carlocation, String carstatus){
        this.carlocation=carlocation;
        this.carstatus=carstatus;
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("carlocation", carlocation);
        result.put("carstatus", carstatus);

        return result;
    }

}

