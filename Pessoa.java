package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author 07337354959
 */
public abstract class Pessoa {
    private String nome;
    private LocalDate dataNascimento;
    private String CPF;

    public Pessoa(String nome, String dataNascimento, String CPF) {
        this.nome = nome;
        this.dataNascimento = LocalDate.parse(dataNascimento, dtf);
        this.CPF = CPF;
    }
    
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getCPF() {
        return CPF;
    }

    @Override
    public String toString() {
        return String.format("%s, Data de Nasc.: %s, CPF: %s",
                getNome(),
                getDataNascimento().format(dtf),
                getCPF());
    }
}