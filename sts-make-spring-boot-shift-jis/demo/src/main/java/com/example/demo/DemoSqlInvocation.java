package com.example.demo;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Properties;

//select, update, insert, delete�̊eSQL�����s����^�C�~���O�ŁA
//���̃N���X��intercept���\�b�h���Ă΂��悤�ɁA
//@Intercepts�E@Signature�̃A�m�e�[�V���������L�̂悤�ɐݒ�
@Intercepts({
        @Signature(type = Executor.class
                , method = "update"
                , args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class
                , method = "query"
                , args = {MappedStatement.class, Object.class
                        , RowBounds.class, ResultHandler.class})
})
@Component
public class DemoSqlInvocation implements Interceptor {

    //���O�o�͂̂��߂̃N���X
    private Logger logger = LogManager.getLogger(DemoSqlInvocation.class);

    //SQL�Ƀ}�b�s���O�����p�����[�^���ɑΉ�����p�����[�^�l���擾���邽�߂�
    //JexlEngine�I�u�W�F�N�g�𗘗p
    private JexlEngine engine = new JexlBuilder().create();

    /**
     * SQL���s�O��ɏ�����ǉ�����
     *
     * @param invocation�@���\�b�h�ďo�p�I�u�W�F�N�g
     * @return SQL���s���\�b�h�̖߂�l
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //�ԋp�I�u�W�F�N�g���`
        Object returnObj = null;

        //SQL���s���\�b�h�����s���A���̊J�n�E�I���������擾
        long start = System.currentTimeMillis();
        returnObj = invocation.proceed();
        long end = System.currentTimeMillis();

        //SQL���s���ԁA���s���\�b�h��(�t���p�X)���o��
        MappedStatement statement = (MappedStatement)invocation.getArgs()[0];
        String[] items = statement.getId().split("\\.");
        logger.info("���s����: {} ms. ���s���\�b�h: {}"
                , end-start, String.join(".", items));

        //SQL���O���o��
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = statement.getBoundSql(parameter);
        logger.info("Preparing: {}", shapingSql(boundSql.getSql()));
        logger.info("Parameters: {}", getParameterValues(boundSql));

        return returnObj;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * ������SQL������A���s�R�[�h(LF)�ƘA������󔒕���������
     * @param beforeSql SQL��
     * @return ���`���SQL��
     */
    private String shapingSql(String beforeSql){
        if(DateCheckUtil.isEmpty(beforeSql)){
            return beforeSql;
        }
        return beforeSql.replaceAll("\n", "")
                .replaceAll(" {2,}", " ");
    }

    /**
     * ������BoundSql����ASQL�ɖ��ߍ��܂��p�����[�^�l���擾����
     * @param boundSql BoundSql�I�u�W�F�N�g
     * @return SQL�ɖ��ߍ��܂��p�����[�^�l
     */
    private String getParameterValues(final BoundSql boundSql) {
        //�ԋp�p�߂�l���i�[
        StringBuilder builder = new StringBuilder();
        //�p�����[�^�l���擾
        Object param = boundSql.getParameterObject();
        if(param == null){
            //�p�����[�^�̐ݒ肪�����ꍇ�́A(No Params.)��ԋp
            builder.append("(No Params.)");
            return builder.toString();
        }
        //SQL�Ƀ}�b�s���O�����p�����[�^�����擾
        List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
        //SQL�Ƀ}�b�s���O�����p�����[�^���ɑΉ�����p�����[�^�l���擾���A
        //�ԋp�p�߂�l�ɏ����i�[����
        for (ParameterMapping mapping : paramMapping) {
            String propValue = mapping.getProperty();
            builder.append(propValue + "=");
            try {
                //SQL�Ƀ}�b�s���O�����p�����[�^���ɑΉ�����p�����[�^�l���擾
                builder.append(engine.getProperty(param, propValue));
                builder.append(", ");
            } catch (JexlException e) {
                //�p�����[�^�l��Long�^�̏ꍇ����JexlException���������邽��
                //�p�����[�^�l�����̂܂܎w�肷��
                builder.append(param);
                builder.append(", ");
                continue;
            }
        }
        //�ԋp�p�߂�l�̍Ō�̃J���}������
        if(builder.lastIndexOf(", ") > 0){
            builder.deleteCharAt(builder.lastIndexOf(", "));
        }
        return builder.toString();
    }

}