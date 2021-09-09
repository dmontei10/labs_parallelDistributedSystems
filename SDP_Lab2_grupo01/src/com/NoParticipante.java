package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class NoParticipante extends Thread{

    private static SortedMap<Integer, String> sortedServers = new TreeMap<Integer, String>();
    private static HashMap<Object, Object> hashMapNoParticipante = new HashMap<Object, Object>();
	
    private static ServerSocket serverParticipant;
    private static Socket socketParticipant;
    private String Resp;
    
    private static int getHash(String str) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++){
			sum = sum + str.charAt(i);
		}
		return sum % sortedServers.size();
	}
    
    public NoParticipante(Socket socketParticipant) {
		this.socketParticipant = socketParticipant;
	}
    
    public void run(){
    	
    	try{
	    	ObjectOutputStream out = new ObjectOutputStream(socketParticipant.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socketParticipant.getInputStream());
			Object command = null;
			Object chave = null;
			Object valor = null;
			Object valorChave = null;
			Object rep = null;
			
			rep = in.readObject();
			System.out.println(rep);
			
				while(true){
					
					command = in.readObject();
					
					if(command.equals("R")){
					
					try{
						
						chave = in.readObject();
						
						valor = in.readObject();
						
						if(hashMapNoParticipante.containsKey(chave)){
							
							out.writeObject("Chave existente");
						
						}else{
							
							hashMapNoParticipante.put(chave, valor);
							out.writeObject("Item registado com sucesso");
						
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
		
				}else if(command.equals("C")){
					
					try{
						
						valorChave = in.readObject();
						
						if(!hashMapNoParticipante.containsKey(valorChave)){
							
							out.writeObject("Chave inexistente");
						
						}else{
							
							out.writeObject(hashMapNoParticipante.get(valorChave));
						
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
				}else if(command.equals("D")){
					
					try{
						
						try {
							valorChave = in.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(!hashMapNoParticipante.containsKey(valorChave)){
							
							out.writeObject("Chave inexistente");
						
						}else{
							
							hashMapNoParticipante.remove(valorChave);
							out.writeObject("item removido com sucesso");
							out.writeObject(hashMapNoParticipante);
						
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
					
				}else if(command.equals("Q")){
					try{
						in.close();
						out.close();
						
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
				}	
			}
    	}catch (IOException e) {
    	} catch (ClassNotFoundException e1) {
    		e1.printStackTrace();
    	}
    }
    public static void main(String[] args) {
    	
    	try{
	    	Scanner serv = new Scanner(System.in);
			System.out.println("IP do No Principal: ");
			String ip = serv.nextLine();
			System.out.println("Porta do No Principal: ");
			int porta = serv.nextInt();
			
			Socket clientRegister = new Socket(ip, porta);
			
			ObjectOutputStream out = new ObjectOutputStream(clientRegister.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientRegister.getInputStream());
			Object rep = null;
		    
			try {
				rep = in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    System.out.println(rep);
		   
		    
			clientRegister.close();
			
			if(clientRegister.isClosed()){
				System.out.println("OPEN");
				while(true){
				try{
					
					serverParticipant = new ServerSocket(4000);
				
					Socket socketParticipante = serverParticipant.accept();
					out.writeObject("Entrou");
					System.out.println("Started");
					System.out.println("[Conexao de no membro] " +
	                        socketParticipante.getInetAddress().getHostName() +
	                        "@" +
	                        socketParticipante.getInetAddress().getHostAddress() +
	                        " " +
	                        socketParticipante.getLocalPort() +
	                        ":" +
	                        socketParticipante.getPort());
					new NoParticipante(socketParticipante).start();
				}catch (IOException e) {
			}} }
    	}
			catch (IOException e) {
		} 
			
    	}
}
	