package com.wwt.commonutil.util.sftp;

import com.jcraft.jsch.*;
import com.wwt.commonutil.util.file.FileBase64ConvertUitl;
import com.wwt.commonutil.util.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

@Service
public class FtpJSchUtils {
	private final Logger log = LoggerFactory.getLogger("");
	private static ChannelSftp sftp = null;

	// 账号
	private static String username = "llvision";
	// 主机ip
	private static String host = "123.56.22.39";
	// 密码
	private static String password = "llvision@test";
	// 端口
	private static int port = 22;
	// ftp服务器地址
	// @Value("${hostname}")
	// private String host;
	// // sftp服务器端口号默认为22
	// @Value("${port}")
	// private Integer port;
	// // ftp登录账号
	// @Value("${ftpUserName}")
	// private String username;
	// // ftp登录密码
	// @Value("${ftpPassword}")
	// private String password;
	private static Session sshSession = null;

	// 连接sftp服务器
	public ChannelSftp initFtpClient() {
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			if (channel != null) {
				channel.connect();
				log.info("初始化成功");
			} else {

			}
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			log.info("初始化失败！！！");
			disConnect();
			initFtpClient();
		}
		return sftp;
	}

	
	/**
	 * 
	 * @param uploadFile
	 *            上传文件的路径
	 * @return 服务器上文件名
	 * @throws Exception
	 */
	public String upload(String uploadFile, String directory, String fileName) {
		initFtpClient();
		File file = null;
		SftpATTRS attrs = null;
		try {
			attrs = sftp.stat(directory);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (attrs == null) {
				sftp.mkdir(directory);
				log.info("创建子目录：" + directory);
			}
			sftp.cd(directory);
			file = new File(uploadFile);
			sftp.put(new FileInputStream(file), fileName);
			log.info("上传文件" + fileName + "到目录" + directory);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return file == null ? null : fileName;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 *            下载的文件名
	 * @param saveFile
	 *            存在本地的路径
	 */
	public void download(String downloadFileName, String directory, String saveFile) {
		try {
			sftp.cd(directory);

			File file = new File(saveFile);

			sftp.get(downloadFileName, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param deleteFile
	 *            要删除的文件名字
	 */
	public void delete(String deleteFile, String directory) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @return
	 */
	public Vector listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	/**  
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径 
     * @param directory  上传到该目录  
     * @param sftpFileName  sftp端文件名  
     * @param in   输入流  
     
	 * @throws Exception */
	public String wirteContentToFile(String savePath, String directory, String sftpFileName, String content)
			throws Exception {
		initFtpClient();
		FileUtils.makefile(savePath);
		log.info("创建路径" + savePath);
		FileBase64ConvertUitl.toFile(content, savePath + sftpFileName);
		log.info("生成文件" + savePath + sftpFileName);
		String upload = upload(savePath + sftpFileName, directory, sftpFileName);
		File file = new File(savePath + sftpFileName);
		if (file.exists()) {
			file.delete();
		}
		return upload;
	}

	/**
	 * 关闭server
	 * 
	 */
	public void disConnect() {
		if(sftp!=null){
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		}
		if (sshSession != null) {
			if (sshSession.isConnected()) {
				sshSession.disconnect();
			}
		}
	}

	/**
	 * 判断目录是否存在
	 */
	public boolean isDirExist(String directory) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}

	/**
	 * 创建一个文件目录
	 * 
	 * @throws Exception
	 */
	public void createDir(String createpath, ChannelSftp sftp) throws Exception {
	  try {
	   if (isDirExist(createpath)) {
	    this.sftp.cd(createpath); 
	    return;
	   }
	   String pathArry[] = createpath.split("/");
	   StringBuffer filePath = new StringBuffer("/");
	   for (String path : pathArry) {
	    if (path.equals("")) {
	     continue;
	    }
	    filePath.append(path + "/");
	    if (isDirExist(filePath.toString())) {
	     sftp.cd(filePath.toString());
	    } else {
	     // 建立目录
	     sftp.mkdir(filePath.toString());
	     // 进入并设置为当前目录
	     sftp.cd(filePath.toString());
	    }
	   }
	   this.sftp.cd(createpath);
	  } catch (SftpException e) {
			throw new Exception("创建路径错误：" + createpath);
	  }
	}

	public boolean createDir(String createPath) {
		try {
			if (isDirExist(createPath)) {
				sftp.cd(createPath);
				return true;
			}
			String pathArry[] = createPath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(filePath.toString())) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}
			}
		} catch (SftpException e) {
		}
		return false;
	}

	public boolean upload(String remotePath, String fileName, InputStream in) {
		try {
			createDir(remotePath);
			sftp.cd(remotePath);
			sftp.put(in, fileName, ChannelSftp.RESUME);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}


	public static void main(String[] args) throws Exception {
		FtpJSchUtils ftpJSchUtils = new FtpJSchUtils();
		ftpJSchUtils.initFtpClient();
		String num = UUID.randomUUID().toString();
		String fileName = num + ".jpg";
		String filePath = "/upload/record/face/";
		String newSourcePicBase64 = FileBase64ConvertUitl
				.encodeBase64File("D:/测试照片/93fb69e0-5c57-4ab5-bb28-131c006a3dd6.jpg");
		byte[] decode = Base64.getDecoder().decode(newSourcePicBase64);
		InputStream input = new ByteArrayInputStream(decode);
		ftpJSchUtils.upload(filePath, fileName, input);
	}
}