package br.edu.infnet.jonathanapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.infnet.jonathanapi.model.domain.Armadura;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaduraService;

@Component
@Order(2)
public class ArmaduraLoader implements ApplicationRunner {

    private final ArmaduraService armaduraService;

    public ArmaduraLoader(ArmaduraService armaduraService) {
        this.armaduraService = armaduraService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("armaduras.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            Armadura armadura = new Armadura(linha, 0);
            armadura.setNome(campos[0]);
            armadura.setDefesa(Integer.valueOf(campos[1]));

            armaduraService.incluir(armadura);

            linha = leitura.readLine();
        }

        Collection<Armadura> armaduras = armaduraService.obterLista();
        System.out.println("\n=== Armaduras carregadas ===");
        armaduras.forEach(System.out::println);

        leitura.close();
    }
}
