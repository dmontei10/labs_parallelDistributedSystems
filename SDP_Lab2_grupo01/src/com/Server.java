package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class Server extends Thread{
	
	private static ArrayList<String> servers = new ArrayList<String>();
	
	private static SortedMap<Integer, String> sortedServers = new TreeMap<Integer, String>();
	private static HashMap<Object, Object> hashMapServer = new HashMap<Object, Object>();
	
	private final static int PORTPART = 4242;
	private final static int PORTUSER = 4243;
    private static ServerSocket serverRegister;
    private static ServerSocket serverUser;
    private final Socket userSocket;
    
    
    private String Resp;
    static int hash = 0;
    
    public Server(Socket userSocket) {
        this.userSocket = userSocket;
    }
    
    private static String getServer(String key) {
	
		int hash = getHash(key);
		SortedMap<Integer, String> subMap = sortedServers.tailMap(hash);
		if(subMap.isEmpty()){
			Integer i = sortedServers.firstKey();
			return sortedServers.get(i);
		}else{
			
			Integer i = subMap.firstKey();
			return subMap.get(i);
		}
	}
    
    private static void getRep(){
    	
		for (int i=0; i<servers.size(); i++) {
			int hash = getHash(servers.get(i));
			sortedServers.put(hash, servers.get(i));
		}
		System.out.println();
	}
    
    private static int getHash(String str) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++){
			sum = sum + str.charAt(i);
		}
		return sum % servers.size();
	}
    
    public void run() {
		
		try {
			
			ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
			Object command = null;
			Object chave = null;
			Object valor = null;
			Object valorChave = null;
			
			Resp = "Ligação Clientes Estabelecida";
			out.writeObject(Resp);
			
			while(true){
					
				command = in.readObject();
					
				if(command.equals("R")){
						
					try{
						out.writeObject("Servidor: recebeu o comando " + command + 
										" - Introduza um par chave-valor: ");
						
						chave = in.readObject();
						valor = in.readObject();
							
						if(hashMapServer.containsKey(chave)){
								
							out.writeObject("Chave existente");
							
						}else{
								
							hashMapServer.put(chave, valor);
							out.writeObject("Item registado com sucesso");
								
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
			
				}else if(command.equals("C")){
						
					try{
							
						out.writeObject("Servidor: recebeu o comando " + command + 
										" - Introduza a chave: ");
							
						valorChave = in.readObject();
							
						if(!hashMapServer.containsKey(valorChave)){
								
							out.writeObject("Chave inexistente");
							
						}else{
								
							out.writeObject(hashMapServer.get(valorChave));
							
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
						
				}else if(command.equals("D")){
						
					try{
							
						out.writeObject("Servidor: recebeu o comando " + command + 
										" - Introduza a chave: ");
							
						valorChave = in.readObject();
							
						if(!hashMapServer.containsKey(valorChave)){
								
							out.writeObject("Chave inexistente");
							
						}else{
								
							hashMapServer.remove(valorChave);
							out.writeObject("item removido com sucesso");
							
						}
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
						
				}else if(command.equals("Q")){
					try{
						in.close();
						out.close();
						userSocket.close();
						break;
					}catch(IOException ioe){
						Resp = "Ocorreu um erro";
						out.writeObject(Resp);
					}
				}	
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
		}
    }
    public static void main(String[] args) {
        try {
        	
            serverRegister = new ServerSocket(PORTPART);
            serverUser = new ServerSocket(PORTUSER);
            System.out.println("[started]");
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
            
        }
        
        servers.add(serverRegister.getInetAddress().getHostName() + ":" + serverRegister.getLocalPort());
        
        for(int i=0; i<2; i++){
            try {
            	
                Socket socketRegister = serverRegister.accept();
                
                System.out.println("[Conexão de nó membro] " +
                        socketRegister.getInetAddress().getHostName() +
                        "@" +
                        socketRegister.getInetAddress().getHostAddress() +
                        " " +
                        socketRegister.getLocalPort() +
                        ":" +
                        socketRegister.getLocalPort());
                
                new NoParticipanteThread(socketRegister).start();
        
                String ip = socketRegister.getInetAddress().getHostName();
               
                int porta = socketRegister.getLocalPort();
                
                servers.add(ip + ":" + porta);
                
            } catch (IOException ioe) {
                if (serverRegister.isClosed()) {
                    System.out.println("[terminado]");
                } else {
                    ioe.printStackTrace();
                }
            }
        }
        for(int j = 0 ; j < sortedServers.size();j++){
        	System.out.println(sortedServers.get(j));
        } 
        
        getRep();
        
        System.out.println("Aceita Utilizador");
        while(true){
	        try {
	        	
				Socket socketUser = serverUser.accept();
				new Server(socketUser).start();
	            
			} catch (IOException e) {
				e.printStackTrace();
				}
        }
    }
}