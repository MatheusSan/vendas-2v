package br.edu.ifsul.control;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.dao.ItemDAO;
import br.edu.ifsul.dao.PedidoDAO;
import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;

import java.util.Scanner;

public class PedidoController {
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        PedidoController telaPedido = new PedidoController();
        int opcao = 0;
        do{
            System.out.println("\n\n******** Pedidos ********");
            System.out.print(
                    "1. Check-out do pedido" +
                            "\n2. Enviar Pedido" +
                            "\n3. Excluir Pedido" +
                            "\n4. Lista todos os pedidos (inativos)" +
                            "\n5. Lista todos os pedidos (ativos)" +
                            "\n6. Lista todos os pedidos por período" +
                            "\n7. Listar todos os pedidos por cliente" +
                            "\n8. Excluir um item do pedido" +
                            "\n9. Listar pedido pelo Id" +
                            "\nDigite a opção (0 para sair): "
            );
            Scanner s = new Scanner(System.in);
            opcao = s.nextInt();
            switch (opcao){
                case 1:
                    telaPedido.checkOutPedido();
                    break;
                case 2:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 3:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 4:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 5:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 6:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 7:
                    System.out.println("em desenvolvimento " + opcao);
                    break;
                case 8:
                    telaPedido.softDeleteItem();
                    break;
                case 9:
                    telaPedido.listaPedidoById();
                    break;
                default:
                    if(opcao != 0) System.out.println("Opção inválida.");
            }
        }while (opcao != 0);
    }

    private void checkOutPedido(){
        System.out.println("Selecione o id do pedido");
        int id = s.nextInt();
        Pedido pedido = pedidoDAO.getPedidoById(id);

        if(pedido == null){
            System.out.println("Codigo invalido!");
        }
        if(!pedidoDAO.updateSituacao(pedido)){
            System.out.println("Pedido nao alterado");
        }else{
            System.out.println("---------------");
            System.out.println(pedidoDAO.getPedidoById(pedido.getId()));
        }
    }
    private void softDeleteItem() {
        PedidoDAO produtoDAO = new PedidoDAO();
        ItemDAO itemDAO = new ItemDAO();
        Pedido pedido = new Pedido();
        int idPedido = 0;
        int idItem = 0;
        int fleg = 0;
        do {
            System.out.println("Digite o id do pedido: ");
            idPedido = s.nextInt();
            pedido = pedidoDAO.getPedidoById(idPedido);
            if (pedido.getId() == 0){
                System.out.println("Codigo invalido");
                idPedido = 0;
            }else{
                System.out.println(pedido);
            }
        }while (idPedido == 0);
        do {
            System.out.println("Digite o id do item");
            idItem = s.nextInt();
            for (Item i : pedido.getItens()){
                if (i.getId() == idItem){
                    fleg = 2;
                }
            }
            if (fleg == 0){
                System.out.println("Id do item nao encontrado\nQuer continuar [0-sim/1-nao]");
                fleg = s.nextInt();
            }
        }while (fleg == 0);
        if (fleg == 2) {
            if(itemDAO.softDeleteItemByPedido(idPedido, idItem) == false){
                System.out.println("Nao foi possivel excluir o item");
            }else {
                System.out.println("Item exluido");
            }
        }
    }
    private void listaPedidoById(){
        int id;
        do {
            System.out.println("Digite o id do pedido: ");
            id = s.nextInt();
            Pedido pedido = pedidoDAO.getPedidoById(id);
            if (pedido.getId() == 0){
                System.out.println("Codigo invalido\nQuer continuar [0-sim/1nao]");
                id = s.nextInt();
            }else{
                System.out.println(pedido);
                id = 0;
            }
        }while (id != 1);
    }
}
