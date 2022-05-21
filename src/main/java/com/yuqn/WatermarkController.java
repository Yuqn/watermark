package com.yuqn;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class WatermarkController {
    public static void main(String[] args) throws DocumentException, IOException {
        // 要输出的pdf文件
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("D:/new.pdf")));
        // 将pdf文件先加水印然后输出
        setWatermark(bos, "F:\\ChromeFile\\ces.pdf", "添加水印");
    }

    /**
    * @Description: TODO
    * @author: scott
    * @date: 2022/5/21 23:33
    * @param bos: 文件输出位置
    * @param input: 文件原位置
    * @param waterMarkName: 水印内容
    * @Return: void
    */
    public static void setWatermark(BufferedOutputStream bos, String input, String waterMarkName)
            throws DocumentException, IOException {

        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

        // 水印透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.5f);
        for (int i = 1; i < total; i++) {
            // 下方水印
            content = stamper.getOverContent(i);
            content.beginText();
            // 水印颜色
            content.setColorFill(BaseColor.GRAY);
            // 水印字体
            content.setFontAndSize(base, 16);
            // 文本大小
            content.setTextMatrix(70, 200);
            // 水印透明度
            content.setGState(gs);
            // 水印位置
            content.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 200, 200, 30);
            content.endText();

            // 上方水印
            content.beginText();
            content.setColorFill(BaseColor.GRAY);
            content.setFontAndSize(base, 16);
            content.setTextMatrix(70, 200);
            content.setGState(gs);
            content.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 200, 400, 30);
            content.endText();
        }

        stamper.close();
    }
}
