package com.inerxia.expensemateapi.utils;

import com.inerxia.expensemateapi.exceptions.RequiredException;

import java.util.Objects;

public class CustomUtilService {

    public static void ValidateRequired(Object object){
        if(Objects.isNull(object)){
            throw new RequiredException(MessageResponse.MISSING_REQUIRED_FIELD);
        }
    }
}
