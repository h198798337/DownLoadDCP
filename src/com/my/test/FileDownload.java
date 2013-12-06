package com.my.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

import com.ykse.tms.MD5.DataSwitch;
import com.ykse.tms.MD5.MD5Util;

public class FileDownload {
	public void downLoad(String urlAddress, String filepath,int buffer) throws IOException, ParseException, NoSuchAlgorithmException{
//		String tempFileName = filepath + ".temp";//临时文件名
		long startTime = System.currentTimeMillis();
		long lenPos = 2147483798L;
		URL url = new URL(urlAddress);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
//		httpUrl.setRequestProperty("User-Agent","NetFox");  
		httpUrl.setRequestProperty("Range","bytes=" + lenPos +"-");
		httpUrl.connect();
		int fileSize = httpUrl.getContentLength();
		System.out.println("文件大小：" + fileSize);
		long copyFileSize = 0;
//		File tempFile = new File(tempFileName);
		File targetFile = new File(filepath);
		InputStream input = httpUrl.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(input);
		long skipStartTime = System.currentTimeMillis();
//		bis.skip(lenPos);
//		skip(bis, lenPos, buffer);
		System.out.println("跳过时长：" + (System.currentTimeMillis() - skipStartTime));
		/*byte[] buf = new byte[buffer];
		int len = 0;
		FileOutputStream fos = new FileOutputStream(targetFile);
		while ((len = bis.read(buf)) != -1) {
			fos.write(buf, 0, len);
			copyFileSize = copyFileSize + len;
		}
		fos.close();
		bis.close();*/
		RandomAccessFile oSavedFile = new RandomAccessFile(targetFile,"rw");
		// 定位文件指针到 nPos 位置
		oSavedFile.seek(lenPos);
		byte[] b = new byte[buffer];
		int nRead;
		// 从输入流中读入字节流，然后写到文件中
//		while((nRead=bis.read(b,0,buffer)) > 0 && copyFileSize < 2147483648L)
		while((nRead=bis.read(b,0,buffer)) > 0)
		{
			oSavedFile.write(b,0,nRead);
			copyFileSize = copyFileSize + nRead;
		} 
		bis.close();
		input.close();
		httpUrl.disconnect();
		
//		if(targetFile.exists()){
//			targetFile.delete();
//		}
//		tempFile.renameTo(targetFile);//更改文件名
		System.out.println("完成时长：" + (System.currentTimeMillis() - startTime));
		System.out.println("复制的大小：" + copyFileSize);
		System.out.println("MD5：" + getFileMD5String(targetFile, buffer));
	}
	
	private void skip(BufferedInputStream inputStream, long skipBytes, int buffer) throws IOException{
		byte[] b = new byte[buffer];
		long alreadyReadSize = 0;
		int nRead = 0;
		while((nRead=inputStream.read(b,0,buffer)) > 0 && alreadyReadSize < skipBytes)
		{
			alreadyReadSize += nRead;
		} 
	}
	
	private String getFileMD5String(File file, int bufferSize) {
		FileInputStream fileInputStream = null;
		byte[] buffer = new byte[bufferSize]; 
		try {
			MessageDigest targetMD5 = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			int len;
			while ( (len = fileInputStream.read(buffer)) != -1) {
				targetMD5.update(buffer, 0, len);
			}
			return DataSwitch.bytesToHexString(targetMD5.digest()).toUpperCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
	
	public static void main(String args[]){
		FileDownload fileDownload = new FileDownload();
		try {
			fileDownload.downLoad("http://192.168.0.91:8080/ctms/duxingxia.mkv", "独行侠.mkv", 1024);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
