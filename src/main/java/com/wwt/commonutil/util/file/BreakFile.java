package com.wwt.commonutil.util.file;

import java.io.*;

/**
 * Created by on 2017/12/8.
 */
public class BreakFile {

	public static void getRow() {
		try {
			FileReader read = new FileReader(
					"E:/backup/backup1/fd1b47faace20d010010af92c24ce461458021532841145c13386a840058471c-json.log");
			BufferedReader br = new BufferedReader(read);
			String row;
			int rownum = 1;
			while ((row = br.readLine()) != null) {
				rownum++;
			}
			System.out.println("rownum=" + rownum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void cuttingFile() {
		
		try {
			FileReader read = new FileReader(
					"E:/backup/backup1_3.log");
			BufferedReader br = new BufferedReader(read);
			String row;

			int rownum = 1;

			int fileNo = 1;
			FileUtils.makefile("E:/backup/backup1/backup1_" + fileNo + ".log");
			FileWriter fw = new FileWriter("E:/backup/backup1/backup1_" + fileNo + ".log");
			while ((row = br.readLine()) != null) {
				rownum++;
				fw.append(row + "\r\n");

				if ((rownum / 54183) > (fileNo - 1)) {
					fw.close();
					fileNo++;
					fw = new FileWriter("E:/backup/backup1/backup1_" + fileNo + ".log");
				}
			}
			fw.close();
			System.out.println("rownum=" + rownum + ";fileNo=" + fileNo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		cuttingFile();
	}

}
