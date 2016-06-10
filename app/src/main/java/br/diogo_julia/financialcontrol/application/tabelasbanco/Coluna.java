package br.diogo_julia.financialcontrol.application.tabelasbanco;

public class Coluna {
    private String nome;
    private Tipo tipo;
    private boolean chavePrimaria;
    private boolean autoIncremento;
    private boolean aceitaNulo;

    // Enumeração de tipos de colunas.
    public enum Tipo {

        INTEIRO("integer"), TEXTO("text"), DECIMAL("real");
        private String tipo;

        private Tipo(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return this.tipo;
        }

    };

    public Coluna(String nome, Tipo tipo) {
        this(nome, tipo, false);
    }

    public Coluna(String nome, Tipo tipo, boolean aceitaNulo) {
        this(nome, tipo, false, false, aceitaNulo);
    }

    public Coluna(String nome, Tipo tipo, boolean chavePrimaria, boolean autoIncremento, boolean aceitaNulo) {
        this.nome = nome;
        this.tipo = tipo;
        this.chavePrimaria = chavePrimaria;
        this.autoIncremento = autoIncremento;
        this.aceitaNulo = aceitaNulo;
    }

    // Métodos de acesso.
    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean isChavePrimaria() {
        return chavePrimaria;
    }

    public boolean isAutoIncremento() {
        return autoIncremento;
    }

    public boolean isAceitaNulo() {
        return aceitaNulo;
    }

    @Override
    public String toString() {
        StringBuilder representacaoTextual = new StringBuilder(nome);
        representacaoTextual.append(" ");
        representacaoTextual.append(tipo);
        if (chavePrimaria) {
            representacaoTextual.append(" primary key");
        }
        if (autoIncremento) {
            representacaoTextual.append(" autoincrement");
        }
        if (!aceitaNulo) {
            representacaoTextual.append(" not null");
        }
        return representacaoTextual.toString();
    }

}
