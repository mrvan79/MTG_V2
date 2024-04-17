package com.juneutf.mtg.config.vender;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.juneutf.mtg.model.JobModel;
import com.juneutf.mtg.model.PlanModel;
import com.juneutf.mtg.model.TableModel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class FileLogToSQL {


    public String createFileLog(String className) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filePath = "logs/" + today + "/" + className + "_"+today+".log";

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                log.info("ログファイル生成: " + filePath);
            } catch (FileAlreadyExistsException e) {
            	log.info("ログファイル生成エラー: " + filePath);
            } catch (IOException e) {
            	log.info("ログファイル生成エラー: " + filePath);
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public void planHistory(PlanModel planmodel) {
        String filePath = this.createFileLog("PlanHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[新規予約]	[ID:" + planmodel.getId() + "] [日付:"
                + planmodel.getDate_plan() + "(" + planmodel.getDate_day() + ")] [時間:" + planmodel.getTime_start()
                + "～" + planmodel.getTime_end() + "] [内容ID:" + planmodel.getPurpose() + "] [担当者ID:"
                + planmodel.getPurpose() + "] [状況:予約中]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void planHistoryEdit(JobModel jobModel) {
        String filePath = this.createFileLog("PlanHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[予約編集]	[ID:" + jobModel.getId() + "] [日付:"
                + jobModel.getDate_plan() + "(" + jobModel.getDate_day() + ")] [時間:" + jobModel.getTime_start()
                + "～" + jobModel.getTime_end() + "] [内容ID:" + jobModel.getPurposeId() + "] [担当者ID:"
                + jobModel.getChargeId() + "] [状況:"+ jobModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
//    batch
    public void planHistoryBatch(JobModel jobModel) {
        String filePath = this.createFileLog("PlanHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        String message = "[" + timeNow + "]	[バッチ]	[予約完了]	[ID:" + jobModel.getId() + "] [日付:"
                + jobModel.getDate_plan() + "(" + jobModel.getDate_day() + ")] [時間:" + jobModel.getTime_start()
                + "～" + jobModel.getTime_end() + "] [内容ID:" + jobModel.getPurposeId() + "] [担当者ID:"
                + jobModel.getChargeId() + "] [状況:"+ jobModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void purposeHistory(TableModel tableModel) {
        String filePath = this.createFileLog("PurposeHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[新規内容]	[ID:" + tableModel.getId() +
        		"] [内容ID:" + tableModel.getTextEdit() +"] [状況:"+ tableModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void purposeHistoryEdit(TableModel tableModel) {
        String filePath = this.createFileLog("PurposeHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[編集内容]	[ID:" + tableModel.getId() +
        		"] [内容ID:" + tableModel.getTableName() +"] [状況:"+ tableModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void chargeHistory(TableModel tableModel,String pass) {
        String filePath = this.createFileLog("ChargeHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[新規担当者]	[ID:" + tableModel.getId() +
        		"] [ユーザー:" + tableModel.getUsername() +"] [パスワード:" + pass +
        		"] [名前:" + tableModel.getFullname() +
        		"] [メールアドレス:" + tableModel.getEmail() +"] [使用範囲：:" + tableModel.getPublicid() +
        		"] [権限:" + tableModel.getRole() +"] [状況:"+ tableModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void chargeHistoryEdit(TableModel tableModel) {
        String filePath = this.createFileLog("ChargeHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[編集担当者]	[ID:" + tableModel.getId() +
        		"] [ユーザー:" + tableModel.getUsername() +"] [名前:" + tableModel.getFullname() +
        		"] [メールアドレス:" + tableModel.getEmail() +"] [使用範囲：:" + tableModel.getPublicid() +
        		"] [権限:" + tableModel.getRole() +"] [状況:"+ tableModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void chargeHistoryReset(TableModel tableModel,String pass) {
        String filePath = this.createFileLog("ChargeHistory");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String timeNow = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser customUser = (CustomUser) userDetails;

        String message = "[" + timeNow + "]	["+customUser.getFullName()+"]	[パスワードリセット]	[ID:" + tableModel.getId() +
        		"] [ユーザー:" + tableModel.getUsername() +"] [パスワード:" + pass +
        		"] [名前:" + tableModel.getFullname() +
        		"] [メールアドレス:" + tableModel.getEmail() +"] [使用範囲：:" + tableModel.getPublicid() +
        		"] [権限:" + tableModel.getRole() +"] [状況:"+ tableModel.getStatus() +"]";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
