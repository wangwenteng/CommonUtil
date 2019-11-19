package com.wwt.commonutil.util.file;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.exception.RarException.RarExceptionType;
import com.github.junrar.rarfile.FileHeader;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CompressFileUit {

	public void extractZipFiles() {
		String root = "c:/javazip/";
		URL url = CompressFileUit.class.getResource("/aa.zip");
		String filename = url.getFile();
		try {
			// destination folder to extract the contents
			byte[] buf = new byte[1024];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(new FileInputStream(filename));
			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {

				// for each entry to be extracted
				String entryName = zipentry.getName();

				System.out.println(entryName);

				int n;
				FileOutputStream fileoutputstream;
				File newFile = new File(entryName);

				String directory = newFile.getParent();

				// to creating the parent directories
				if (directory == null) {
					if (newFile.isDirectory()) {
						break;
					}
				} else {
					new File(root + directory).mkdirs();
				}

				if (!zipentry.isDirectory()) {
					System.out.println("File to be extracted....." + entryName);
					fileoutputstream = new FileOutputStream(root + entryName);
					while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
						fileoutputstream.write(buf, 0, n);
					}
					fileoutputstream.close();
				}

				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();
			}
			zipinputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
				if (fileName != null && fileName.trim().length() > 0) {
					String saveFileName = outFilePath + "\\" + fileName;
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

	public void extractRarFiles(String srcRarPath, String dstDirectoryPath) {
		File archive = new File(srcRarPath);
		File destination = new File(dstDirectoryPath);

		Archive arch = null;
		try {
			arch = new Archive(archive);
		} catch (RarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (arch != null) {
			if (arch.isEncrypted()) {
				System.out.println("archive is encrypted cannot extreact");
				return;
			}
			FileHeader fh = null;
			while (true) {
				fh = arch.nextFileHeader();
				if (fh == null) {
					break;
				}
				if (fh.isEncrypted()) {
					System.out.println("file is encrypted cannot extract: " + fh.getFileNameString());
					continue;
				}
				System.out.println("extracting: " + fh.getFileNameString());
				try {
					if (fh.isDirectory()) {
						createDirectory(fh, destination);
					} else {
						File f = createFile(fh, destination);
						OutputStream stream = new FileOutputStream(f);
						arch.extractFile(fh, stream);
						stream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private File createFile(FileHeader fh, File destination) {
		File f = null;
		String name = null;
		if (fh.isFileHeader() && fh.isUnicode()) {
			name = fh.getFileNameW();
		} else {
			name = fh.getFileNameString();
		}
		f = new File(destination, name);
		if (!f.exists()) {
			try {
				f = makeFile(destination, name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	private File makeFile(File destination, String name) throws IOException {
		String[] dirs = name.split("\\\\");
		if (dirs == null) {
			return null;
		}
		String path = "";
		int size = dirs.length;
		if (size == 1) {
			return new File(destination, name);
		} else if (size > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				path = path + File.separator + dirs[i];
				new File(destination, path).mkdir();
			}
			path = path + File.separator + dirs[dirs.length - 1];
			File f = new File(destination, path);
			f.createNewFile();
			return f;
		} else {
			return null;
		}
	}

	private void createDirectory(FileHeader fh, File destination) {
		File f = null;
		if (fh.isDirectory() && fh.isUnicode()) {
			f = new File(destination, fh.getFileNameW());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameW());
			}
		} else if (fh.isDirectory() && !fh.isUnicode()) {
			f = new File(destination, fh.getFileNameString());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameString());
			}
		}
	}

	private void makeDirectory(File destination, String fileName) {
		String[] dirs = fileName.split("\\\\");
		if (dirs == null) {
			return;
		}
		String path = "";
		for (String dir : dirs) {
			path = path + File.separator + dir;
			new File(destination, path).mkdir();
		}
	}

	public static void main(String[] args) throws Exception {
		unrar("D:\\测试照片\\测试照片.rar", "D:\\测试照片\\测试照片");
	}
}

