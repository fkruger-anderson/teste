import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 * @author 07337354959
 */
public class Universidade {
    private String nome;
    private String CNPJ;
    private List<Curso> cursos;
    private static int geradorCurso = 0;

    public Universidade(String nome, String CNPJ) {
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.cursos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public Curso criarCurso(String nome, String dataCriacao, String descricao) {
        Curso curso = new Curso(++geradorCurso, nome, dataCriacao, descricao);
        cursos.add(curso);
        return curso;
    }

    public Optional<Curso> buscarCurso(int codigo) {
        return cursos.stream().filter(c -> c.getCodigo() == codigo).findFirst();
    }

    public boolean adicionarProfessor(int codCurso, String dataAdmissao, String nome, String dataNascimento, String CPF) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        if (curso != null) {
            return curso.getProfessores().add(new Professor(curso, dataAdmissao, nome, dataNascimento, CPF));
        } else {
            System.out.println("Curso não encontrado nesta Universidade");
            return false;
        }
    }

    public boolean criarTurma(int codCurso, String nome, int qtdVagas, String matriculaProf) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Professor prof = curso.buscarProfessor(matriculaProf).orElse(null);

        if (prof != null) {
            return curso.addTurma(nome, qtdVagas, prof);
        } else {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public boolean matricularAluno(int codCurso, int codTurma, Aluno aluno) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);

        if (turma != null) {
            if (turma.getAlunos().size() + 1 <= turma.getQtdVagas() && !turma.getAlunos().contains(aluno)) {
                return turma.getAlunos().add(aluno);
            } else {
                System.out.println("Excedeu os limites da turma, ou o aluno já encontra-se matriculado.");
                return false;
            }
        } else  {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public boolean registrarNotaAluno(int codCurso, int codTurma, String matricula, Double nota) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);
        Aluno aluno = turma.buscarAluno(matricula).orElse(null);

        if (aluno != null) {
            turma.getNotas().put(String.format("%s - %s", aluno.getMatricula(), aluno.getNome()), nota);
            return true;
        } else {
            System.out.println("Dados não encontrados nesta Universidade.");
            return false;
        }
    }

    public void calcMediaAlunos(int codCurso, int codTurma) {
        Curso curso = this.buscarCurso(codCurso).orElse(null);
        Turma turma = curso.buscarTurma(codTurma).orElse(null);
        Map<String, Double> notas = turma.getNotas();

//        Map<String,Double> average =
//                notas.entrySet().stream()
//                     .collect(Collectors.groupingBy(Map.Entry::getKey,
//                             Collectors.averagingDouble(value -> value.getValue())));
        Map<String,Double> average =
                notas.entrySet().stream()
                     .collect(groupingBy(Map.Entry::getKey,
                             averagingDouble(value -> value.getValue())));

        for (Map.Entry<String, Double> entry : average.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Universidade: ").append(getNome())
          .append("; CNPJ: ").append(getCNPJ())
          .append("\n\nCursos: \n").append(cursos.stream().map(Curso::toString).collect(Collectors.joining("\n\n")));
        return sb.toString();
    }
}
