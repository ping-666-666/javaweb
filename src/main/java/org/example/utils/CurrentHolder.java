package org.example.utils;

public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL=  new  ThreadLocal<>();

    public static void setCurrentEmpId(Integer id){
        CURRENT_LOCAL.set(id);
    }

    public static Integer getCurrentEmpId(){
        return CURRENT_LOCAL.get();
    }

    public static void remove(){
        CURRENT_LOCAL.remove();
    }
}
