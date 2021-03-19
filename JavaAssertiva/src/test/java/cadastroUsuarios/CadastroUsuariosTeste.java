package cadastroUsuarios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class CadastroUsuariosTeste {
    CadastroUsuarios novoCadastro = new CadastroUsuarios();

    @Before
    public void limparBanco() throws SQLException {
        novoCadastro.deletarTodos();
    }

    @Test(expected = Exception.class)
    public void TestInserirUsuarioNomeVazio() throws Exception {
        Usuario usuario = new Usuario("", "nayalla_@dominio.com", "12345");
        novoCadastro.inserirUsuario(usuario);
    }

    @Test(expected = Exception.class)
    public void TestInserirUsuarioEmailVazio() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "", "12345");

        novoCadastro.inserirUsuario(usuario);
    }

    @Test(expected = Exception.class)
    public void TestInserirUsuarioSenhaVazia() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nay_alla@dominio.com", "");

        novoCadastro.inserirUsuario(usuario);

    }

    @Test(expected = Exception.class)
    public void TestInserirEmailIgual() throws Exception{
        Usuario usuarioRepetido = new Usuario("Nayalla", "nayalla@dominio.com", "12345");

        novoCadastro.inserirUsuario(usuarioRepetido);
        novoCadastro.inserirUsuario(usuarioRepetido);
    }

    @Test
    public void TestInserirUsuario() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla1@dominio.com", "12345");

        novoCadastro.inserirUsuario(usuario);

        Usuario usuarioSalvo = novoCadastro.buscarUsuarioPorId(usuario.getId());

        Assert.assertEquals(usuario.getId(), usuarioSalvo.getId());
        Assert.assertEquals(usuario.getNome(), usuarioSalvo.getNome());
        Assert.assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
        Assert.assertEquals(usuario.getSenha(), usuarioSalvo.getSenha());
        Assert.assertNotNull(usuarioSalvo.getDataCadastro().getTime());

    }

    @Test
    public void TestAtualizarUsuario() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla2@dominio.com", "12345");
        novoCadastro.inserirUsuario(usuario);

        usuario.setNome("Nayalla Lima");
        usuario.setEmail("nayalla@dominio.com.br");

        novoCadastro.atualizarUsuario(usuario);

        Usuario usuarioAtualizado = novoCadastro.buscarUsuarioPorId(usuario.getId());

        Assert.assertEquals(usuario.getNome(), usuarioAtualizado.getNome());
        Assert.assertEquals(usuario.getEmail(), usuarioAtualizado.getEmail());
    }

    @Test
    public void TestDeletarUsuario() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla3@dominio.com", "12345");

        novoCadastro.inserirUsuario(usuario);
        novoCadastro.deletarUsuario(usuario.getId());

        Usuario usuarioDeletado = novoCadastro.buscarUsuarioPorId(usuario.getId());

        Assert.assertNull(usuarioDeletado);

    }

    @Test
    public void TestBuscarUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla4@dominio.com", "12345");

        novoCadastro.inserirUsuario(usuario);

        Usuario usuarioProcurado = novoCadastro.buscarUsuarioPorId(usuario.getId());

        Assert.assertEquals(usuario.getNome(), usuarioProcurado.getNome());
        Assert.assertEquals(usuario.getEmail(), usuarioProcurado.getEmail());
        Assert.assertEquals(usuario.getSenha(), usuarioProcurado.getSenha());
        Assert.assertNotNull(usuarioProcurado.getDataCadastro().getTime());

    }

    @Test
    public void TestBuscarUsuario() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla5@dominio.com", "12345");
        Usuario usuario2 = new Usuario("Nay", "nayalla6@dominio.com.br", "56789");

        novoCadastro.inserirUsuario(usuario);
        novoCadastro.inserirUsuario(usuario2);

        List<Usuario> listaUsuarios = novoCadastro.buscarUsuarios();

        Assert.assertEquals(listaUsuarios.size(), 2);
    }

    @Test
    public void TestBuscarUsuarioPalavra() throws Exception {
        Usuario usuario = new Usuario("Nayalla", "nayalla7@dominio.com", "12345");
        Usuario usuario2 = new Usuario("Nay", "nayalla8@dominio.com.br", "56789");
        Usuario usuario3 = new Usuario("Leonardo", "leonardo@dominio.com.br", "02468");
        novoCadastro.inserirUsuario(usuario);
        novoCadastro.inserirUsuario(usuario2);
        novoCadastro.inserirUsuario(usuario3);

        List<Usuario> listaUsuariosPalavra = novoCadastro.buscarUsuariosPalavra("Nay");

        Assert.assertEquals(listaUsuariosPalavra.size(), 2);

    }

}
