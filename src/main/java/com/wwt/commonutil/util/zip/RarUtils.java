package com.wwt.commonutil.util.zip;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.exception.RarException.RarExceptionType;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class RarUtils {
	/**
	 * @param rarFileName
	 *            rar file name
	 * @param outFilePath
	 *            output file path
	 * @return success Or Failed
	 * @author zhuss
	 * @throws Exception
	 */
	public static boolean unrar(String rarFileName, String outFilePath) throws Exception {

		boolean flag = false;
		try {
			Archive archive = new Archive(new File(rarFileName));
			if (archive == null) {
				throw new FileNotFoundException(rarFileName + " NOT FOUND!");
			}
			if (archive.isEncrypted()) {
				throw new Exception(rarFileName + " IS ENCRYPTED!");
			}
			List<FileHeader> files = archive.getFileHeaders();
			for (FileHeader fh : files) {
				if (fh.isEncrypted()) {
					throw new Exception(rarFileName + " IS ENCRYPTED!");
				}
				String fileName = fh.getFileNameW();
				if (fileName != null && fileName.trim().length() > 0 && !fh.isDirectory()) {
					String saveFileName = outFilePath + "/" + fileName;
					File saveFile = new File(saveFileName);
					File parent = saveFile.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					if (!saveFile.exists()) {
						saveFile.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(saveFile);
					try {
						archive.extractFile(fh, fos);
						fos.flush();
						fos.close();
					} catch (RarException e) {
						if (e.getType().equals(RarExceptionType.notImplementedYet)) {
						}
					} finally {
					}
				}
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	public static void main(String[] args) {

		String userDir = System.getProperty("user.dir");
		System.out.println("userDir=" + userDir);

		String srcFile = "D:\\测试照片\\测试照片.rar";
		String outFilePath = srcFile.substring(0, srcFile.lastIndexOf("."));
		File dir = new File(outFilePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			RarUtils.unrar(srcFile, outFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
