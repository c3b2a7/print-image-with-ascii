package me.lolicom.demo;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author lolicom
 */
public class Application {
    
    private ImageProperties imageProperties;
    
    /**
     * 对传入的参数进行预处理
     *
     * @param args 参数
     */
    private Application(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("-"))
                    map.put(args[i].substring(1).toLowerCase(), args[++i]);
                else
                    map.put(args[i].substring(1).toLowerCase(), "");
            }
        }
        imageProperties = new ImageProperties(map);
    }
    
    
    public static void run(String[] args) {
        new Application(args).run();
    }
    
    /**
     * 打印字符画
     */
    private void run() {
        try {
            new Image(imageProperties).print();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.gc();
        }
    }
}
