package com.example.demo;

import lombok.Data;

/**
 * ���[�U�[�f�[�^�e�[�u��(user_data)�A�N�Z�X�p�G���e�B�e�B
 */
@Data
public class UserData {

    /** ID */
    private long id;

    /** ���O */
    private String name;

    /** ���N����_�N */
    private int birthY;

    /** ���N����_�� */
    private int birthM;

    /** ���N����_�� */
    private int birthD;

    /** ���� */
    private String sex;

    /** ���� */
    private String memo;

    /** ����(������) */
    private String sex_value;

}