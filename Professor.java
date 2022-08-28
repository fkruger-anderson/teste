package entities;

import java.time.LocalDate;

/**
 *
 * @author 07337354959
 */
public class Professor extends Pessoa {
    private static long contar = 0;
    private String matricula;
    private Curso curso;
    private LocalDate dataAdmissao;

    public Professor(Curso curso, String dataAdmissao, String nome, String dataNascimento, String CPF) {
        super(nome, dataNascimento, CPF);
        this.matricula = String.format("P%d%d", LocalDate.parse(dataAdmissao, dtf).getYear(), ++contar);
        this.curso = curso;
        this.dataAdmissao = LocalDate.parse(dataAdmissao, dtf);
    }

    public String getMatricula() {
        return matricula;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    @Override
    public String toString() {
        return String.format("Professor: %s - %s, Admissão: %s, Curso: %s",
                getMatricula(),
                super.toString(),
                getDataAdmissao().format(dtf),
                this.curso.getNome());
    }
}