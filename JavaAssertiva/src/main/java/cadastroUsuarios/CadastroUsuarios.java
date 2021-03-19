package cadastroUsuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroUsuarios {
    private String nomeTabela = "usuarios";
    private Connection connection;

    public Statement conexaoBancoDados() {
        try {
            String URL = "jdbc:mysql://localhost:3306/cadastrocliente";
            String usuario = "[seu usuário]";
            String senha = "[sua senha]";

            connection = DriverManager.getConnection(URL, usuario, senha);
            Statement statement = connection.createStatement();

            return statement;
        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private void validarUsuario(Usuario usuario) throws Exception {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new Exception("Não é possível inserir um campo vazio, preencha corretamente o nome.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new Exception("Não é possível inserir um campo vazio, preencha corretamente o e-mail.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new Exception("Não é possível inserir um campo vazio, preencha corretamente a senha.");
        }
    }

    private void validarEmailUsuario(Usuario usuario) throws Exception {
        Statement statement = conexaoBancoDados();

        statement.executeQuery("SELECT email FROM " + nomeTabela + " WHERE email LIKE '" + usuario.getEmail()
                + "';");

        ResultSet set = statement.getResultSet();

        if (set.next() && set.getString("email") != null) {
            throw new Exception("Já existe um cadastro com o email " + usuario.getEmail());
        }

        statement.close();
        connection.close();
    }

    public void inserirUsuario(Usuario usuario) throws Exception {
        this.validarUsuario(usuario);

        this.validarEmailUsuario(usuario);

        Statement statement = conexaoBancoDados();

        String queryMaxId = "select max(id) from usuarios";
        statement.execute(queryMaxId);

        ResultSet set = statement.getResultSet();
        if (set.next()) {
            usuario.setId(set.getInt("MAX(id)") + 1);
        } else {
            usuario.setId(1);
        }

        String query = "INSERT INTO " + nomeTabela + "(id, nome, email, senha, dataCadastro)" +
                "VALUES ('" + usuario.getId() + "','" + usuario.getNome() + "', '" + usuario.getEmail() + "','"
                + usuario.getSenha() + "','" + usuario.getDataCadastro() + "')";

        statement.execute(query);

        statement.close();
        connection.close();
    }

    public void atualizarUsuario(Usuario usuario) throws Exception {
        Statement statement = conexaoBancoDados();

        Usuario salvo = buscarUsuarioPorId(usuario.getId());
        if (salvo != null) {
            salvo.setNome(usuario.getNome().isEmpty() ? salvo.getNome() : usuario.getNome());
            salvo.setEmail(usuario.getEmail().isEmpty() ? salvo.getEmail() : usuario.getEmail());
            salvo.setSenha(usuario.getSenha().isEmpty() ? salvo.getSenha() : usuario.getSenha());

            String query = "UPDATE " + nomeTabela + " SET nome='" + salvo.getNome() + "', email='" + salvo.getEmail() + "'," +
                    " senha='" + salvo.getSenha() + "' WHERE id = " + salvo.getId() + ";";

            statement.execute(query);
        } else {
            throw new Exception("Não existe usuário correspondente a " + usuario.getId());
        }
        statement.close();
        connection.close();
    }

    public void deletarUsuario(int id) throws SQLException {
        Statement statement = conexaoBancoDados();
        statement.execute("DELETE FROM " + nomeTabela + " WHERE id =" + id);
        statement.close();
        connection.close();
    }

    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        Statement statement = conexaoBancoDados();
        statement.executeQuery("SELECT * FROM " + nomeTabela + " WHERE id =" + id);

        ResultSet set = statement.getResultSet();
        Usuario usuario = null;
        while (set.next()) {
            System.out.printf("%d: %s - %s - %s- %s\n",
                    set.getInt("id"),
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"),
                    set.getDate("dataCadastro")

            );
            Date date = set.getDate("dataCadastro");
            usuario = new Usuario(
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"))
            ;
            usuario.setId(set.getInt("id"));
        }
        statement.close();
        connection.close();
        return usuario;
    }

    public List<Usuario> buscarUsuarios() throws SQLException {
        Statement statement = conexaoBancoDados();
        statement.executeQuery("SELECT * FROM " + nomeTabela);

        List<Usuario> listaUsuarios = new ArrayList<>();

        ResultSet set = statement.getResultSet();
        while (set.next()) {
            System.out.printf("%d: %s - %s - %s- %s\n",
                    set.getInt("id"),
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"),
                    set.getDate("dataCadastro")
            );
            Date date = set.getDate("dataCadastro");
            Usuario usuario = new Usuario(
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"));
            usuario.setId(set.getInt("id"));
            listaUsuarios.add(usuario);
        }
        statement.close();
        connection.close();
        return listaUsuarios;
    }

    public List<Usuario> buscarUsuariosPalavra(String palavra) throws SQLException {

        Statement statement = conexaoBancoDados();
        statement.executeQuery("SELECT * FROM " + nomeTabela + " WHERE nome like '%" + palavra + "%'");

        List<Usuario> listaUsuarios = new ArrayList<>();

        ResultSet set = statement.getResultSet();
        while (set.next()) {
            System.out.printf("%d: %s - %s - %s- %s\n",
                    set.getInt("id"),
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"),
                    set.getDate("dataCadastro")
            );
            Date date = set.getDate("dataCadastro");
            Usuario usuario = new Usuario(
                    set.getString("nome"),
                    set.getString("email"),
                    set.getString("senha"));
            usuario.setId(set.getInt("id"));
            listaUsuarios.add(usuario);
        }
        statement.close();
        connection.close();
        return listaUsuarios;
    }

    /**
     * metodo criado apenas para limpar o banco de dados para executar o teste
     *
     * @throws SQLException
     */

    public void deletarTodos() throws SQLException {
        Statement statement = conexaoBancoDados();
        statement.execute("DELETE FROM " + nomeTabela);
    }

}
