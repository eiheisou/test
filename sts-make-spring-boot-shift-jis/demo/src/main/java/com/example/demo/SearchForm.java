package com.example.demo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form�I�u�W�F�N�g�̃N���X
 */
@Data
public class SearchForm {

	/** �����p���O */
    private String searchName;

    /** ���N����_�N_from */
    private String fromBirthYear;

    /** ���N����_��_from */
    private String fromBirthMonth;

    /** ���N����_��_from */
    private String fromBirthDay;

    /** ���N����_�N_to */
    private String toBirthYear;

    /** ���N����_��_to */
    private String toBirthMonth;

    /** ���N����_��_to */
    private String toBirthDay;

    /** �����p���� */
    private String searchSex;

    /** �ꗗ��ʂ̌��݃y�[�W�� */
    private int currentPageNum;

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