package me.lolicom.demo;

import java.io.*;
import java.util.Map;

/**
 * 图片属性类
 *
 * @author lolicom
 */
public class ImageProperties {
    private int width = 100;
    private int height;
    private int margin = 0;
    private boolean resize = true;
    private boolean invert;
    private InputStream in = ImageProperties.class.getResourceAsStream("/cat.jpg");
    private PrintStream out = System.out;
    
    public ImageProperties(Map<String, String> map) throws FileNotFoundException {
        File file = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "width":
                case "w":
                    this.width = Integer.parseInt(value);
                    break;
                case "height":
                case "h":
                    this.height = Integer.parseInt(value);
                    break;
                case "margin":
                case "m":
                    this.margin = Integer.parseInt(value);
                    break;
                case "resize":
                case "r":
                    this.resize = value.equals("") || value.equals("true");
                    break;
                case "invert":
                case "i":
                    this.invert = value.equals("") || value.equals("true");
                    break;
                case "f":
                case "file":
                    file = new File(value);
                    this.in = new FileInputStream(file);
                    break;
            }
        }
        if (file != null) {
            if ((map.containsKey("o") || map.containsKey("out"))) {
                String outPath = map.containsKey("o") ? map.get("o") : map.get("out");
                if (outPath.equals(""))
                    out = new PrintStream(file.getName() + ".txt");
                else
                    out = new PrintStream(outPath);
            }
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getMargin() {
        return margin;
    }
    
    public void setMargin(int margin) {
        this.margin = margin;
    }
    
    public boolean isResize() {
        return resize;
    }
    
    public void setResize(boolean resize) {
        this.resize = resize;
    }
    
    public boolean isInvert() {
        return invert;
    }
    
    public void setInvert(boolean invert) {
        this.invert = invert;
    }
    
    public InputStream getIn() {
        return in;
    }
    
    public void setIn(InputStream in) {
        this.in = in;
    }
    
    public PrintStream getOut() {
        return out;
    }
    
    public void setOut(PrintStream out) {
        this.out = out;
    }
}
