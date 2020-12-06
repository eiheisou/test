package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService{

    /**
     * ���[�U�[�f�[�^�e�[�u��(user_data)�փA�N�Z�X����}�b�p�[
     */
    @Autowired
    private UserDataMapper mapper;

    /**
     * 1�y�[�W�ɕ\������s��(application.properties����擾)
     */
    @Value("${demo.list.pageSize}")
    private String listPageSize;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemoForm> demoFormList(SearchForm searchForm, Pageable pageable) {
        List<DemoForm> demoFormList = new ArrayList<>();
        //���[�U�[�f�[�^�e�[�u��(user_data)���猟�������ɍ����f�[�^���擾����
        Collection<UserData> userDataList = mapper.findBySearchForm(searchForm, pageable);
        for (UserData userData : userDataList) {
            demoFormList.add(getDemoForm(userData));
        }
        return demoFormList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemoForm findById(String id) {
        Long longId = stringToLong(id);
        UserData userData = mapper.findById(longId);
        return getDemoForm(userData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(String id){
        Long longId = stringToLong(id);
        mapper.deleteById(longId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void createOrUpdate(DemoForm demoForm){
        //�X�V�E�ǉ��������s���G���e�B�e�B�𐶐�
        UserData userData = getUserData(demoForm);
        //�ǉ��E�X�V����
        if(demoForm.getId() == null){
            userData.setId(mapper.findMaxId() + 1);
            mapper.create(userData);
        }else{
            mapper.update(userData);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkForm(DemoForm demoForm, BindingResult result, String normalPath){
        //form�I�u�W�F�N�g�̃`�F�b�N�������s��
        if(result.hasErrors()){
            //�G���[������ꍇ�́A���͉�ʂ̂܂܂Ƃ���
            return "input";
        }
        //���N�����̓��t�`�F�b�N�������s��
        //�G���[������ꍇ�́A�G���[���b�Z�[�W�E�G���[�t�B�[���h�̐ݒ���s���A
        //���͉�ʂ̂܂܂Ƃ���
        int checkDate = DateCheckUtil.checkDate(demoForm.getBirthYear()
                , demoForm.getBirthMonth(), demoForm.getBirthDay());
        switch(checkDate){
            case 1:
                //���N����_�N���󕶎��̏ꍇ�̃G���[����
                result.rejectValue("birthYear", "validation.date-empty"
                        , new String[]{"���N����_�N"}, "");
                return "input";
            case 2:
                //���N����_�����󕶎��̏ꍇ�̃G���[����
                result.rejectValue("birthMonth", "validation.date-empty"
                        , new String[]{"���N����_��"}, "");
                return "input";
            case 3:
                //���N����_�����󕶎��̏ꍇ�̃G���[����
                result.rejectValue("birthDay", "validation.date-empty"
                        , new String[]{"���N����_��"}, "");
                return "input";
            case 4:
                //���N�����̓��t���s���ȏꍇ�̃G���[����
                result.rejectValue("birthYear", "validation.date-invalidate");
                //���N����_���E���N����_���́A�G���[�t�B�[���h�̐ݒ���s���A
                //���b�Z�[�W���󕶎��ɐݒ肵�Ă���
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            case 5:
                //���N�����̓��t���������̏ꍇ�̃G���[����
                result.rejectValue("birthYear", "validation.date-future");
                //���N����_���E���N����_���́A�G���[�t�B�[���h�̐ݒ���s���A
                //���b�Z�[�W���󕶎��ɐݒ肵�Ă���
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            default:
                //���ʂ��s���ɏ����������Ă��Ȃ����`�F�b�N����
                if(!demoForm.getSexItems().keySet().contains(demoForm.getSex())){
                    result.rejectValue("sex", "validation.sex-invalidate");
                    return "input";
                }
                //�G���[�`�F�b�N�ɖ�肪�����̂ŁA���펞�̉�ʑJ�ڐ�ɑJ��
                return normalPath;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkSearchForm(SearchForm searchForm, BindingResult result){
        int checkDate =DateCheckUtil.checkSearchForm(searchForm);
        switch (checkDate){
            case 1:
                //���N����_from���s���ȏꍇ�̃G���[����
                result.rejectValue("fromBirthYear", "validation.date-invalidate-from");
                result.rejectValue("fromBirthMonth", "validation.empty-msg");
                result.rejectValue("fromBirthDay", "validation.empty-msg");
                return "search";
            case 2:
                //���N����_to���s���ȏꍇ�̃G���[����
                result.rejectValue("toBirthYear", "validation.date-invalidate-to");
                result.rejectValue("toBirthMonth", "validation.empty-msg");
                result.rejectValue("toBirthDay", "validation.empty-msg");
                return "search";
            case 3:
                //���N����_from�����N����_to�̏ꍇ�̃G���[����
                result.rejectValue("fromBirthYear", "validation.date-invalidate-from-to");
                result.rejectValue("fromBirthMonth", "validation.empty-msg");
                result.rejectValue("fromBirthDay", "validation.empty-msg");
                result.rejectValue("toBirthYear", "validation.empty-msg");
                result.rejectValue("toBirthMonth", "validation.empty-msg");
                result.rejectValue("toBirthDay", "validation.empty-msg");
                return "search";
            default:
                //����ȏꍇ��null��ԋp
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pageable getPageable(int pageNumber){
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                //���݃y�[�W����ԋp
                return pageNumber;
            }

            @Override
            public int getPageSize() {
                //1�y�[�W�ɕ\������s����ԋp
                //listPageSize�́A�{�v���O�����̐擪�ɒ�`���Ă���
                return Integer.parseInt(listPageSize);
            }

            @Override
            public int getOffset() {
                //�\���J�n�ʒu��ԋp
                //�Ⴆ�΁A1�y�[�W��2�s�\������ꍇ�́A2�y�[�W�ڂ̕\���J�n�ʒu��
                //(2-1)*2+1=3 �Ōv�Z�����
                return ((pageNumber - 1) * Integer.parseInt(listPageSize) + 1);
            }

            @Override
            public Sort getSort() {
                //�\�[�g�͎g��Ȃ��̂�null��ԋp
                return null;
            }
        };
        return pageable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAllPageNum(SearchForm searchForm) {
        //1�y�[�W�ɕ\������s�����擾
        int listPageSizeNum = Integer.parseInt(listPageSize);
        if(listPageSizeNum == 0){
            return 1;
        }
        //�ꗗ��ʂɕ\������S�f�[�^���擾
        //��������pageable��null��ݒ肷�邱�ƂŁA�ꗗ��ʂɕ\������S�f�[�^���擾�ł���
        Collection<UserData> userDataList = mapper.findBySearchForm(searchForm, null);
        //�S�y�[�W�����v�Z
        //�Ⴆ�΁A1�y�[�W��2�s�\������ꍇ�ŁA�S�f�[�^������5�̏ꍇ�A
        //(5+2-1)/2=3 �ƌv�Z�����
        int allPageNum = (userDataList.size() + listPageSizeNum - 1) / listPageSizeNum;
        return allPageNum == 0 ? 1 : allPageNum;
    }

    /**
     * DemoForm�I�u�W�F�N�g�Ɉ����̃��[�U�[�f�[�^�̊e�l��ݒ肷��
     * @param userData ���[�U�[�f�[�^
     * @return DemoForm�I�u�W�F�N�g
     */
    private DemoForm getDemoForm(UserData userData){
        if(userData == null){
            return null;
        }
        DemoForm demoForm = new DemoForm();
        demoForm.setId(String.valueOf(userData.getId()));
        demoForm.setName(userData.getName());
        demoForm.setBirthYear(String.valueOf(userData.getBirthY()));
        demoForm.setBirthMonth(String.valueOf(userData.getBirthM()));
        demoForm.setBirthDay(String.valueOf(userData.getBirthD()));
        demoForm.setSex(userData.getSex());
        demoForm.setMemo(userData.getMemo());
        demoForm.setSex_value(userData.getSex_value());
        return demoForm;
    }

    /**
     * UserData�I�u�W�F�N�g�Ɉ����̃t�H�[���̊e�l��ݒ肷��
     * @param demoForm DemoForm�I�u�W�F�N�g
     * @return ���[�U�[�f�[�^
     */
    private UserData getUserData(DemoForm demoForm){
        UserData userData = new UserData();
        if(!DateCheckUtil.isEmpty(demoForm.getId())){
            userData.setId(Long.valueOf(demoForm.getId()));
        }
        userData.setName(demoForm.getName());
        userData.setBirthY(Integer.valueOf(demoForm.getBirthYear()));
        userData.setBirthM(Integer.valueOf(demoForm.getBirthMonth()));
        userData.setBirthD(Integer.valueOf(demoForm.getBirthDay()));
        userData.setSex(demoForm.getSex());
        userData.setMemo(demoForm.getMemo());
        userData.setSex_value(demoForm.getSex_value());
        return userData;
    }

    /**
     * �����̕������Long�^�ɕϊ�����
     * @param id ID
     * @return Long�^��ID
     */
    private Long stringToLong(String id){
        try{
            return Long.parseLong(id);
        }catch(NumberFormatException ex){
            return null;
        }
    }

}
