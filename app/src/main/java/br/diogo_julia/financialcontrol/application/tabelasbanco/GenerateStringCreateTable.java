package br.diogo_julia.financialcontrol.application.tabelasbanco;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Iterator;

public class GenerateStringCreateTable {

    SQLiteDatabase db;
    public GenerateStringCreateTable(SQLiteDatabase db) {
        this.db = db;
    }

    public void gerar() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        // Passa por todas as tabelas.
        Iterator<br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas> tabelas = EnumSet.allOf(br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas.class).iterator();
        while(tabelas.hasNext()) {
            br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas tabela = tabelas.next();
            // Inicia a criação do SQL.
            StringBuilder sqlDeCriacao = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            sqlDeCriacao.append(tabela);
            sqlDeCriacao.append("(");
            // Passa por todas as colunas.
            // Aqui, não dá para definir um iterator do tipo específico das
            // colunas, pois não sabemos qual é esse tipo em tempo de compilação.
            Iterator colunas = EnumSet.allOf(tabela.getEnumeracao()).iterator();
            while(colunas.hasNext()) {
                Object enumColuna = colunas.next();
                // Acessa o conteúdo do atributo coluna.
                Field atributoColuna = enumColuna.getClass().getDeclaredField("coluna");
                atributoColuna.setAccessible(true);
                Coluna coluna = (Coluna) atributoColuna.get(enumColuna);

                sqlDeCriacao.append(coluna);
                sqlDeCriacao.append(", ");
            }
            // Remove a vírgula final que sobrou.
            sqlDeCriacao.setLength(sqlDeCriacao.length()-2);
            // Finaliza o SQL.
            sqlDeCriacao.append(");");
            // Imprime o SQL.
            db.execSQL(sqlDeCriacao.toString());
            Log.d("CRIACAO_DE_TABELA", "Tabela "+ tabela + " criada com sucesso");
        }
    }
}
