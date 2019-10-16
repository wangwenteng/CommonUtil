package com.wwt.commonUtil.util.zip;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SevenZUtils {
	/**
	 * @author wangwenteng
	 * @param orgPath
	 *            源压缩文件地址
	 * @param tarpath
	 *            解压后存放的目录地址
	 * @throws Exception
	 */
	public static void apache7ZDecomp(String orgPath, String tarpath) {
		if (StringUtils.isEmpty(tarpath) || StringUtils.isEmpty(orgPath)) {
			// 如果路径为空直接返回
			return;
		}
		int index = 0;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		try {
			SevenZFile sevenZFile = new SevenZFile(new File(orgPath));
			SevenZArchiveEntry entry = sevenZFile.getNextEntry();
			// 判断解压后存放的目录地址是否存在
			File unSevenZFileDir = new File(tarpath);
			if (!unSevenZFileDir.exists() || !unSevenZFileDir.isDirectory()) {
				unSevenZFileDir.mkdirs();
			}
			while (entry != null) {
				String entryName = entry.getName();
				if (entryName.contains("/")) {
					entryName = entryName.replace("/", "\\");
				}
				entryFilePath = tarpath + File.separator + entryName;
				// 构建解压后保存的文件夹路径
				index = entryFilePath.lastIndexOf(File.separator);
				if (index != -1) {
					entryDirPath = entryFilePath.substring(0, index);
				} else {
					entryDirPath = "";
				}
				entryDir = new File(entryDirPath);
				// 如果文件夹路径不存在，则创建文件夹
				if (!entryDir.exists() || !entryDir.isDirectory()) {
					entryDir.mkdirs();
				}
				System.out.println(entry.isDirectory());
				entryFile = new File(tarpath + File.separator + entryName);
				if (entryFile.exists()) {
					// 删除已存在的目标文件
					entryFile.delete();
				}
				entryDir = new File(entryDirPath);
				System.out.println("entry.getName()" + entry.getName());
				System.out.println("entry.isDirectory()" + entry.isDirectory());
				// System.out.println(entry.getName());
				if (entry.isDirectory()) {
					entry = sevenZFile.getNextEntry();
					continue;
				}
				FileOutputStream out = new FileOutputStream(tarpath + File.separator + entryName);
				byte[] content = new byte[(int) entry.getSize()];
				// sevenZFile.read(content, 0, content.length);
				 int count = 0;
				while ((count = sevenZFile.read(content, 0, (int) entry.getSize())) > 0) {
					out.write(content, 0, count);

				}
				// out.write(content, 0, (int) entry.getSize());
				out.write(content);
				content = null;
				out.flush();
				out.close();
				entry = sevenZFile.getNextEntry();

			}
			sevenZFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		SevenZUtils.apache7ZDecomp("D:\\测试照片\\测试照片.7z", "D:\\测试照片\\测试照片");
	}
}
