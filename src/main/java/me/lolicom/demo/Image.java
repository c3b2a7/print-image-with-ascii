package me.lolicom.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author lolicom
 */
public class Image {
    private static final char[] PIXELS = {'@', '#', '8', '&', 'o', ':', '*', '.', ' '};
    private static final double[] RGB_WEIGHT = {0.2126d, 0.7152d, 0.0722d, 0.229d, 0.578d, 0.114d};
    private ImageProperties properties;
    private BufferedImage image;
    
    public Image(ImageProperties properties) throws IOException {
        this(properties, ImageIO.read(properties.getIn()));
    }
    
    public Image(ImageProperties imageProperties, BufferedImage image) {
        this.properties = imageProperties;
        this.image = image;
    }
    
    public void print() {
        prePrint();
        for (int y = 0; y < image.getHeight(); y++) {
            PrintStream out = properties.getOut();
            for (int i = 0; i < properties.getMargin(); i++) {
                out.print(' ');
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                out.print(getAsciiPixel(color, properties.isInvert()));
            }
            out.println();
        }
    }
    
    private void prePrint() {
        if (properties.isResize())
            image = resizeImage(image, properties.getWidth(), properties.getHeight());
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
    
    protected BufferedImage resizeImage(BufferedImage image, int width, int height) {
        if (width < 1) {
            width = 1;
        }
        if (height <= 0) {
            double ratio = width * 0.5 / image.getWidth();
            height = (int) Math.ceil(image.getHeight() * ratio);
        }
        BufferedImage tagImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        java.awt.Image scaled = image.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        tagImage.getGraphics().drawImage(scaled, 0, 0, null);
        return tagImage;
    }
    
    protected BufferedImage resizeImage(BufferedImage image, double ratio) {
        if (ratio < 0) {
            ratio = -ratio;
        }
        int width = (int) Math.ceil(image.getWidth() * ratio);
        int height = (int) Math.ceil(image.getHeight() * ratio * 0.5);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        java.awt.Image scaled = image.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        resized.getGraphics().drawImage(scaled, 0, 0, null);
        return resized;
    }
    
    /**
     * 获取亮度值，当inverse为true时，获得一个相反的亮度值
     *
     * @param color   颜色
     * @param inverse 是否反转
     * @return 结果
     */
    protected int getLuminance(Color color, boolean inverse) {
        double luminance = 0.0;
        luminance += getLevelByWeight(color.getRed(), inverse, RGB_WEIGHT[0]);
        luminance += getLevelByWeight(color.getGreen(), inverse, RGB_WEIGHT[1]);
        luminance += getLevelByWeight(color.getBlue(), inverse, RGB_WEIGHT[2]);
        return (int) Math.ceil((luminance / 0xFF) * 100);
    }
    
    /**
     * 获取灰度值，当inverse为true时，获得一个相反的灰度值
     * grey= (r*38 + g*75 + b*15) >> 7
     *
     * @param color   颜色
     * @param inverse 是否反转
     * @return 结果
     */
    protected double getGray(Color color, boolean inverse) {
        double grey = 0.0;
        grey += getLevelByWeight(color.getRed(), inverse, RGB_WEIGHT[3]);
        grey += getLevelByWeight(color.getGreen(), inverse, RGB_WEIGHT[4]);
        grey += getLevelByWeight(color.getBlue(), inverse, RGB_WEIGHT[5]);
        return grey;
    }
    
    protected double getLevelByWeight(int component, boolean inverse, double weight) {
        return (inverse ? 0xFF - component : component) * weight;
    }
}
