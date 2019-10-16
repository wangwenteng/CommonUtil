package com.wwt.commonUtil.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.wwt.commonUtil.util.file.FileUtils;
import com.wwt.commonUtil.util.zip.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wwt.commonUtil.util.RarUtils;

@Controller
@RequestMapping("/test")
public class TestFileController {
	@ResponseBody
	@RequestMapping("/addFace")
	public String addFace(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
	{
		try {

		System.out.println("================");
		String suffix = file.getOriginalFilename().trim().substring(file.getOriginalFilename().indexOf("."));
		System.out.println("后缀：" + suffix);
		String num = UUID.randomUUID().toString();
		// 服务器存储文件路径
		String pathName = "upload/face/" + num + suffix;
		String filePath = request.getSession().getServletContext().getRealPath("/") + pathName;
		System.out.println("新路径pathName：" + filePath);
		// 没有路径自动创建路径
		FileUtils.makefile(filePath);
		// 转存文件
		file.transferTo(new File(filePath));
		if (suffix.equals(".zip")) {
			ZipUtils.unzip(filePath, request.getSession().getServletContext().getRealPath("/") + "upload/face/" + num,
					false);
		} else if (suffix.equals(".rar")) {
			RarUtils.unrar(filePath, request.getSession().getServletContext().getRealPath("/") + "upload/face/" + num);
		}
		} catch (Exception e) {
			e.printStackTrace();
			return "文件格式不正确";
		}
		return null;
	}
}
