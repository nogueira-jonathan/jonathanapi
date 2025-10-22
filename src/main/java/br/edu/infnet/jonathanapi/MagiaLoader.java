package br.edu.infnet.jonathanapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.jonathanapi.model.domain.Magia;
import br.edu.infnet.jonathanapi.model.domain.service.MagiaService;

@Component
@Order(3)
public class MagiaLoader implements ApplicationRunner {

    private final MagiaService magiaService;

    public MagiaLoader(MagiaService magiaService) {
        this.magiaService = magiaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("magias.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            Magia magia = new Magia(linha, 0, linha, 0);
            magia.setNome(campos[0]);
            magia.setDano(Integer.valueOf(campos[1]));
            magia.setRank(campos[2]);
            magia.setCustoMana(Integer.valueOf(campos[3]));

            magiaService.incluir(magia);

            linha = leitura.readLine();
        }

        Collection<Magia> magias = magiaService.obterLista();
        System.out.println("\n=== Magias carregadas ===");
        magias.forEach(System.out::println);

        leitura.close();
    }
}
