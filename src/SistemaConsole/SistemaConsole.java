/*
* User: admin
* Password: 123

* Developed by: Pedro Luca Prates
* e-mail: pedrolucaofc@outlook.com
* site: https://pedroluca.vercel.app
*/
package SistemaConsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Objetos.*;

public class SistemaConsole {
    static Caixa caixa = new Caixa(0);
    static ArrayList<Cliente> clientes = new ArrayList<>();
    static ArrayList<Produto> produtos = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // login();
        loadDefaultProductsAndClients();
    }

    public static void loadDefaultProductsAndClients() {
        Cliente cliente1 = new Cliente("Pedro", "123.123.123-12", 20);
        Cliente cliente2 = new Cliente("Lucas", "123.123.123-12", 19);
        Cliente cliente3 = new Cliente("João", "123.123.123-12", 20);
        Cliente cliente4 = new Cliente("Tharlis", "123.123.123-12", 18);
        clientes.addAll(Arrays.asList(cliente1, cliente2, cliente3, cliente4));
        LocalDate data1 = LocalDate.of(2024, 5, 20);
        ProdutoPerecivel produto1 = new ProdutoPerecivel("PP0001", "Feijão", 5.7, 7, data1);
        LocalDate data2 = LocalDate.of(2024, 5, 20);
        ProdutoPerecivel produto2 = new ProdutoPerecivel("PP0002", "Arroz", 3.4, 8, data2);
        LocalDate data3 = LocalDate.of(2024, 5, 20);
        ProdutoPerecivel produto3 = new ProdutoPerecivel("PP0003", "Macarrão", 7.34, 20, data3);
        LocalDate data4 = LocalDate.of(2024, 5, 20);
        ProdutoPerecivel produto4 = new ProdutoPerecivel("PP0004", "Tapioca", 15.99, 12, data4);
        LocalDate data5 = LocalDate.of(2024, 5, 20);
        ProdutoPerecivel produto5 = new ProdutoPerecivel("PP0005", "Banana", 7.89, 5, data5);
        produtos.addAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5));
        menu();
    }

    public static void menu() {
        int option = 0;
        System.out.println("<<= = = = = = = = MENU = = = = = = = ==>");
        System.out.println(" || 1- Mostrar produtos em estoque   ||");
        System.out.println(" || 2- Mostrar clientes              ||");
        System.out.println(" || 3- Cadastrar novo cliente        ||");
        System.out.println(" || 4- Cadastrar novo produto        ||");
        System.out.println(" || 5- Mostrar dinheiro em caixa     ||");
        System.out.println(" || 6- Vender produtos               ||");
        System.out.println(" || 7- Comprar produtos              ||");
        System.out.println(" || 8- Limpar tela                   ||");
        System.out.println(" || 9- Sair                          ||");
        System.out.println("<== = = = = = = = = = = = = = = = = ===>");
        while (option < 1 || option > 9) {
            System.out.println("Informe a opção desejada: ");
            option = scan.nextInt();
            if (option < 1 || option > 9) System.out.println("Opção inválida!");
        }
        switch (option) {
            case 1:
                showProduct();
                break;
            case 2:
                showCustomers();
                break;
            case 3:
                addNewCustomer();
                break;
            case 4:
                addNewProduct();
                break;
            case 5:
                System.out.println("\nO valor em caixa é de R$" + caixa.getSaldo() + "\n");
                menu();
                break;
            case 6:
                sellProducts();
                break;
            case 7:
                buyProducts();
                break;
            case 8:
                clearConsole();
                break;
            case 9:
                System.exit(0);
                break;
        }
    } 

    private static void showProduct() {
        int option;
        System.out.print("\n");
        System.out.println(imprimirProdutos());
        do {
            System.out.println("\n[0 voltar ao menu | 1 excluir produto]\nOpção: ");
            option = scan.nextInt();
        } while (option < 0 || option > 1);
        if (option == 0) menu(); 
        else deleteProduct();
    }
    
    private static void showCustomers() {
        int option;
        System.out.print("\n");
        System.out.println(imprimirClientes());
        do {
            System.out.println("\n[0 voltar ao menu | 1 excluir cliente]\nOpção: ");
            option = scan.nextInt();
        } while (option < 0 || option > 1);
        if (option == 0) menu(); 
        else deleteCustomer();
    }

    public static void addNewCustomer() {
        System.out.print("\nInforme o nome do novo cliente: ");
        String nomeCliente = scan.next();
        System.out.print("Informe o CPF do novo cliente: ");
        String cpfCliente = scan.next();
        System.out.println("Informe a idade do novo cliente: ");
        int idadeCliente = scan.nextInt();
        scan.nextLine();
        Cliente novoCliente = new Cliente(nomeCliente, cpfCliente, idadeCliente);
        clientes.add(novoCliente);
        menu();
    }

    public static void addNewProduct() {
        System.out.println("O novo produto é perecível ou não? [0- sim | 1- não]: ");
        int option = scan.nextInt();
        scan.nextLine();
        System.out.print("\nInforme o código, nome, preço e a quantidade do novo produto (respectivamente): ");
        String codigoProduto = scan.next();
        String nomeProduto = scan.next();
        double precoProduto = scan.nextDouble();
        scan.nextLine();
        int quantidadeProduto = scan.nextInt();
        scan.nextLine();
        if (option == 0) {
            System.out.println("Informe a data de vencimento do produto: ");
            String dataVencimentoProduto = scan.next();
            try {
                LocalDate dataVencimento = converterDataBrasileiraParaLocalDate(dataVencimentoProduto);
                ProdutoPerecivel novoProduto = new ProdutoPerecivel(codigoProduto, nomeProduto, precoProduto, quantidadeProduto, dataVencimento);
                produtos.add(novoProduto);
                System.out.println("Produto adicionado com sucesso!");
                menu();
            } catch (Exception e) {
                System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/mm/yyyy.");
                addNewProduct();
            }
        } else if (option == 1) {
            System.out.println("Informe o material do produto: ");
            String materialProduto = scan.next();
            ProdutoNaoPerecivel novoProduto = new ProdutoNaoPerecivel(codigoProduto, nomeProduto, precoProduto, quantidadeProduto, materialProduto);
            produtos.add(novoProduto);
            System.out.println("Produto adicionado com sucesso!");
            menu();
        }
    }

    public static LocalDate converterDataBrasileiraParaLocalDate(String dataInput) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataInput, formatoBrasileiro);
    }

    public static void deleteCustomer() {
        System.out.println("");
        System.out.println(imprimirClientes());
        System.out.println("Informe o CPF do cliente que deseja excluir [0 para voltar ao menu]: ");
        String option = scan.next();
        if (option.equals("0")) menu();
        Cliente clienteParaDeletar = encontrarClientePorCPF(option);
        if (clienteParaDeletar != null) {
            clientes.remove(clienteParaDeletar);
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Esse CPF não pertence a nenhum cliente!");
        }
        menu();
    }

    public static Produto encontrarProdutoPorCodigo(String codigoProduto) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigoProduto)) return produto;
        }
        return null;
    }

    public static Cliente encontrarClientePorCPF(String cpfCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(cpfCliente)) return cliente;
        }
        return null;
    }

    public static void deleteProduct() {
        System.out.println("");
        System.out.println(imprimirProdutos());
        System.out.println("Informe o código do produto que deseja excluir [0 para voltar ao menu]: ");
        String option = scan.next();
        if (option.equals("0")) menu();
        Produto produtoParaDeletar = encontrarProdutoPorCodigo(option);
        if (produtoParaDeletar != null) {
            produtos.remove(produtoParaDeletar);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Esse código não pertence a nenhum produto!");
        }
        menu();
    }

    public static void sellProducts() {
        Cliente clienteComprador;
        String cpfCliente;
        System.out.println(imprimirClientes());
        System.out.println("Informe o CPF do cliente que está comprando: ");
        do {
            cpfCliente = scan.nextLine();
            clienteComprador = encontrarClientePorCPF(cpfCliente);
            if (clienteComprador == null) System.out.println("O CPF informado não pertence a nenhum cliente cadastrado!");
            else break;
        } while (true);
    
        Venda novaVenda = new Venda(clienteComprador);
        int optionConfirm = 0;

        do {
            Produto produtoParaVender;
            String codigoProduto;
        System.out.println(imprimirProdutos());
            System.out.println("\n=> Informe o código do produto a ser vendido: ");
            codigoProduto = scan.next();
            produtoParaVender = encontrarProdutoPorCodigo(codigoProduto);
            if (produtoParaVender == null) System.out.println("O código informado não pertence a nenhum produto cadastrado!");
            else {
                System.out.println("Produto selecionado: " + produtoParaVender.getNome() + "\nPreço: R$" + produtoParaVender.getPreco() + "\nQuant. em estoque: " + produtoParaVender.getQuantidade());
                System.out.println("Informe a quantidade que será vendida: ");
                int optionQuantity = scan.nextInt();
                while (optionQuantity < 1 || optionQuantity > produtoParaVender.getQuantidade()) {
                    System.out.println("Quantidade inválida! Informe um valor entre 1 e " + produtoParaVender.getQuantidade());
                    optionQuantity = scan.nextInt();
                }
                novaVenda.setProduto(produtoParaVender, optionQuantity);
                System.out.println("Deseja adicionar outro produto? [0- sim, adicionar outro produto | 1- não, finalizar venda]: ");
                do {
                    optionConfirm = scan.nextInt();
                    scan.nextLine();
                } while (optionConfirm < 0 || optionConfirm > 1);
            }
            if (optionConfirm == 1) break;
        } while (true);
        
        novaVenda.setValorTotal();
        novaVenda.obterReciboFormatado();
        System.out.println("Deseja confirmar essa venda? [0- sim, confirmar | 1- não, cancelar venda]");
        do {
            optionConfirm = scan.nextInt();
            scan.nextLine();
        } while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 0) confirmarVenda(novaVenda);
        else {
            System.out.println("Venda cancelada!");
            menu();
        }
    }

    public static void confirmarVenda(Venda vendaParaConfirmar) {
        for (ItemTransacao produtoVenda : vendaParaConfirmar.getListaDeProdutos()) {
            Produto produtoVendido = produtoVenda.getProduto();
            produtoVendido.reduzirQuantidadePosVenda(produtoVenda.getQuantidade());
        }
        caixa.realizarVenda(vendaParaConfirmar.getValorTotal());
        System.out.println("Venda realizada com sucesso!");
        System.out.println("Deseja realizar outra venda? [0 sim, desejo | 1 não, voltar ao menu]\nOpção: ");
        int optionConfirm;
        do {
            optionConfirm = scan.nextInt();
            scan.nextLine();
        } while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 0) sellProducts();
        else menu();
    }

    public static void confirmarCompra(Compra compraParaConfirmar) {
        for (ItemTransacao produtoVenda : compraParaConfirmar.getListaDeProdutos()) {
            Produto produtoVendido = produtoVenda.getProduto();
            produtoVendido.reduzirQuantidadePosVenda(produtoVenda.getQuantidade());
        }
        caixa.realizarCompra(compraParaConfirmar.getValorTotal());
        System.out.println("Compra realizada com sucesso!");
        System.out.println("Deseja realizar outra compra? [0 sim, desejo | 1 não, voltar ao menu]\nOpção: ");
        int optionConfirm;
        do {
            optionConfirm = scan.nextInt();
            scan.nextLine();
        } while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 0) sellProducts();
        else menu();
    }

    public static String imprimirProdutos() {
        StringBuilder listaProdutos = new StringBuilder();
        listaProdutos.append("Produtos: \n");
        for (Produto produto : produtos) {
            listaProdutos.append(produto.toString());
            listaProdutos.append("\n");
        }
        return listaProdutos.toString();
    }

    public static String imprimirClientes() {
        StringBuilder listaClientes = new StringBuilder();
        listaClientes.append("Clientes: \n");
        for (Cliente cliente : clientes) {
            listaClientes.append(cliente.toString());
            listaClientes.append("\n");
        }
        return listaClientes.toString();
    }

    public static void buyProducts() {
        Compra novaCompra = new Compra();
        int optionConfirm = 0;

        do {
            Produto produtoParaComprar;
            String codigoProduto;
            System.out.println(imprimirProdutos());
            System.out.println("\n=> Informe o código do produto a ser adquirido: ");
            codigoProduto = scan.next();
            produtoParaComprar = encontrarProdutoPorCodigo(codigoProduto);
            if (produtoParaComprar == null) System.out.println("O código informado não pertence a nenhum produto cadastrado!");
            else {
                System.out.println("Produto selecionado: " + produtoParaComprar.getNome() + "\nPreço: R$" + produtoParaComprar.getPreco() + "\nQuant. em estoque: " + produtoParaComprar.getQuantidade());
                System.out.println("Informe a quantidade que será comprada: ");
                int optionQuantity = scan.nextInt();
                while (optionQuantity < 1 || (optionQuantity * produtoParaComprar.getPreco()) > caixa.getSaldo()) {
                    System.out.println("Quantidade inválida ou saldo insuficiente! Tente novamente: ");
                    optionQuantity = scan.nextInt();
                }
                novaCompra.setProduto(produtoParaComprar, optionQuantity);
                System.out.println("Deseja adicionar outro produto? [0- sim, adicionar outro produto | 1- não, finalizar venda]: ");
                do {
                    optionConfirm = scan.nextInt();
                    scan.nextLine();
                } while (optionConfirm < 0 || optionConfirm > 1);
            }
            if (optionConfirm == 1) break;
        } while (true);
        
        novaCompra.setValorTotal();
        novaCompra.obterReciboFormatado();
        System.out.println("Deseja confirmar essa compra? [0- sim, confirmar | 1- não, cancelar compra]");
        do {
            optionConfirm = scan.nextInt();
            scan.nextLine();
        } while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 0) confirmarCompra(novaCompra);
        else {
            System.out.println("Compra cancelada!");
            menu();
        }
    }

    public static void login() {
        System.out.println("<== = = = = LOGIN = = = = ==>");
        do {
            System.out.println("Informe o usuário: ");
            String userAttempt = scan.next();
            System.out.println("Infome a senha: ");
            String passAttempt = scan.next();
            if (userAttempt.equals("admin") && passAttempt.equals("123")) break;
            else System.out.println("Usuário ou senha incorretos!");
        } while (true);
        clearConsole();
        loadDefaultProductsAndClients();
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
                Process process = processBuilder.inheritIO().start();
                process.waitFor();
            } else {
                ProcessBuilder processBuilder = new ProcessBuilder("clear");
                Process process = processBuilder.inheritIO().start();
                process.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        menu();
    }
}