package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /**
     * ユーザーデータテーブル(user_data)を更新する
     * トランザクションを含むサービス
     */
    @Autowired
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            // ユーザーデータテーブル(user_data)を更新する
            // トランザクションを呼び出す
            // DB更新に失敗するとExceptionがスローされる
            //demoService.transUserDataOra();
            demoService.transUserDataSs();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
