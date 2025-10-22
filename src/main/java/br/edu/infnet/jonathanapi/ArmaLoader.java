package br.edu.infnet.jonathanapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.jonathanapi.model.domain.Arma;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaService;

@Component
@Order(1)
public class ArmaLoader implements ApplicationRunner {

    private final ArmaService armaService;

    public ArmaLoader(ArmaService armaService) {
        this.armaService = armaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("armas.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            Arma arma = new Arma(linha, 0, linha);
            arma.setNome(campos[0]);
            arma.setDano(Integer.valueOf(campos[1]));
            arma.setCategoria(campos[2]);

            armaService.incluir(arma);

            linha = leitura.readLine();
        }

        Collection<Arma> armas = armaService.obterLista();
        System.out.println("\n=== Armas carregadas ===");
        armas.forEach(System.out::println);

        leitura.close();
    }
}
