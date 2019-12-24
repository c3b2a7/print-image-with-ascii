package me.lolicom;

import me.lolicom.demo.AsciiPicture;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Map;
import java.util.Properties;


public class App {
    private static final Properties properties = new Properties();
    private static InputStream APP = App.class.getResourceAsStream("/cat.jpg");
    private static OutputStream out = System.out;
    
    static {
        InputStream is = App.class.getResourceAsStream("/properties.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        App.run(args);
    }
    
    /**
     * 黑色：\u001b[30m
     * 红色：\u001b[31m
     * 绿色：\u001b[32m
     * 黄色：\u001b[33m
     * 蓝色：\u001b[34m
     * 洋红色：\u001b[35m
     * 青色：\u001b[36m
     * 白色：\u001b[37m
     * 重置：\u001b[0m
     *
     * @param args 参数
     * @throws IOException io异常
     */
    private static void run(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-"))
                    properties.setProperty(args[i].substring(1).toLowerCase(), args[++i]);
                else
                    properties.setProperty(args[i].substring(1).toLowerCase(), "");
            }
        }
        int o = 0, f = 0;
        String filepath = null;
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey().equals("out") || entry.getKey().equals("o")) {
                if (entry.getValue().equals(""))
                    o = 1;
                else {
                    try {
                        out = new FileOutputStream(String.valueOf(entry.getValue()));
                    } catch (FileNotFoundException e) {
                        System.out.println("\u001b[31m[Info] \u001b[0m文件不存在：" + entry.getValue());
                    }
                }
            }
            if (entry.getKey().equals("f") || entry.getKey().equals("file")) {
                filepath = String.valueOf(entry.getValue());
                try {
                    App.APP = new FileInputStream(filepath);
                } catch (FileNotFoundException e) {
                    System.out.println("\u001b[31m[Info] \u001b[0m文件不存在：" + filepath + "\n\u001b[32m见[-f|-file]用法");
                    return;
                }
                f = 1;
            }
            if ((o & f) == 1) {
                assert filepath != null;
                File file = new File(new File(filepath).getName() + ".txt");
                try {
                    out = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    System.out.println("\u001b[31m[Info] \u001b[0m文件不存在：" + file);
                }
            }
        }
        AsciiPicture picture = new AsciiPicture(ImageIO.read(APP));
        picture.print(properties, new PrintStream(out));
    }
}
