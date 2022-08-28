package entities;

import java.time.LocalDate;

/**
 *
 * @author 07337354959
 */
public class Aluno extends Pessoa {
    private static long contar = 0;
    private String matricula;

    public Aluno(String nome, String dataNascimento, String CPF) {
        super(nome, dataNascimento, CPF);
        this.matricula = String.format("A%s%d", LocalDate.parse(dataNascimento, dtf).getYear(), ++contar);
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return String.format("Aluno: %s - %s",
                matricula,
                super.toString());
    }
}