package com.zhangmingjian.kpopservice.utils;
import java.io.*;
import org.springframework.web.multipart.MultipartFile;

public class MakeRomaUtils {

    // 生成带时间戳的音译文本
    // file1表示原曲歌词(里面有英文)，file2表示原曲韩语部分的音译Roma
    public static String getTransWithTime(MultipartFile file1, MultipartFile file2) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(file1.getInputStream()));
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(file2.getInputStream()));

            // 跳过文本2的前三行
            for (int i = 0; i < 3; i++) {
                String line = bufferedReader2.readLine();
                result.append(line).append("\n");
            }

            String line2;
            String line1;

            while ((line2 = bufferedReader2.readLine()) != null) {
                String timestamp2 = extractTimestamp(line2);
                if (timestamp2 != null && line2.substring(10).trim().isEmpty()) {
                    while ((line1 = bufferedReader1.readLine()) != null) {
                        String timestamp1 = extractTimestamp(line1);
                        if (timestamp1 != null && timestamp1.equals(timestamp2)) {
                            result.append(line1).append("\n");
                            break;
                        }
                    }
                } else {
                    result.append(line2).append("\n");
                }
            }

            bufferedReader1.close();
            bufferedReader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    // 生成不带时间戳的音译文本
    // file1表示原曲歌词(里面有英文)，file2表示原曲韩语部分的音译Roma
    public static String getTransWithoutTime(MultipartFile file1, MultipartFile file2) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(file1.getInputStream()));
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(file2.getInputStream()));

            // 跳过文本2的前三行
            for (int i = 0; i < 3; i++) {
                String line = bufferedReader2.readLine();
                result.append(line).append("\n");
            }

            String line2;
            String line1;

            while ((line2 = bufferedReader2.readLine()) != null) {
                String timestamp2 = extractTimestamp(line2);
                if (timestamp2 != null && line2.substring(10).trim().isEmpty()) {
                    while ((line1 = bufferedReader1.readLine()) != null) {
                        String timestamp1 = extractTimestamp(line1);
                        if (timestamp1 != null && timestamp1.equals(timestamp2)) {
                            result.append(line1.substring(10)).append("\n");
                            break;
                        }
                    }
                } else {
                    result.append(line2.substring(10)).append("\n");
                }
            }

            bufferedReader1.close();
            bufferedReader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    // 从一行文本中提取时间戳
    private static String extractTimestamp(String line) {
        int startIndex = line.indexOf("[");
        int endIndex = line.indexOf("]");
        if (startIndex != -1 && endIndex != -1) {
            return line.substring(startIndex, endIndex + 1);
        }
        return null;
    }

}
