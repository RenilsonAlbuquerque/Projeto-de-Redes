package service;


public class PaginaHtm{

	private final String nome = "index.htm";
    private final String conteudo = "<htm>  <LIN><TAM 8><COR blue> Trabalho de redes </COR></TAM></LIN> "
	                           +"<LIN><SUB><NEG><TAM 30><COR red> Sou de Nova Descoberta Maluco  </COR></TAM></NEG></SUB></LIN>  "
	                           + "<LIN><NEG><TAM 50><COR black> Vai Corinthians   </COR></TAM></NEG></LIN></htm>";
   
    
    
	public String getConteudo() {
		return conteudo;
	}
	public String getNome() {
		return nome;
	}
	
    
}

