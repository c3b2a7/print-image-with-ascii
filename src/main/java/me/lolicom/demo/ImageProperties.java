package me.lolicom.demo;

import java.io.*;
import java.net.URL;
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
    private String in;
    private String out;
    
    public ImageProperties(Map<String, String> map) {
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
                    setIn(value);
                    break;
                case "o":
                case "out":
                    setOut(value);
                    break;
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
    
    public InputStream getIn() throws IOException {
        if (this.in == null || "".equals(this.in)) {
            return ImageProperties.class.getResourceAsStream("/cat.jpg");
        }
        if (this.in.startsWith("http")) {
            return new URL(this.in).openStream();
        }
        return new FileInputStream(this.in);
    }
    
    public void setIn(String in) {
        if (!in.startsWith("http")) {
            this.in = new File(in).toURI().getPath();
        } else {
            this.in = in;
        }
    }
    
    public PrintStream getOut() throws FileNotFoundException {
        if (this.in != null && !"".equals(this.in)) {
            if (this.out != null) {
                if ("".equals(this.out)) {
                    String fileName = this.in.substring(this.in.lastIndexOf('/') + 1);
                    return new PrintStream(fileName + ".txt");
                }
                return new PrintStream(this.out);
            }
        }
        return System.out;
    }
    
    public void setOut(String out) {
        this.out = out;
    }
}
