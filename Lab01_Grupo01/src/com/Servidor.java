package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    static ArrayList<String> tarefas = new ArrayList<>();
    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(4242);

            while(true) {

                System.out.println("Servidor espera ligacao cliente");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente Conectado");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {

                    String command = in.readLine();
                    String tarefasListadas = String.join(" ", tarefas);

                    if (command.equals("L")) {

                        out.println("Servidor: recebeu o comando " + command);
                        out.println("Lista de Tarefas: " + tarefasListadas);

                    } else if (command.equals("R")) {

                        out.println("Servidor: recebeu o comando " + command + " - Introduza a tarefa: ");
                        String tarefa = in.readLine();

                        if (tarefa.length() <= 120) {

                            tarefas.add(tarefa);
                            out.println("Tarefa adicionada com sucesso!");

                        } else {

                            out.println("Erro! Tarefa excede 120 carateres");
                        }
                    } else if (command.equals("Q")) {

                        out.println("Servidor: recebeu o comando " + command);
                        in.close();
                        out.close();
                        clientSocket.close();
                        break;
                    }
                }
                serverSocket.close();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}