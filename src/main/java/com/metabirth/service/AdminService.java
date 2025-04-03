package com.metabirth.service;

import com.metabirth.dao.AdminDao;
import com.metabirth.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminService {
    private final AdminDao adminDao;
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final Connection connection;



    public AdminService(Connection connection) {
        this.adminDao = new AdminDao(connection);
        this.connection = connection;
    }

    /*
    모든 관리자를 조회할 필요는 없다는 가정
     */


    /*
    단일 관리자를 조회함
     */

    public Admin getAdminById(int admin_id) throws SQLException {
        Admin admin = adminDao.getAdminById(admin_id);
        if (admin == null) {
            throw new SQLException("해당 ID의 관리자를 찾을 수 없습니다.");
            // throw로 해당프로그램을 끝냄 없는 id를 없는건 치명적인 오류이기때문임
        }
        return admin;
    }

    /*
    해당 이메일과 비밀번호가 db에 있는지 확인후 해당 이메일과 비밀번호를 가진 admin객체를 반환함

     */
    public int login(String email, String password) {
        Admin admin = adminDao.login(email, password);
        if(admin == null){
            throw new IllegalArgumentException("관리자 정보를 받아올 수 없습니다. 로그인 실패!");
        }
        return admin.getAdmin_id(); // 로그인 성공 시 admin 객체를 반환, 실패 시 null 반환
    }
    /*
    중복확인
     */
    public boolean registerAdmin(Admin admin) throws SQLException {
        //해당 관라자를 가져와서 중복 이메일 검사
        List<Admin> existingUsers = adminDao.getAlladmin();
        for (Admin a : existingUsers) {
            if (a.getEmail().equals(admin.getEmail())) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }
        }// 해당 관라자가 없다면 db에 추가하는 addadmin실행후 반환
        return adminDao.addAdmin(admin);
    }

    /*
    관리자 삭제
     */
    public boolean deleteadmin(int admin_Id) {
        boolean isDeleted = adminDao.deleteAdmin(admin_Id);
        // 받아온 관리자를 다시 dao에 보내고 반환 받아서 검사를 진행함

        if (!isDeleted) {  // 삭제 실패하면 예외 발생
            throw new IllegalArgumentException("❌ 삭제할 관리자를 찾을 수 없습니다.");
        }
        return true;
    }
    /*
    관리자 수정
     */
    public boolean updateAdmin (Admin admin) throws SQLException {
        //dao에 다시 보내서 기존 관리자 존재 여부 확인을 함
        Admin existingAdmin = adminDao.getAdminById(admin.getAdmin_id());

        if (existingAdmin == null) {
            throw new IllegalArgumentException("수정할 관리자을 찾을 수 없습니다.");
        }

        //업데이트 수행하는 dao에 보내서 업데이트를 진행함
        boolean result = adminDao.updateAdmin(admin);
        //false면 해당 오류 발생
        if (!result) {
            throw new SQLException("수정하는 과정에서 오류가 발생했습니다.");
        }

        return true;
    }




}
