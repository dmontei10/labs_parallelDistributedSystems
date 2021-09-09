package com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class UtilizadorThread extends Thread{
	
	private static HashMap<Object, Object> hashMapCliente = new HashMap<Object, Object>();
	
    private Socket userSocket;
    private String Resp;
    
    public UtilizadorThread(Socket userSocket) {
        this.userSocket = userSocket;
    }

    public static HashMap<Object, Object> getHm() {
		return hashMapCliente;
	}
	
    public void run() {
		
		try {
			new NoParticipante(userSocket).start();
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
							
						if(hashMapCliente.containsKey(chave)){
								
							out.writeObject("Chave existente");
							
						}else{
								
							hashMapCliente.put(chave, valor);
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
							
						if(!hashMapCliente.containsKey(valorChave)){
								
							out.writeObject("Chave inexistente");
							
						}else{
								
							out.writeObject(hashMapCliente.get(valorChave));
							
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
							
						if(!hashMapCliente.containsKey(valorChave)){
								
							out.writeObject("Chave inexistente");
							
						}else{
								
							hashMapCliente.remove(valorChave);
							out.writeObject("item removido com sucesso");
							out.writeObject(hashMapCliente);
							
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
}