package br.edu.infnet.jonathanapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.jonathanapi.model.domain.Inimigo;
import br.edu.infnet.jonathanapi.model.domain.service.InimigoService;

@Component
@Order(4)
public class InimigoLoader implements ApplicationRunner {

    private final InimigoService inimigoService;

    public InimigoLoader(InimigoService inimigoService) {
        this.inimigoService = inimigoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("inimigos.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            Inimigo inimigo = new Inimigo();
            inimigo.setNome(campos[0]);
            inimigo.setVida(Integer.valueOf(campos[1]));
            inimigo.setForca(Integer.valueOf(campos[2]));
            inimigo.setConstituicao(Integer.valueOf(campos[3]));
            inimigo.setAgilidade(Integer.valueOf(campos[4]));
            inimigo.setDestreza(Integer.valueOf(campos[5]));

            inimigoService.incluir(inimigo);

            linha = leitura.readLine();
        }

        Collection<Inimigo> inimigos = inimigoService.obterLista();
        System.out.println("\n=== Inimigos carregados ===");
        inimigos.forEach(System.out::println);

        leitura.close();
    }
}
