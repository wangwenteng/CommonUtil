package com.wwt.commonUtil.util.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 执行Linux的shell命令并在console端输出结果
 */
public class CallShell {

	/**
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("请输入执行命令");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		System.out.println("输入的指令是" + str);
		executeShell(str);

	}

	public static void executeShell(final String command) {
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			// 通过执行cmd命令调用protoc.exe程序
			BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = strCon.readLine()) != null) {
				System.out.println("java print:" + line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void callShell(String shellString) {
		try {
			Process process = Runtime.getRuntime().exec(shellString);
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				System.err.println("call shell failed. error code is :" + exitValue);
			}
		} catch (Throwable e) {
			System.err.println("call shell failed. " + e);
		}
	}
}
