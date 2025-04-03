import java.util.ArrayList;
import java.util.Scanner;

class Produto {
    int codigo;
    String nome;
    float preco;

    Produto(int codigo, String nome, float preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    void infoProduto() {
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.printf("Preço: %.2f\n", preco);
    }
}

class CarrinhoItem {
    Produto produto;
    int quantidade;

    CarrinhoItem(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
}

public class Supermercado {
    private static final int MAX_PRODUTOS = 50;
    private static final int MAX_CARRINHO = 50;

    private static ArrayList<Produto> produtos = new ArrayList<>();
    private static ArrayList<CarrinhoItem> carrinho = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private static int lerNumeroInteiroPositivo() {
        while (true) {
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                if (num > 0) return num;
            }
            System.out.print("Entrada inválida. Tente novamente: ");
            scanner.nextLine(); // Limpa buffer
        }
    }

    private static float lerNumeroFloatPositivo() {
        while (true) {
            if (scanner.hasNextFloat()) {
                float num = scanner.nextFloat();
                if (num > 0) return num;
            }
            System.out.print("Entrada inválida. Tente novamente: ");
            scanner.nextLine();
        }
    }

    private static void cadastrarProduto() {
        if (produtos.size() >= MAX_PRODUTOS) {
            System.out.println("Não é possível cadastrar mais produtos.");
            return;
        }
        System.out.print("Digite o código do produto: ");
        int codigo = lerNumeroInteiroPositivo();
        for (Produto p : produtos) {
            if (p.codigo == codigo) {
                System.out.println("Produto com esse código já cadastrado.");
                return;
            }
        }
        System.out.print("Digite o nome do produto: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do produto: ");
        float preco = lerNumeroFloatPositivo();
        produtos.add(new Produto(codigo, nome, preco));
        System.out.println("Produto cadastrado com sucesso.");
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("Produtos cadastrados:");
        for (Produto p : produtos) {
            p.infoProduto();
            System.out.println();
        }
    }

    private static void comprarProduto() {
        System.out.print("Digite o código do produto: ");
        int codigo = lerNumeroInteiroPositivo();
        Produto p = produtos.stream().filter(prod -> prod.codigo == codigo).findFirst().orElse(null);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        for (CarrinhoItem item : carrinho) {
            if (item.produto.codigo == codigo) {
                item.quantidade++;
                System.out.println("Quantidade aumentada.");
                return;
            }
        }
        if (carrinho.size() >= MAX_CARRINHO) {
            System.out.println("Carrinho cheio.");
            return;
        }
        carrinho.add(new CarrinhoItem(p, 1));
        System.out.println("Produto adicionado ao carrinho.");
    }

    private static void visualizarCarrinho() {
        if (carrinho.isEmpty()) {
            System.out.println("O carrinho está vazio.");
            return;
        }
        System.out.println("Itens no carrinho:");
        float total = 0;
        for (CarrinhoItem item : carrinho) {
            System.out.println("Produto: " + item.produto.nome);
            System.out.println("Quantidade: " + item.quantidade);
            System.out.printf("Preço unitário: %.2f\n", item.produto.preco);
            float precoTotal = item.produto.preco * item.quantidade;
            System.out.printf("Preço total: %.2f\n\n", precoTotal);
            total += precoTotal;
        }
        System.out.printf("Total a pagar: %.2f\n", total);
    }

    private static void fecharPedido() {
        if (carrinho.isEmpty()) {
            System.out.println("O carrinho está vazio. Não é possível fechar o pedido.");
            return;
        }
        visualizarCarrinho();
        carrinho.clear();
        System.out.println("Pedido fechado com sucesso!");
    }

    private static void menu() {
        int opcao;
        do {
            System.out.println("\n=== Sistema de Supermercado ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Comprar Produto");
            System.out.println("4. Visualizar Carrinho");
            System.out.println("5. Fechar Pedido");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerNumeroInteiroPositivo();
            scanner.nextLine();
            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> comprarProduto();
                case 4 -> visualizarCarrinho();
                case 5 -> fecharPedido();
                case 6 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 6);
    }

    public static void main(String[] args) {
        menu();
    }
}
