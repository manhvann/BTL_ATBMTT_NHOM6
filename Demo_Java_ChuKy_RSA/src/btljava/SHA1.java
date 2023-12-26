/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btljava;

/**
 *
 * @author ADMIN
 */
import java.io.*;
import java.security.*;

public class SHA1 {
    public static String hash(String text) throws NoSuchAlgorithmException {
        // Tạo đối tượng MessageDigest với thuật toán SHA-1
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
       
        /* Đọc dữ liệu từ tập tin được chọn từ người dùng và cập nhật đối tượng MessageDigest với dữ liệu này
        InputStream inputStream = new FileInputStream("D:\\chukya.txt");
        byte[] buffer = new byte[8192];
        int count;
        while ((count = inputStream.read(buffer)) != -1) {
            sha1.update(buffer, 0, count);
        }
        inputStream.close();*/

        // Tính toán giá trị băm SHA-1 và in kết quả
       
        byte[] hash = sha1.digest(text.getBytes());
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hash) {
            hexHash.append(String.format("%02x", b));
        }
        return hexHash.toString();
    }
            
    public static void main(String[] args) throws Exception {
        
    }
}


