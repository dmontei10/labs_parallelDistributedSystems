package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Utilizador {
    public static void main(String[] args) {
    	
    	try{
    		
	    	Scanner serv = new Scanner(System.in);
			System.out.println("IP do servidor: ");
			String ip = serv.nextLine();
			System.out.println("Porta de ligação: ");
			int porta = serv.nextInt();
		
			Socket clientSocket = new Socket(ip, porta);
			
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
		    
		    Object rep = null;
		    
			try {
				rep = in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    System.out.println(rep);
	    
		    while(!clientSocket.isClosed()){
		    	
		    	String tarefa = serv.nextLine();
			    out.writeObject(tarefa);
				
				if(tarefa.equals("R")){
					
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println(rep);
					
					String chave = serv.next();
					
					String valor = serv.nextLine();
					
					String chave2 = chave.trim();
					
					out.writeObject(chave2);
					out.writeObject(valor);
					
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println(rep);
					
				}
				else if(tarefa.equals("C")){
					
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println(rep);
					
			    	String valorChave = serv.nextLine();
			    	out.writeObject(valorChave);
			    	
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			    	System.out.println(rep);
				}
				else if(tarefa.equals("D")){
					
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println(rep);
					
			    	String chaveRem = serv.nextLine();
			    	out.writeObject(chaveRem);
			    
					try {
						rep = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			    	System.out.println(rep);
			    	
				}
				else if(tarefa.equals("Q")){
					
					serv.close();
				    in.close();
				    out.close();
				    clientSocket.close();
				    System.out.println("Terminado");
					break;
					
				}
				System.out.println("Introduza um comando: ");
		    }
		    
    	}catch (IOException ioe) {
    		
    		System.out.println("Ocorreu um erro");
    	}
    }
}
