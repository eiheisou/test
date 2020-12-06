package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.Collection;

@Mapper
public interface UserDataMapper {

    /**
     * ���[�U�[�f�[�^�e�[�u��(user_data)���猟�������ɍ����f�[�^���擾����
     * @param searchForm �����pForm�I�u�W�F�N�g
     * @param pageable �y�[�W�l�[�V�����I�u�W�F�N�g
     * @return ���[�U�[�f�[�^�e�[�u��(user_data)�̌��������ɍ����f�[�^
     */
    Collection<UserData> findBySearchForm(
            @Param("searchForm") SearchForm searchForm, @Param("pageable") Pageable pageable);

    /**
     * �w�肵��ID�������[�U�[�f�[�^�e�[�u��(user_data)�̃f�[�^���擾����
     * @param id ID
     * @return ���[�U�[�f�[�^�e�[�u��(user_data)�̎w�肵��ID�̃f�[�^
     */
    UserData findById(Long id);

    /**
     * �w�肵��ID�������[�U�[�f�[�^�e�[�u��(user_data)�̃f�[�^���폜����
     * @param id ID
     */
    void deleteById(Long id);

    /**
     * �w�肵�����[�U�[�f�[�^�e�[�u��(user_data)�̃f�[�^��ǉ�����
     * @param userData ���[�U�[�f�[�^�e�[�u��(user_data)�̒ǉ��f�[�^
     */
    void create(UserData userData);

    /**
     * �w�肵�����[�U�[�f�[�^�e�[�u��(user_data)�̃f�[�^���X�V����
     * @param userData ���[�U�[�f�[�^�e�[�u��(user_data)�̍X�V�f�[�^
     */
    void update(UserData userData);

    /**
     * ���[�U�[�f�[�^�e�[�u��(user_data)�̍ő�lID���擾����
     * @return ���[�U�[�f�[�^�e�[�u��(user_data)�̍ő�lID
     */
    long findMaxId();
}