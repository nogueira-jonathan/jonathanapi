package br.edu.infnet.jonathanapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.jonathanapi.model.domain.Jogador;
import br.edu.infnet.jonathanapi.model.domain.Personagem;
import br.edu.infnet.jonathanapi.model.domain.service.JogadorService;

@Component
public class JogadorLoader implements ApplicationRunner {

	private final JogadorService jogadorService;
	
	public JogadorLoader(JogadorService jogadorService) {
		this.jogadorService = jogadorService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		FileReader arquivo = new FileReader ("personagens_listagem.txt");
		BufferedReader leitura = new BufferedReader(arquivo);
		
		String linha = leitura.readLine();
		String[] campos = null;
		
		while (linha != null) {
			campos = linha.split(";");
			
			Jogador jogador = new Jogador();
			
			jogador.setNome(campos[0]);
			jogador.setVida(Integer.valueOf(campos[1]));
			jogador.setForca(Integer.valueOf(campos[2]));
			jogador.setConstituicao(Integer.valueOf(campos[3]));
			jogador.setAgilidade(Integer.valueOf(campos[4]));
			jogador.setDestreza(Integer.valueOf(campos[5]));
			
			jogadorService.incluir((Jogador) jogador);						
			
			linha = leitura.readLine();
			
		}
		
		Collection<Jogador> jogadores = jogadorService.obterLista();
		jogadores.forEach(System.out::println);
		
		
		leitura.close();
	}

}
