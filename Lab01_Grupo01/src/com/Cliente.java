package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try {
            Scanner serv = new Scanner(System.in);
            System.out.println("IP do servidor: ");
            String ip = serv.nextLine();
            System.out.println("Porta de ligação: ");
            int porta = serv.nextInt();

            Socket clientSocket = new Socket(ip, porta);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {

                String tarefa = serv.nextLine();
                out.println(tarefa);

                if (tarefa.equals("L")) {

                    String respostaL = in.readLine();
                    System.out.println(respostaL);
                    String lista = in.readLine();
                    System.out.println(lista);

                } else if (tarefa.equals("R")) {

                    String respostaR = in.readLine();
                    System.out.println(respostaR);
                    String nomeTarefa = serv.nextLine();
                    out.println(nomeTarefa);
                    String respostaRe = in.readLine();
                    System.out.println(respostaRe);

                } else if (tarefa.equals("Q")) {

                    String respostaQ = in.readLine();
                    System.out.println(respostaQ);
                    serv.close();
                    in.close();
                    out.close();
                    clientSocket.close();
                    break;
                }
                System.out.println("Introduza um comando: ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}