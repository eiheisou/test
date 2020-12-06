package com.example.demo;

import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateCheckUtil {

    /** ���t�̃t�H�[�}�b�g */
    private final static String dateFormat = "uuuuMMdd";

    /**
     * ���t�`�F�b�N�������s��
     * @param year�@�N
     * @param month ��
     * @param day ��
     * @return ���茋��(1:�N����A2:������A3:������A4:�N�������s���A5:�N�������������A0:����)
     */
    public static int checkDate(String year, String month, String day){
        if(isEmpty(year)){
            return 1;
        }
        if(isEmpty(month)){
            return 2;
        }
        if(isEmpty(day)){
            return 3;
        }
        String dateStr = year + addZero(month) + addZero(day);
        if(!isCorrectDate(dateStr, dateFormat)){
            return 4;
        }
        if(isFutureDate(dateStr, dateFormat)){
            return 5;
        }
        return 0;
    }

    /**
     * �����pForm�I�u�W�F�N�g�̃`�F�b�N�����s��
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @return ���茋��(1:���N����_from���s���A2:���N����_to���s���A3:���N����_from�����N����_to�A0:����)
     */
    public static int checkSearchForm(SearchForm searchForm){
        //���N����_from���s���ȏꍇ
        if(!checkSearchFormBirthday(searchForm.getFromBirthYear()
                , searchForm.getFromBirthMonth(), searchForm.getFromBirthDay())){
            return 1;
        }
        //���N����_to���s���ȏꍇ
        if(!checkSearchFormBirthday(searchForm.getToBirthYear()
                , searchForm.getToBirthMonth(), searchForm.getToBirthDay())){
            return 2;
        }
        //���N����_from�����N����_to�̏ꍇ
        if(!isEmpty(searchForm.getFromBirthYear()) && !isEmpty(searchForm.getToBirthYear())){
            String fromBirthDay = searchForm.getFromBirthYear()
                    + addZero(searchForm.getFromBirthMonth()) + addZero(searchForm.getFromBirthDay());
            String toBirthDay = searchForm.getToBirthYear()
                    + addZero(searchForm.getToBirthMonth()) + addZero(searchForm.getToBirthDay());
            if(fromBirthDay.compareTo(toBirthDay) > 0){
                return 3;
            }
        }
        //����ȏꍇ
        return 0;
    }

    /**
     * �����pForm���̓��t���`�F�b�N����
     * @param year �N
     * @param month ��
     * @param day ��
     * @return ���t�`�F�b�N����
     */
    private static boolean checkSearchFormBirthday(String year, String month, String day){
        //�N�E���E�����S�Ė��w��̏ꍇ�̓`�F�b�NOK�Ƃ���
        if(isEmpty(year) && isEmpty(month) && isEmpty(day)){
            return true;
        }
        //�N�E���E�����S�Ďw�肳��Ă���ꍇ�́A���t���������ꍇ�Ƀ`�F�b�NOK�Ƃ���
        if(!isEmpty(year) && !isEmpty(month) && !isEmpty(day)){
            String dateStr = year + addZero(month) + addZero(day);
            if(isCorrectDate(dateStr, dateFormat)){
                return true;
            }
            return false;
        }
        //�N�E���E�����w�肠��/�w��Ȃ��ō��݂��Ă���ꍇ�̓`�F�b�NNG�Ƃ���
        return false;
    }

    /**
     * DateTimeFormatter�𗘗p���ē��t�`�F�b�N���s��
     * @param dateStr �`�F�b�N�Ώە�����
     * @param dateFormat ���t�t�H�[�}�b�g
     * @return ���t�`�F�b�N����
     */
    private static boolean isCorrectDate(String dateStr, String dateFormat){
        if(isEmpty(dateStr) || isEmpty(dateFormat)){
            return false;
        }
        //���t�Ǝ����������ɉ�������X�^�C���ŁADateTimeFormatter�I�u�W�F�N�g���쐬
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try{
            //�`�F�b�N�Ώە������LocalDate�^�̓��t�ɕϊ��ł���΁A�`�F�b�NOK�Ƃ���
            LocalDate.parse(dateStr, df);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * ���t�̕����񂪖��������ǂ����𔻒肷��
     * @param dateStr �`�F�b�N�Ώە�����
     * @param dateFormat ���t�t�H�[�}�b�g
     * @return ���茋��
     */
    private static boolean isFutureDate(String dateStr, String dateFormat){
        if(!isCorrectDate(dateStr, dateFormat)){
            return false;
        }
        LocalDate dateStrDate = convertStrToLocalDate(dateStr, dateFormat);
        LocalDate now = LocalDate.now();
        if(dateStrDate.isAfter(now)){
            return true;
        }
        return false;
    }

    /**
     * ���t�̕��������t�^�ɕϊ��������ʂ�Ԃ�
     * @param dateStr ���t�̕�����
     * @param dateFormat ���t�̃t�H�[�}�b�g
     * @return �ϊ���̕�����
     */
    private static LocalDate convertStrToLocalDate(String dateStr, String dateFormat){
        if(isEmpty(dateStr) || isEmpty(dateFormat)){
            return null;
        }
        //���t�Ǝ����������ɉ�������X�^�C���ŁA��̌n�͘a��̌n�ŁADateTimeFormatter�I�u�W�F�N�g���쐬
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat, Locale.JAPAN)
                .withChronology(JapaneseChronology.INSTANCE).withResolverStyle(ResolverStyle.STRICT);
        //���t�̕������LocalDate�^�ɕϊ����ĕԋp
        return LocalDate.parse(dateStr, df);
    }

    /**
     * ���l������1���̏ꍇ�A����0��t���ĕԂ�
     * @param intNum ���l������
     * @return �ϊ��㐔�l������
     */
    private static String addZero(String intNum){
        if(isEmpty(intNum)){
            return intNum;
        }
        if(intNum.length() == 1){
            return "0" + intNum;
        }
        return intNum;
    }

    /**
     * �����̕�����null�A�󕶎����ǂ����𔻒肷��
     * @param str �`�F�b�N�Ώە�����
     * @return ������`�F�b�N����
     */
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }
        return false;
    }
}