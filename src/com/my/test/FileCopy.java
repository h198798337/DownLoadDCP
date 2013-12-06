package com.my.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.ykse.tms.MD5.MD5Util;

public class FileCopy {
	public static void main(String args[]) {
		FileInputStream inputStream = null;
		RandomAccessFile oSavedFile = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			File sourceFile = new File("test.txt");
			File targetFile = new File("test2.txt");
			System.out.println("source MD5：" + MD5Util.getFileMD5String(sourceFile));
			inputStream = new FileInputStream(sourceFile);
//			bufferedInputStream = new BufferedInputStream(inputStream);
//			FileChannel channel = inputStream.getChannel();
			oSavedFile = new RandomAccessFile(new File("test2.txt"), "rw");
			
//			System.out.println("marksupport : " + inputStream.markSupported());
//			channel.position(128);
			oSavedFile.seek(128);
			inputStream.skip(128);
//			bufferedInputStream.reset();
			int copyFileSize = 0;
//			ByteBuffer buffer = ByteBuffer.allocate(128);
			byte[] b = new byte[128];
			int nRead;
			// 从输入流中读入字节流，然后写到文件中
//			while ((nRead = channel.read(buffer)) > 0) {
			while ((nRead = inputStream.read(b,0,b.length)) > 0) {
//			while ((nRead = inputStream.read(b,0,b.length)) > 0 && copyFileSize < 128) {
//				buffer.flip();
//				oSavedFile.write(buffer.array(), 0, nRead);
				oSavedFile.write(b, 0, nRead);
//				buffer.clear();
				copyFileSize += nRead;
			}
			System.out.println("copyFileSize : " + copyFileSize);
			System.out.println("target MD5：" + MD5Util.getFileMD5String(targetFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (oSavedFile != null)
				try {
					oSavedFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (bufferedInputStream != null)
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}
