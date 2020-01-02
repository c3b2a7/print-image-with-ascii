# print image with ascii

## 使用

```

java -jar print-image-with-ascii-[version].jar

    [-i|-invert]
        反转灰度值，默认关闭，某些情况下开启生成的字符画效果可能更好（例如当图片颜色比较多时）
        java -jar print-image-with-ascii-[version].jar -i

    [-r|-resize]
        开启自动调整大小，默认开启
        关闭：java -jar print-image-with-ascii-[version].jar -r false

    [-w|-width]
        指定宽度，一般来说不用指定，因为默认开启自动调整大小，需要调整时添加此参数
        java -jar print-image-with-ascii-[version].jar -w 80

    [-h|-height]
        指定高度,一般来说不用指定，因为默认开启自动调整大小，需要调整时添加此参数
        java -jar print-image-with-ascii-[version].jar -h 40

    [-m|-margin]
        输出字符画时可选的边距

    [-f|-file]
        指定图片的路径，可以是本地图片也可以是网络图片
        java -jar print-image-with-ascii-[version].jar -f temp.jpg

    [-o|-out]
        指定是否输出到文件，可选，默认在终端中输出，可以跟一个filename指定输出文件，默认是 源文件名.txt
        java -jar print-image-with-ascii-[version].jar -f temp.jpg -o [filename]
        不添加[filename]默认输出到temp.jpg.txt文件

```
## 示例
1. 将文件 D:/cat.jpg 输出到 D:/cat.jpg.txt:  
`java -jar print-image-with-ascii-[version].jar -f D:/cat.jpg -o`

2. 将文件 D:/cat.jpg 输出到 D:/cat.txt:  
`java -jar print-image-with-ascii-[version].jar -f D:/cat.jpg -o D:/cat.txt`

3. 将文件 D:/cat.jpg 反转灰度值输出在终端：  
`java -jar print-image-with-ascii-[version].jar -f D:/cat.jpg -i`

4. 关闭自动调整大小，设置宽80高40 在终端输出效果：  
`java -jar print-image-with-ascii-[version].jar -f D:/cat.jpg -r false -w 80 -h 40`

## note
一般来说可以不加上`[-o|-out]`参数默认输出在终端，查看效果，如果可以再加上`[-o|-out]`参数，默认输出文件名 `源文件名.txt`

## 演示
![演示][1]






[1]: https://github.com/Leisureee/print-image-with-ascii/blob/master/2019-12-24-22-18-14.gif