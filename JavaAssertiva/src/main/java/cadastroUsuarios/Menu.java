package cadastroUsuarios;

import java.util.Date;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        CadastroUsuarios novoCadastro = new CadastroUsuarios();
        Scanner leitor = new Scanner(System.in);

        boolean repetir = true;
        while (repetir) {
            System.out.println("\n===========================MENU===========================");
            System.out.println("Qual atividade deseja realizar? \n1 - Cadastrar um usuário. \n2 - Procurar um usuário." +
                    "\n3 - Listar todos os usuários cadastrados. \n4 - Alterar dados do usuário.\n5 - Excluir usuário.");
            System.out.println("----------------------------------------------------------");
            int resposta = leitor.nextInt();
            leitor.nextLine();
            try {
                switch (resposta) {
                    case 1 -> {
                        System.out.println("\nDigite o nome do usuário.");
                        String nome = leitor.nextLine();

                        System.out.println("\nDigite o e-mail do usuário.");
                        String email = leitor.nextLine();

                        System.out.println("\nDigite a senha do usuário.");
                        String senha = leitor.nextLine();

                        Date dataCadastro = new Date();

                        Usuario novoUsuario = new Usuario(nome, email, senha);


                        novoCadastro.inserirUsuario(novoUsuario);


                        System.out.println("Usuário cadastrado com sucesso.");
                    }
                    case 2 -> {
                        String palavra = "";
                        System.out.println("\nDigite o nome ou o email do usuário que deseja encontrar.");
                        palavra = leitor.nextLine();

                        novoCadastro.buscarUsuariosPalavra(palavra);
                    }
                    case 3 -> {
                        novoCadastro.buscarUsuarios();
                    }
                    case 4 -> {
                        System.out.println("\nDeseja atualizar o nome do usuário? Sim, digite o nome. Não, dê enter.");
                        String nome = leitor.nextLine();

                        System.out.println("\nDigite atualizar o e-mail do usuário? Sim, digite o e-mail. Não, dê enter.");
                        String email = leitor.nextLine();

                        System.out.println("\nDigite atualizar a senha do usuário? Sim, digite a senha. Não, dê enter.");
                        String senha = leitor.nextLine();

                        Usuario usuarioAtualizado = new Usuario(nome, email, senha);
                        novoCadastro.atualizarUsuario(usuarioAtualizado);
                        System.out.println("Usuário atualizado com sucesso.");
                    }
                    case 5 -> {
                        System.out.println("\nDigite o id do usuário que deseja excluir.");
                        byte id = leitor.nextByte();
                        novoCadastro.deletarUsuario(id);
                    }
                    default -> System.out.println("\nResposta inválida.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\nDeseja realizar uma nova operação? 1 - Sim ou 2 - Não");
            resposta = leitor.nextByte();
            if (resposta != 1) {
                repetir = false;
            }
        }
        System.out.println("\nPrograma finalizado com sucesso.");
    }

}
