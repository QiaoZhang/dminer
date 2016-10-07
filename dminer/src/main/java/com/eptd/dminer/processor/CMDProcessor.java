package com.eptd.dminer.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CMDProcessor {
	private Process p;
	private PrintWriter stdin;
	private OutputStream stdout;
	private ArrayList<String> commands;
	private final String CMD = "cmd";
	
	public CMDProcessor(){
		this.commands = new ArrayList<String>();
	}
	
	public CMDProcessor addCommand(String commandLine){
		commands.add(commandLine);
		return this;
	}
	
	/**
	 * @throws IOException If error occurs during process initialization
	 * @throws InterruptedException If process is interrupted accidentally
	 * @return 0 if command prompt is closed normally; -1 if command execution is interrupted accidentally.
	 */
	public boolean execute() throws InterruptedException, IOException{
		//initialize
		p = Runtime.getRuntime().exec(CMD);
		stdin = new PrintWriter(p.getOutputStream());
		stdout = new OutputStream() { @Override public void write(int b) { } };
		new Thread(() -> syncPipe(p.getErrorStream(), stdout)).start();
		new Thread(() -> syncPipe(p.getInputStream(), stdout)).start();
		//execute appended commands
		if(!commands.isEmpty())
			for(int i=0;i<commands.size();i++)
				stdin.println(commands.get(i));		
		commands.clear();
		stdin.close();
		return p.waitFor(10, TimeUnit.MINUTES);
	}

	private void syncPipe(InputStream istrm,OutputStream ostrm){
		try{
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm.read(buffer)) != -1; )
				ostrm.write(buffer, 0, length);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
