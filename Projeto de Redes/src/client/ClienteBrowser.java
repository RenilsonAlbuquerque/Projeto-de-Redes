package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClienteBrowser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lbCoteudo;
    private JButton pesquisar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteBrowser frame = new ClienteBrowser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteBrowser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 421);
		this.setTitle("GUGOL KROME");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setBounds(46, 21, 480, 20);
		textField.setText("GET /index.htm HTTP/1.0");
		contentPane.add(textField);
		textField.setColumns(10);
		
		pesquisar = new JButton("Pesquisar");
		pesquisar.setLayout(null);
		pesquisar.setBounds(550, 21, 100, 20);
		pesquisar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				requisitar(textField.getText());
			}
			
		});
		pesquisar.setVisible(true);
		contentPane.add(pesquisar);
		
	    lbCoteudo = new JLabel("");
		lbCoteudo.setBounds(10, 52, 597, 319);
		contentPane.add(lbCoteudo);
	}
	public void requisitar(String requisicao){
		Socket cliente = null;
	    try
	    {
	      
	      cliente = new Socket("192.168.56.1",12345); 
	      DataInputStream in = new DataInputStream(cliente.getInputStream());
	      DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
	     
	 
	      
	      out.writeUTF(requisicao);
	      out.flush();
	       
	      

	      this.lbCoteudo.setText(tratarPagina(in.readUTF()));
	     
	     
	      
	     
	    }
	    catch(IOException e)
	    { 
	    	this.lbCoteudo.setText(e.getMessage());
	      System.out.println("Erro: "+e.getMessage()); 
	    }
	    finally
	    {
	      try {if(cliente !=null) cliente.close();} catch(Exception e2){}
	    }
	    
	    
	  
	}
	private  String tratarPagina(String pagina){
	    String status = "";
	    int i = 0;
	    
	    
		for(i = 0; i < pagina.indexOf('*'); i++ ){
			status += pagina.charAt(i);
			}
		
		
		if(status.contains("200")){
			pagina = tratarHtm(pagina.substring(i+1,pagina.length()).trim());
		}
		else{
		   if(status.contains("400")){
			pagina = null;   
			pagina = tratarHtm("<htm><TAM 20> Desculpe mas a requisição é inválida</TAM></htm>");
			
		  }
		  if(status.contains("404")){
			pagina = null;
			pagina = tratarHtm("<htm><TAM 20> Desculpe, mas não conseguimos encontrar a página solicitada</TAM> </htm>");
		  }
		}
		return pagina;
	}
	
	private  String  tratarHtm(String pagina){
		String resultado = "";
		String tag = "";
		int ultimaPosicao = 0;
		
		for(int i = 0; i < pagina.length();i++){
			
			if(pagina.charAt(i) == '<')
			{
				
				resultado += pagina.substring(ultimaPosicao, i);
				for(int j = i + 1;j <= pagina.indexOf('>'); j++){
					if(pagina.charAt(j) == '>'){
						ultimaPosicao = j;
						
					}
					else{
						
						tag += pagina.charAt(j);
					}
				}
				
				resultado += traduzirTags(tag);
				tag = "";
				pagina = pagina.replaceFirst("<", " ");
				pagina = pagina.replaceFirst(">", " ");
			}
			
		}
		
		return resultado;
		
	}
	private String traduzirTags(String tag){
		String resultado = null;
		
		if(tag.contains("htm")){
			if(tag.contains("/"))
				resultado = "</html>";
			else
			    resultado = "<html>";
		}
		if(tag.contains("NEG")){
			if(tag.contains("/"))
				resultado = "</b>";
			else
			    resultado = "<b>";
		}
		if(tag.contains("ITA")){
			if(tag.contains("/"))
				resultado = "</i>";
			else
			    resultado = "<i>";
		}
		if(tag.contains("TAM")){
			if(tag.contains("/"))
				resultado = "</font>";
			else{
				if(tag.contains("/") == false){
			     resultado = "<font size =";
			     resultado += tag.substring(tag.indexOf('M') +1 , tag.length()) + ">";
				}
			}
		}
		if(tag.contains("COR")){
			if(tag.contains("/"))
				resultado = "</font>";
			else{
				if(tag.contains("/") == false){
			     resultado = "<font color =";
			     resultado += tag.substring(tag.indexOf('R') +1 , tag.length()) + ">";
				}
			}
		}
		if(tag.contains("SUB")){
			if(tag.contains("/"))
				resultado = "</u>";
			else
			    resultado = "<u>";
		}
		if(tag.contains("LIN")){
			if(tag.contains("/"))
			  resultado = "</li>";
			else
			  resultado = "<li>";
		}
		return resultado;
		
			
	}
}
