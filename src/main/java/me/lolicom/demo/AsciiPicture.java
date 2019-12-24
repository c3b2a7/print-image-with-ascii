package me.lolicom.demo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.Properties;

/**
 * @author lolicom
 */
public class AsciiPicture {
    private static final char[] PIXELS = {'@', '#', '8', '&', 'o', ':', '*', '.', ' '};//' ', '.', '*', ':', 'o', '&', '8', '#', '@'
    private static final double[] RGB_WEIGHT = {0.2126d, 0.7152d, 0.0722d};
    private BufferedImage image;
    
    public AsciiPicture(BufferedImage image) {
        this.image = image;
    }
    
    public void print(Properties properties, PrintStream out) {
        boolean resize = properties.containsKey("resize") || properties.containsKey("r");
        if (resize && !properties.getProperty("r", properties.getProperty("resize")).equals("")) {
            resize = Boolean.parseBoolean(properties.getProperty("r", properties.getProperty("resize")));
        }
        print(properties, out, resize);
    }
    
    protected void print(Properties properties, PrintStream out, boolean resize) {
        if (resize) {
            int width = Integer.parseInt(getProperty(properties, "w", "width"));
            int height = Integer.parseInt(getProperty(properties, "h", "height"));
            image = resizeImage(image, width, height);
        }
        int margin = Integer.parseInt(properties.getProperty("margin"));
        boolean invert = properties.containsKey("invert") || properties.containsKey("i");
        print(margin, invert, out);
    }
    
    /**
     * 从属性文件中获取值，可以传入一个默认的属性名，将在找不到指定的属性时作为默认值返回
     *
     * @param properties 属性
     * @param name 指定的属性名
     * @param defaultName 默认属性的属性名
     * @return 属性值，也可能是默认值
     */
    private String getProperty(Properties properties, String name, String defaultName) {
        String value = properties.getProperty(name, properties.getProperty(defaultName));
        if ("".equals(value)) {
            return properties.getProperty(defaultName);
        }
        return value;
    }
    
    protected void print(int margin, boolean invert, PrintStream out) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int i = 0; i < margin; i++) {
                out.print(' ');
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                out.print(getAsciiPixel(color, invert));
            }
            out.println();
        }
    }
    
    protected BufferedImage resizeImage(BufferedImage image, int width, int height) {
        if (width < 1) {
            width = 1;
        }
        if (height <= 0) {
            double ratio = width * 0.5 / image.getWidth();
            height = (int) Math.ceil(image.getHeight() * ratio);
        }
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Image scaled = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        resized.getGraphics().drawImage(scaled, 0, 0, null);
        return resized;
    }
    
    protected char getAsciiPixel(Color color, boolean dark) {
        int increment = (10 / PIXELS.length) * 10;
        int start = increment * PIXELS.length;
        double luminance = getLuminance(color, dark);
        for (int i = 0; i < PIXELS.length; i++) {
            if (luminance >= (start - (i * increment))) {
                return PIXELS[i];
            }
        }
        return PIXELS[PIXELS.length - 1];
    }
    
    /**
     * 获取灰度值，当inverse为true时，获得一个相反的灰度值
     *
     * @param color 颜色
     * @param inverse 是否反转
     * @return 结果
     */
    protected int getLuminance(Color color, boolean inverse) {
        double luminance = 0.0;
        luminance += getLuminance(color.getRed(), inverse, RGB_WEIGHT[0]);
        luminance += getLuminance(color.getGreen(), inverse, RGB_WEIGHT[1]);
        luminance += getLuminance(color.getBlue(), inverse, RGB_WEIGHT[2]);
        return (int) Math.ceil((luminance / 0xFF) * 100);
    }
    
    protected double getLuminance(int component, boolean inverse, double weight) {
        return (inverse ? 0xFF - component : component) * weight;
    }
    
}
