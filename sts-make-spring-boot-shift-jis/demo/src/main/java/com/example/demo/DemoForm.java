package com.example.demo;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form�I�u�W�F�N�g�̃N���X
 */
@Data
public class DemoForm {
	
    /** ID */
    private String id;

    /** ���O */
    @NotEmpty
    private String name;

    /** ���N����_�N */
    private String birthYear;

    /** ���N����_�� */
    private String birthMonth;

    /** ���N����_�� */
    private String birthDay;

    /** ���� */
    @NotEmpty
    private String sex;

    /** ���� */
    private String memo;

    /** �m�F�`�F�b�N */
    @NotEmpty
    private String checked;

    /** ����(������) */
    private String sex_value;

    /** ���N����_����Map�I�u�W�F�N�g */
    public Map<String,String> getMonthItems(){
        Map<String, String> monthMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 12; i++){
            monthMap.put(String.valueOf(i), String.valueOf(i));
        }
        return monthMap;
    }

    /** ���N����_����Map�I�u�W�F�N�g */
    public Map<String,String> getDayItems(){
        Map<String, String> dayMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 31; i++){
            dayMap.put(String.valueOf(i), String.valueOf(i));
        }
        return dayMap;
    }

    /** ���ʂ�Map�I�u�W�F�N�g */
    public Map<String,String> getSexItems(){
        Map<String, String> sexMap = new LinkedHashMap<String, String>();
        sexMap.put("1", "�j");
        sexMap.put("2", "��");
        return sexMap;
    }

}