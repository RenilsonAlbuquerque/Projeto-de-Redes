package service;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor
{
  private PaginaHtm pagina;
  private ServerSocket socketServidor;
  
  private static Servidor instance = null;
  
  private Servidor(){
	  
	  try{
		  this.socketServidor = new ServerSocket(12345);
		  this.pagina = new PaginaHtm();
		  System.out.println("Servidor iniciado");
	  }
	  catch(IOException e){
		  System.out.println(e.getMessage());
	  }
	  
  }
  
  public static Servidor getInstance(){
	  if(instance == null)
		  instance = new Servidor();
	  return instance;
  }
  
  public ServerSocket getSocketServidor() {
	return socketServidor;
  }

  private static String  tratarString(String requisicao) {
	  String resultado = requisicao.substring(5, requisicao.length() -9); 
	  if(resultado.trim().equals("")){
		  resultado = "index.htm";  
	  }
	  return resultado;
	  
  }
  
  protected String tratarRequisicao(String requisicao){
	  
	 
	  String codigo = null;
	  String status = null;
	  
	  if(requisicao.startsWith("GET /") && (requisicao.endsWith(" HTTP/1.1") || requisicao.endsWith(" HTTP/1.0") || requisicao.endsWith(" HTTP/0.9"))){
		   if(tratarString(requisicao).equals(pagina.getNome())){
			     codigo = "200";
			     status = "OK";
			  return "HTTP/1.0 " + codigo + " "+ status + "* "  + 
			          pagina.getConteudo(); 
		   }
		   else{
			    codigo = "404";
			    status = "HTTP Not Found";
			    return "HTTP/1.0 " + codigo + " "+ status + "* ";
		   }
		     
	   }
	   else{
		    codigo = "400";
		    status = "Bad Request";
		    return "HTTP/1.0 " + codigo + " "+ status + "* ";
	   }
		  
  }
}
