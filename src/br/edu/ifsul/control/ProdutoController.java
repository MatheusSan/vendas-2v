package br.edu.ifsul.control;

import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.model.Produto;

import java.util.List;
import java.util.Scanner;

public class ProdutoController {
    private ProdutoDAO  produtoDAO = new ProdutoDAO();
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        ProdutoController telaProduto = new ProdutoController();
        int opcao = 0;
        do{
            System.out.println("\n\n******** Produto ********");
            System.out.print(
                    "1. Inserir" +
                    "\n2. Alterar" +
                    "\n3. Excluir (tornar inativo)" +
                    "\n4. Lista todos os produtos (ativos e inativos)" +
                    "\n5. Lista todos os produtos (ativos)" +
                    "\n6. Listar produtos pelo nome" +
                    "\n7. Listar produto pelo código" +
                    "\nDigite a opção (0 para sair): "
            );
            opcao = s.nextInt();
            switch (opcao){
                case 1:
                    telaProduto.insert();
                    break;
                case 2:
                    telaProduto.update();
                    break;
                case 3:
                    telaProduto.tornarInativo();
                    break;
                case 4:
                    telaProduto.listarProdutos();
                    break;
                case 5:
                    telaProduto.listarProdutosAtivos();
                    break;
                case 6:
                    telaProduto.localizarPorNome();
                    break;
                case 7:
                    telaProduto.localizarPeloCodigo();
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        }while (opcao != 0);
    }

    /***** Métodos Utilitários ******/


    private void insert(){
        Produto produto = new Produto();
        System.out.println("Nome do produto: ");
        produto.setNome(s.next());
        System.out.println("Descricao: ");
        produto.setDescricao(s.next());
        System.out.println("Valor: ");
        produto.setValor(s.nextDouble());
        System.out.println("Estoque: ");
        produto.setEstoque(s.nextInt());
        if (produtoDAO.insert(produto)){
            System.out.println("Produto inserido com sucesso!");
        }else{
            System.out.println("Falha na inserção do produto!");
        }
    }

    private void update(){
        Produto produto;
        int opcao;
        do {
            opcao = 1;
            System.out.println("Digite o codigo do produto ou 0 para sair: ");
            produto = produtoDAO.getProdutoId(s.nextInt());
            if (produto == null){
                System.out.println("Id inválido: ");
            }else{
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Alterar? (1-sim / 0-não): ");
                if (s.nextInt() == 1){
                    System.out.println("Digite o novo nome: ");
                    produto.setNome(s.next());
                }
                System.out.println("Descricao: " + produto.getDescricao());
                System.out.println("Alterar? (1-sim / 0-não): ");
                if (s.nextInt() == 1){
                    System.out.println("Digite a nova descricao: ");
                    produto.setDescricao(s.next());
                }
                System.out.println("Valor: " + produto.getValor());
                System.out.println("Alterar? (1-sim / 0-não): ");
                if (s.nextInt() == 1){
                    System.out.println("Digite o novo valor: ");
                    produto.setValor(s.nextDouble());
                }
                System.out.println("Estoque: " + produto.getEstoque());
                System.out.println("Alterar? (1-sim / 0-não): ");
                if (s.nextInt() == 1){
                    System.out.println("Digite o valor do estoque: ");
                    produto.setEstoque(s.nextInt());
                }
                System.out.println("Situacao: " + produto.getSituacao());
                System.out.println("Alterar? (1-sim / 0-não): ");
                if (s.nextInt() == 1){
                    System.out.println("Digite a nova situacao: ");
                    produto.setSituacao(s.nextBoolean());
                }
                if (produtoDAO.update(produto)){
                    System.out.println("Produto atualizado: " + produtoDAO.getProdutoId(produto.getId()));
                }else{
                    System.out.println("Erro ao atualizar o produto "+produto.getNome());
                }
                opcao = 0;
            }
        }while (produto == null || opcao !=0);
    }

    private void tornarInativo() {
        System.out.print("Digite o código do produto: ");
        int codigo = s.nextInt();
        Produto produto = produtoDAO.getProdutoId(codigo);
        if(produto != null){
            System.out.println(produto);
            System.out.println("Confirmar a operação? (0-sim/1-não)");
            if(s.nextInt() == 0){
                if(produtoDAO.softDelete(produto)) System.out.println(produtoDAO.getProdutoId(codigo));
            }else{
                System.out.println("Operação cancelada.");
            }
        }else{
            System.out.println("Código não localizado.");
        }
    }


    private void listarProdutos() {
        System.out.println("\n****Lista de produtos Ativos: " + produtoDAO.getProdutoSituacao(true));
        System.out.println("\n****Lista de produtos Inativos: " + produtoDAO.getProdutoSituacao(false));
    }

    private void listarProdutosAtivos(){
        System.out.println("\n****Lista de produtos Ativos: " + produtoDAO.getProdutoSituacao(true));
    }


    private void localizarPorNome() {
        System.out.print("Digite o nome do produto: ");
        String nome = s.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Produto> produtos = produtoDAO.getProdutoName(nome);
        if(produtos.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.print(produtos);
        }
    }


    private void localizarPeloCodigo() {
        System.out.print("Digite o código do produto: ");
        Produto produto = produtoDAO.getProdutoId(s.nextInt());
        if(produto != null){
            System.out.print(produto);
        }else{
            System.out.println("Código não localizado.");
        }
    }
}
