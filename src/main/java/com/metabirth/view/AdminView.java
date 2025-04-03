package com.metabirth.view;


import com.metabirth.model.Admin;
import com.metabirth.service.AdminService;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminView {

    private AdminService adminService;
    private Scanner scanner;
    private Connection connection;

    public AdminView(Connection connection) {  //Scanner scanner
        this.adminService = new AdminService(connection); // AdminService로 수정
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    /*
    처음 사용자 메뉴
    사용자가 crud기능을 선택할 수 있도록 메뉴를 제공
     */


    public void showMenu() {
        while (true) {
            System.out.println("\n===== 사용자 관리 시스템 =====");
            System.out.println("1. 관리자 등록");
            System.out.println("2. 관리자 조회 (ID)");
            System.out.println("3. 관리자 수정");
            System.out.println("4. 관리자 삭제");
            System.out.println("0. 종료");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> registerAdmin();
                case 2 -> getAdminById();
                case 3 -> updateAdmin();
                case 4 -> deleteAdmin();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    /*
    관리자 조회
             */
    private void getAdminById() {
        System.out.print("조회할 사용자 ID를 입력하세요: ");
        int adminid = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리

        try {
            Admin admin = adminService.getAdminById(adminid);
            System.out.println("\n===== 사용자 정보 =====");
            System.out.println(admin);
        } catch (SQLException e) {
            System.out.println("사용자 조회 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
       관리자 등록
    */

    public void registerAdmin() {

        // 새로운 관리자 등록을 위한 내용들을 입력 받음
        Scanner scanner = new Scanner(System.in);
        System.out.print("이메일을 입력하세요: ");
        String email = scanner.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        System.out.print("이름을 입력하세요: ");
        String adminname = scanner.nextLine();

        Admin admin = new Admin(0, adminname, (byte) 1, (byte) 1, null, null, null, email, password);
        try {
            boolean success = adminService.registerAdmin(admin);
            //입력한 관리자를 registerAdmin에 보냄
            if (success) {
                System.out.println("사용자가 성공적으로 등록되었습니다.");
            } else {
                System.out.println("사용자 등록에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("사용자 등록 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    관리자 삭제
     */
    private void deleteAdmin() {
        System.out.print("삭제할 관리자 ID를 입력하세요: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리
        // 삭제를 위한 id를 입력받음

        boolean success = adminService.deleteadmin(adminId);
        // 해당 deleteadmin에 id를 보내서 삭제를 진행함
        if (success) {
            System.out.println("✅ 관리자 계정이 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("❌ 관리자 계정 삭제에 실패했습니다.");
        }
    }
    /*

    관리자 수정
     */
    private void updateAdmin() {
        System.out.print("수정할 사용자 ID를 입력하세요: ");
        int adminid = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리

        System.out.print("새로운 사용자 이름: ");
        String adminname = scanner.nextLine();

        System.out.print("새로운 이메일: ");
        String email = scanner.nextLine();

        System.out.print("새로운 비밀번호: ");
        String password = scanner.nextLine();

        Admin admin = new Admin(adminid, adminname, (byte) 1, (byte) 1, null, null, null, email, password);
        try {
            boolean success = adminService.updateAdmin(admin);
            if (success) {
                System.out.println("사용자 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("사용자 정보 수정에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.out.println("사용자 정보 수정 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    관리자 로그인
     */

    public void login() {
        System.out.print("이메일을 입력하세요: ");
        String email = scanner.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        int admin_id = adminService.login(email, password); // 로그인 후 admin 객체 반환
        NoticesView noticesView = new NoticesView(connection, admin_id);
        noticesView.showMenu();

    }
}
