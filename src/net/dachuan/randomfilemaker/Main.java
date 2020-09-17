package net.dachuan.randomfilemaker;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {
		double gigabyte;
		try {gigabyte = Double.parseDouble(args[0]);}
		catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}
		final long size = (long)(gigabyte*1024*1024*1024); 
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("random.io"));
		byte[] bufferedByte = new byte[128];
		Random random = new Random();
		WriteDeamon writer = new WriteDeamon(output);
		writer.setDaemon(true);
		writer.start();
		
		long count = 0;
		while(count < size) {
			count = count + 128;
			random.nextBytes(bufferedByte);
			output.write(bufferedByte);
		}
		output.flush();
		output.close();
	}

}

class WriteDeamon extends Thread{
	BufferedOutputStream output;
	public WriteDeamon(BufferedOutputStream o) {
		this.output = o;
	}
	@Override
	public void run() {
		while (true) {
			try {output.flush();}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


