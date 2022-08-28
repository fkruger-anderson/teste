import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author 07337354959
 */
public class Curso {
    private int codigo;
    private String nome;
    private LocalDate dataCriacao;
    private String descricao;

    private List<Turma> turmas;
    private List<Professor> professores;

    private static int geradorTurma = 0;

    public Curso(int codigo, String nome, String dataCriacao, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataCriacao = LocalDate.parse(dataCriacao, dtf);
        this.descricao = descricao;
        this.turmas = new ArrayList<>();
        this.professores = new ArrayList<>();
    }

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Optional<Turma> buscarTurma(int codigo) {
        return turmas.stream()
                     .filter(t -> t.getCodigo() == codigo)
                     .findFirst();
    }

    public boolean addTurma(String nome, int qtdVagas, Professor prof) {
        return turmas.add(new Turma(++geradorTurma, nome, qtdVagas, prof));
    }

    public Optional<Professor> buscarProfessor(String matricula) {
        return professores.stream()
                          .filter(p -> p.getMatricula()
                                        .equalsIgnoreCase(matricula))
                          .findFirst();
    }

    public String matriculadasAluno(String matricula) {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrícula: ").append(matricula).append("\n")
          .append(turmas.stream()
                        .filter(t -> !t.buscarAluno(matricula).isEmpty())
                        .map(Turma::toString)
                        .collect(Collectors.joining("\n")));
        return sb.toString();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public String listarTurmas() {
        return turmas.stream()
                     .map(Turma::infosCompletaTurma)
                     .collect(Collectors.joining("\n\n"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCodigo()).append(" - ").append(getNome())
          .append(", Criação: ").append(getDataCriacao().format(dtf)).append(", ")
          .append(getDescricao()).append("\n")
          .append("\nTurmas:\n")
          .append(listarTurmas());

        return sb.toString();
    }
}
