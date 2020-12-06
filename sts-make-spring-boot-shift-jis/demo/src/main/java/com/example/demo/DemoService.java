package com.example.demo;

import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DemoService {

    /**
     * ���[�U�[�f�[�^���X�g���擾
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param pageable �y�[�W�l�[�V�����I�u�W�F�N�g
     * @return ���[�U�[�f�[�^���X�g
     */
    List<DemoForm> demoFormList(SearchForm searchForm, Pageable pageable);

    /**
     * ������ID�ɑΉ����郆�[�U�[�f�[�^���擾
     * @param id ID
     * @return ���[�U�[�f�[�^
     */
    DemoForm findById(String id);

    /**
     * ������ID�ɑΉ����郆�[�U�[�f�[�^���폜
     * @param id ID
     */
    void deleteById(String id);

    /**
     * �����̃��[�U�[�f�[�^������΍X�V���A������΍폜
     * @param demoForm �ǉ��E�X�V�pForm�I�u�W�F�N�g
     */
    void createOrUpdate(DemoForm demoForm);

    /**
     * �ǉ��E�X�V�pForm�I�u�W�F�N�g�̃`�F�b�N�������s���A��ʑJ�ڐ��Ԃ�
     * @param demoForm �ǉ��E�X�V�pForm�I�u�W�F�N�g
     * @param result �o�C���h����
     * @param normalPath ���펞�̉�ʑJ�ڐ�
     * @return ��ʑJ�ڐ�
     */
    String checkForm(DemoForm demoForm, BindingResult result, String normalPath);

    /**
     * �����pForm�I�u�W�F�N�g�̃`�F�b�N�������s���A��ʑJ�ڐ��Ԃ�
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param result �o�C���h����
     * @return ��ʑJ�ڐ�
     */
    String checkSearchForm(SearchForm searchForm, BindingResult result);

    /**
     * ���[�U�[�������ɗ��p����y�[�W���O�p�I�u�W�F�N�g�𐶐�����
     * @param pageNumber �y�[�W�ԍ�
     * @return �y�[�W���O�p�I�u�W�F�N�g
     */
    Pageable getPageable(int pageNumber);

    /**
     * �ꗗ��ʂ̑S�y�[�W�����擾����
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @return �S�y�[�W��
     */
    int getAllPageNum(SearchForm searchForm);
}
