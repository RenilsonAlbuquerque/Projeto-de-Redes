package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunService {
	 public static void main(String[] arg)
	 {
			DataInputStream in = null;
			Servidor servidor = null;
			DataOutputStream out = null;
			Socket cliente = null;
					
	//--------------------------------------------------Inicia a conexão e o servidor fica escutando--------------------------		
			while (true) {
				try {

					servidor = Servidor.getInstance();

					cliente = servidor.getSocketServidor().accept();

					in = new DataInputStream(cliente.getInputStream());
					out = new DataOutputStream(cliente.getOutputStream());

					out.writeUTF(servidor.tratarRequisicao(in.readUTF()));

				} catch (IOException e1) {

					e1.printStackTrace();

				} finally {
					/*
					if (servidor != null) {
						try {
							servidor.getSocketServidor().close();
						} catch (IOException e) {
							// Exceção silenciosa

						}
					}
					*/
				}

			}

		}
}
