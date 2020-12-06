package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoInvocation {

    //���O�o�͂̂��߂̃N���X
    private Logger logger = LogManager.getLogger(DemoInvocation.class);

    /**
     * Before�A�m�e�[�V�����ɂ��A�w�肵�����\�b�h�̑O�ɏ�����ǉ�����
     * Before�A�m�e�[�V�����̈����ɂ́APointcut�� execution(�߂�l �p�b�P�[�W.�N���X.���\�b�h(����))��
     * �w�肵�A�����ł�Controller�N���X�̑S���\�b�h�̎��s�O�Ƀ��O�o�͂���悤�ɂ��Ă���
     *
     * @param jp ���f�I�ȏ�����}������ꏊ
     */
    @Before("execution(public String com.example.demo.*Controller.*(..))")
    public void startLog(JoinPoint jp){
        //�J�n���O���o��
        String signature = jp.getSignature().toString();
        logger.info("�J�n���O : " + signature);
    }

    /**
     * After�A�m�e�[�V�����ɂ��A�w�肵�����\�b�h�̑O�ɏ�����ǉ�����
     * After�A�m�e�[�V�����̈����ɂ́APointcut�����w��
     *
     * @param jp ���f�I�ȏ�����}������ꏊ
     */
    @After("execution(public String com.example.demo.*Controller.*(..))")
    public void endLog(JoinPoint jp){
        //�I�����O���o��
        String signature = jp.getSignature().toString();
        logger.info("�I�����O : " + signature);
    }

}
