package com;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class NoParticipanteThread extends Thread{
	
	private static HashMap<Object, Object> hashMapParticipante = new HashMap<Object, Object>();
		
	private Socket cSocket;
	private String Resp;
	    
	public NoParticipanteThread(Socket socket) {
		this.cSocket = socket;
	}

	public static HashMap<Object, Object> getHm() {
		return hashMapParticipante;
	}
		
	public void run() {
			
		try {
				
			ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
				
			Resp = "Ligação Nó Estabelecida";
			out.writeObject(Resp);
				
		} catch (IOException e) {
			System.out.println("Ocorreu um erro");
		} 
	}
}
	