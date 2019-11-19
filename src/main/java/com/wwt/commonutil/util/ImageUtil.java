package com.wwt.commonutil.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	/**
	 * 判断压缩文件的类型
	 */

	/**
	 * 生成缩略图名称
	 * 
	 * @param srcFileName
	 * @return
	 */
	public static String createThumbFileName(String srcFileName) {
		StringBuffer thumbFileName = new StringBuffer();

		int pos = srcFileName.lastIndexOf(".");
		thumbFileName.append(srcFileName.substring(0, pos));
		thumbFileName.append("_small");
		thumbFileName.append(srcFileName.substring(pos, srcFileName.length()));
		return thumbFileName.toString();
	}

	/**
	 * 对图片进行剪裁
	 * 
	 * @param is
	 *            图片输入流
	 * @param width
	 *            裁剪图片的宽
	 * @param height
	 *            裁剪图片的高
	 * @param imageFormat
	 *            输出图片的格式
	 * @return
	 */
	public static byte[] clipImage(InputStream is, int width, int height, String imageFormat) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(is);
			// 缩小边长
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 绘制 缩小 后的图片
			tag.getGraphics().drawImage(src, 0, 0, width, height, null);
			ImageIO.write(tag, imageFormat, bos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bos.toByteArray();
	}
}