package codejam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> readFileToList(String filePath, String fileEncoding) {
        if (fileEncoding == null) {
            fileEncoding = System.getProperty("file.encoding");
        }
        File file = new File(filePath);
        BufferedReader br = null;
        String line = null;
        List<String> list = new ArrayList<String>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    file), fileEncoding));
            while ((line = br.readLine()) != null){
                list.add(line);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("关闭IOUtil流时出现错误!");
                }
            }
        }
        return list;
    }

    public static void writeFile(String path, String value, boolean isAppend, String encoding) {
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            if (isAppend) {
                fos = new FileOutputStream(f, isAppend);
            } else {
                fos = new FileOutputStream(f);
            }
            fos.write(value.getBytes(encoding));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
