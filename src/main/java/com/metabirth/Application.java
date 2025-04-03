package com.metabirth;

import com.metabirth.config.JDBCConnection;

import com.metabirth.view.AdminView;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
        public static void main(String[] args) throws SQLException {
            Connection connection = JDBCConnection.getConnection();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== LMS 관리 시스템 =====");
                System.out.println("1. 관리자 관리");
                System.out.println("2. 관리자 로그인");
                System.out.println("0. 종료");
                System.out.print("선택: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // 개행 문자 처리

                switch (choice) {
                    case 1 -> startAdminManagement(connection);
                    case 2 -> startAdminlogin(connection);
                    case 0 -> {
                        connection.close();
                        System.out.println("🚀 프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
                }
            }
        }
    /**
     * 📌 admin 관리 시작
     * - admin 관련 기능 실행
     */
    private static void startAdminManagement(Connection connection) {
        AdminView adminView = new AdminView(connection);
        adminView.showMenu();
    }
    /**
     * 📌 로그인 시작
     * - 로그인 성공시 공지사항관리로 넘어감
     */
    private static void startAdminlogin(Connection connection) {
        AdminView adminView = new AdminView(connection);
        adminView.login();
    }
}




