/*
* User: admin
* Password: 123

* Developed by: Pedro Luca Prates
* e-mail: pedrolucaofc@outlook.com
* site: https://pedroluca.vercel.app
*/
package SistemaConsole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Objetos.*;

public class SistemaConsole {
    static ConexaoMySQL database = new ConexaoMySQL();
    static Caixa caixa = new Caixa(0);
    static ArrayList<Cliente> clientes = new ArrayList<>();
    static ArrayList<Produto> produtos = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        login();
        // loadDataFromDB();
    }

    public static void loadDataFromDB() {
        database.openDatabase();
        produtos.clear();
        try (ResultSet produtosRetornados = database.executeSelectQuery("SELECT * FROM Produto")) {
            while (produtosRetornados.next()) {
                Boolean isPerecivel = produtosRetornados.getBoolean("isPerecivel");
                if (isPerecivel) {
                    String dataVencimento = produtosRetornados.getString("data_vencimento");
                    LocalDate localDateVencimento = converterDataStringParaLocalDate(dataVencimento);
                    ProdutoPerecivel produtoPerecivel = new ProdutoPerecivel(
                        produtosRetornados.getString("codigo"),
                        produtosRetornados.getString("nome"),
                        produtosRetornados.getDouble("preco"),
                        produtosRetornados.getInt("quantidade"),
                        localDateVencimento
                    );
                    produtos.add(produtoPerecivel);
                } else {
                    ProdutoNaoPerecivel produtoNaoPerecivel = new ProdutoNaoPerecivel(
                        produtosRetornados.getString("codigo"),
                        produtosRetornados.getString("nome"),
                        produtosRetornados.getDouble("preco"),
                        produtosRetornados.getInt("quantidade"),
                        produtosRetornados.getString("material")
                    );
                    produtos.add(produtoNaoPerecivel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clientes.clear();
        try (ResultSet clientesRetornados = database.executeSelectQuery("SELECT * FROM Cliente")) {
            while (clientesRetornados.next()) {
                Cliente cliente = new Cliente(
                    clientesRetornados.getString("nome"),
                    clientesRetornados.getString("cpf"),
                    clientesRetornados.getInt("idade")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet saldoCaixa = database.executeSelectQuery("SELECT * FROM Caixa")) {
            while (saldoCaixa.next()) {
                caixa.setSaldo(saldoCaixa.getDouble("saldo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        caixa.getHistoricoDeCompras().getListaDeTransacoes().clear();
        caixa.getHistoricoDeVendas().getListaDeTransacoes().clear();
        try (ResultSet historicoRetornado = database.executeSelectQuery("SELECT * FROM Transacao")) {
            while (historicoRetornado.next()) {
                boolean isVenda = historicoRetornado.getBoolean("isVenda");
                if (isVenda) {
                    Venda vendaHistorico = new Venda(encontrarClientePorCPF(historicoRetornado.getString("cpf_cliente")));
                    vendaHistorico.setDataDaTransacao(converterDataStringParaLocalDate(historicoRetornado.getString("data_transacao")));
                    ResultSet itensVenda = database.executeSelectQuery("SELECT * FROM Item_Transacao WHERE codigo_transacao = " + historicoRetornado.getInt("codigo"));
                    while (itensVenda.next()) {
                        Produto produtoVenda = encontrarProdutoPorCodigo(itensVenda.getString("codigo_produto"));
                        vendaHistorico.setProduto(produtoVenda, itensVenda.getInt("quantidade_produto"));
                    }
                    vendaHistorico.setValorTotal();
                    caixa.setNovaVendaParaHistorico(vendaHistorico);
                } else {
                    Compra compraHistorico = new Compra();
                    compraHistorico.setDataDaTransacao(converterDataStringParaLocalDate(historicoRetornado.getString("data_transacao")));
                    ResultSet itensCompra = database.executeSelectQuery("SELECT * FROM Item_Transacao WHERE codigo_transacao = " + historicoRetornado.getInt("codigo"));
                    while (itensCompra.next()) {
                        Produto produtoCompra = encontrarProdutoPorCodigo(itensCompra.getString("codigo_produto"));
                        compraHistorico.setProduto(produtoCompra, itensCompra.getInt("quantidade_produto"));
                    }
                    compraHistorico.setValorTotal();
                    caixa.setNovaCompraParaHistorico(compraHistorico);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        database.closeDatabase();
        menu();
    }

    public static void menu() {
        int option = 0;
        System.out.println("<<= = = = = = = = MENU = = = = = = = ==>");
        System.out.println(" || 1- Ver produtos                  ||");
        System.out.println(" || 2- Ver clientes                  ||");
        System.out.println(" || 3- Mostrar dinheiro em caixa     ||");
        System.out.println(" || 4- Vender produtos               ||");
        System.out.println(" || 5- Comprar produtos              ||");
        System.out.println(" || 6- Ver históricos                ||");
        System.out.println(" || 7- Limpar tela                   ||");
        System.out.println(" || 8- Sair                          ||");
        System.out.println("<== = = = = = = = = = = = = = = = = ===>");
        while (option < 1 || option > 8) {
            System.out.println("Informe a opção desejada: ");
            option = scan.nextInt();
            if (option < 1 || option > 8) System.out.println("Opção inválida!");
        }
        switch (option) {
            case 1:
                verProdutos();
                break;
            case 2:
                verClientes();
                break;
            case 3:
                System.out.println("\nO valor em caixa é de R$" + caixa.getSaldo() + "\n");
                menu();
                break;
            case 4:
                sellProducts();
                break;
            case 5:
                buyProducts();
                break;
            case 6:
                verHistoricos();
            case 7:
                clearConsole();
                break;
            case 8:
                System.exit(0);
                break;
        }
    } 

    private static void verProdutos() {
        String codigoProduto = "";
        int option;
        System.out.print("\n");
        System.out.println(imprimirProdutos());
        System.out.println("<<== = = = = = = = = = = = = ==>>");
        System.out.println("  || 1- Adicionar produto    ||");
        System.out.println("  || 2- Editar produto       ||");
        System.out.println("  || 3- Remover produto      ||");
        System.out.println("  || 4- Voltar ao menu       ||");
        System.out.println("<<== = = = = = = = = = = = = ==>>");
        System.out.println("Opção: ");
        option = scan.nextInt();
        if (option == 2 || option == 3) {
            System.out.println("Informe o codigo do produto: ");
            codigoProduto = scan.next();
            scan.nextLine();
        }
        switch (option) {
            case 1:
                addNovoProduto();
                break;
            case 2:
                editarProduto(codigoProduto);
                break;
            case 3:
                deleteProduct(codigoProduto);
                break;
            case 4:
                menu();
                break;
            default: 
                System.out.println("Opção inválida!");
                verProdutos();
        }
    }
    
    private static void verClientes() {
        String cpfCliente = "";
        int option;
        System.out.print("\n");
        System.out.println(imprimirClientes());
        System.out.println("<<== = = = = = = = = = = = = ==>>");
        System.out.println("  || 1- Adicionar cliente    ||");
        System.out.println("  || 2- Editar cliente       ||");
        System.out.println("  || 3- Remover cliente      ||");
        System.out.println("  || 4- Voltar ao men        ||");
        System.out.println("<<== = = = = = = = = = = = = ==>>");
        System.out.println("Opção: ");
        option = scan.nextInt();
        if (option == 2 || option == 3) {
            System.out.println("Informe o CPF do cliente: ");
            cpfCliente = scan.next();
            scan.nextLine();
        }
        switch (option) {
            case 1:
                addNovoCliente();
                break;
            case 2:
                editarCliente(cpfCliente);
                break;
            case 3:
                deletarCliente(cpfCliente);
                break;
            case 4:
                menu();
                break;
            default: 
                System.out.println("Opção inválida!");
                verClientes();
        }
    }

    public static void editarCliente(String cpfCliente) {
        Cliente clienteParaEditar = encontrarClientePorCPF(cpfCliente);
    
        if (clienteParaEditar != null) {
            String novoNome;
            String novoCPF;
            int novaIdade;
    
            System.out.println("Informe o novo nome do cliente (ou deixe em branco para manter o atual): ");
            novoNome = scan.nextLine();
    
            do {
                System.out.println("Informe o novo CPF (apenas números) do cliente (ou deixe em branco para manter o atual): ");
                novoCPF = scan.nextLine();
                if (encontrarClientePorCPF(cpfCliente) != null) System.out.println("Esse CPF já pertence a um cliente!");
            } while (novoCPF.length() != 11 || encontrarClientePorCPF(cpfCliente) != null);
    
            System.out.println("Informe a nova idade do cliente (ou -1 para manter o atual): ");
            novaIdade = scan.nextInt();
            scan.nextLine();
    
            StringBuilder updateQuery = new StringBuilder("UPDATE Cliente SET ");
            if (!novoNome.isEmpty()) updateQuery.append("nome = '").append(novoNome).append("', ");
            if (!novoCPF.isEmpty()) updateQuery.append("cpf = '").append(novoCPF).append("', ");
            if (novaIdade != -1) updateQuery.append("idade = ").append(novaIdade).append(", ");
            updateQuery.setLength(updateQuery.length() - 2);
            updateQuery.append(" WHERE CPF = '").append(cpfCliente).append("'");

            database.openDatabase();
            database.executeQuery(updateQuery.toString());
            database.closeDatabase();
    
            System.out.println("Cliente editado com sucesso!");
        } else {
            System.out.println("Esse CPF não pertence a nenhum cliente!");
        }
        menu();
    }    

    public static void editarProduto(String codigoProduto) {
        Produto produtoParaEditar = encontrarProdutoPorCodigo(codigoProduto);
    
        if (produtoParaEditar != null) {
            String novoCodigo;
            String novoNome;
            double novoPreco;
            int novaQuantidade;
    
            do {
                System.out.println("Informe o novo código do produto (ou deixe em branco para manter o atual): ");
                novoCodigo = scan.nextLine();
                if (encontrarProdutoPorCodigo(codigoProduto) != null) System.out.println("Esse código já pertence a um produto!");
            } while (encontrarProdutoPorCodigo(codigoProduto) != null);
    
            System.out.println("Informe o novo nome do produto (ou deixe em branco para manter o atual): ");
            novoNome = scan.nextLine();
    
            System.out.println("Informe o novo preço do produto (ou -1 para manter o atual): ");
            novoPreco = scan.nextDouble();
            scan.nextLine();
    
            System.out.println("Informe a nova quantidade do produto (ou -1 para manter o atual): ");
            novaQuantidade = scan.nextInt();
            scan.nextLine();
    
            StringBuilder updateQuery = new StringBuilder("UPDATE Produto SET ");
            if (!novoCodigo.isEmpty()) {
                updateQuery.append("codigo = '").append(novoCodigo).append("', ");
            }
            if (!novoNome.isEmpty()) {
                updateQuery.append("nome = '").append(novoNome).append("', ");
            }
            if (novoPreco != -1) {
                updateQuery.append("preco = ").append(novoPreco).append(", ");
            }
            if (novaQuantidade != -1) {
                updateQuery.append("quantidade = ").append(novaQuantidade).append(", ");
            }
            updateQuery.setLength(updateQuery.length() - 2);
            updateQuery.append(" WHERE codigo = '").append(codigoProduto).append("'");
    
            database.openDatabase();
            database.executeQuery(updateQuery.toString());
            database.closeDatabase();
    
            System.out.println("Produto editado com sucesso!");
        } else {
            System.out.println("Esse código não pertence a nenhum produto!");
        }
    
        menu();
    }    

    public static void addNovoCliente() {
        System.out.print("\nInforme o nome do novo cliente: ");
        String nomeCliente = scan.nextLine();
        System.out.print("Informe o CPF do novo cliente: ");
        String cpfCliente = "";
        do {
            cpfCliente = scan.next();
            scan.nextLine();
        } while (cpfCliente.length() != 11);
        System.out.println("Informe a idade do novo cliente: ");
        int idadeCliente = scan.nextInt();
        scan.nextLine();
        Cliente novoCliente = new Cliente(nomeCliente, cpfCliente, idadeCliente);
        clientes.add(novoCliente);
        database.openDatabase();
        database.executeQuery("INSERT INTO Cliente (nome, cpf, idade) VALUES ('" + nomeCliente + "', '" + cpfCliente + "', " + idadeCliente + ")");
        database.closeDatabase();
        menu();
    }

    public static void addNovoProduto() {
        int option;
        do {
            System.out.println("O novo produto é perecível ou não? [0- sim | 1- não]: ");
            option = scan.nextInt();
            scan.nextLine();
        } while (option < 0 || option > 1);
        String codigoProduto;
        do {
            System.out.print("Informe o código do novo produto: ");
            codigoProduto = scan.nextLine();
            if (encontrarProdutoPorCodigo(codigoProduto) != null) System.out.println("Esse código já pertence a um produto!");
            else break;
        } while (true);
        System.out.print("Informe o nome do novo produto: ");
        String nomeProduto = scan.nextLine();
        System.out.print("Informe o preço do novo produto: ");
        double precoProduto = scan.nextDouble();
        scan.nextLine();
        System.out.print("Informe a quantidade do novo produto: ");
        int quantidadeProduto = scan.nextInt();
        scan.nextLine();
        if (option == 0) {
            System.out.println("Informe a data de vencimento do produto (no formato dd/mm/yyyy): ");
            String dataVencimentoProduto = scan.nextLine();
            try {
                LocalDate dataVencimento = converterDataBrasileiraParaLocalDate(dataVencimentoProduto);
                ProdutoPerecivel novoProduto = new ProdutoPerecivel(codigoProduto, nomeProduto, precoProduto, quantidadeProduto, dataVencimento);
                produtos.add(novoProduto);
                database.openDatabase();
                database.executeQuery("INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('" + codigoProduto + "', '" + nomeProduto + "', " + precoProduto + ", " + quantidadeProduto + ", '" + dataVencimento + "', 1)");
                database.closeDatabase();
                System.out.println("Produto adicionado com sucesso!");
                menu();
            } catch (Exception e) {
                System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/mm/yyyy.");
                addNovoProduto();
            }
        } else if (option == 1) {
            System.out.println("Informe o material do produto: ");
            String materialProduto = scan.nextLine();
            ProdutoNaoPerecivel novoProduto = new ProdutoNaoPerecivel(codigoProduto, nomeProduto, precoProduto, quantidadeProduto, materialProduto);
            produtos.add(novoProduto);
                database.openDatabase();
                database.executeQuery("INSERT INTO Produto (codigo, nome, preco, quantidade, material, isPerecivel) VALUES ('" + codigoProduto + "', '" + nomeProduto + "', " + precoProduto + ", " + quantidadeProduto + ", '" + materialProduto + "', 0)");
                database.closeDatabase();
            System.out.println("Produto adicionado com sucesso!");
            menu();
        }
    }

    public static LocalDate converterDataBrasileiraParaLocalDate(String dataInput) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataInput, formatoBrasileiro);
    }
    
    public static LocalDate converterDataStringParaLocalDate(String dataInput) {
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dataInput, dataFormatada);
    }
    

    public static void deletarCliente(String cpfParaDeletar) {
        Cliente clienteParaDeletar = encontrarClientePorCPF(cpfParaDeletar);
        if (clienteParaDeletar != null) {
            clientes.remove(clienteParaDeletar);
            database.openDatabase();
            database.executeQuery("DELETE FROM Cliente WHERE cpf = '" + cpfParaDeletar + "'");
            database.closeDatabase();
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Esse CPF não pertence a nenhum cliente!");
            verClientes();
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

    public static void deleteProduct(String codigoProdutoParaDeletar) {
        Produto produtoParaDeletar = encontrarProdutoPorCodigo(codigoProdutoParaDeletar);
        if (produtoParaDeletar != null) {
            produtos.remove(produtoParaDeletar);
            database.openDatabase();
            database.executeQuery("DELETE FROM Produto WHERE codigo = '" + codigoProdutoParaDeletar + "'");
            database.closeDatabase();
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Esse código não pertence a nenhum produto!");
            verProdutos();
        }
        menu();
    }

    public static void sellProducts() {
        Cliente clienteComprador;
        String cpfCliente;
        System.out.println(imprimirClientes());
        System.out.println("=> Informe o CPF do cliente que está comprando: ");
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
            codigoProduto = scan.nextLine();
            produtoParaVender = encontrarProdutoPorCodigo(codigoProduto);
            if (produtoParaVender == null) System.out.println("O código informado não pertence a nenhum produto cadastrado!");
            else if (produtoParaVender.getQuantidade() == 0) System.out.println("Produto com estoque zerado, por favor escolha outro de nossos produtos!");
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
        System.out.println(novaVenda.obterReciboFormatado());
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
        caixa.realizarVenda(vendaParaConfirmar.getValorTotal());
        caixa.setNovaVendaParaHistorico(vendaParaConfirmar);
        database.openDatabase();
        database.executeQuery("UPDATE Caixa SET saldo = " + caixa.getSaldo());
        database.executeQuery("INSERT INTO Transacao (cpf_cliente, data_transacao, valor_total, isVenda) VALUES ('" + vendaParaConfirmar.getClienteComprador().getCPF() + "', '" + vendaParaConfirmar.getDataTransacaoSemFormatar() + "', " + vendaParaConfirmar.getValorTotal() + ", 1)");
        ResultSet codigoTransacaoRetornado = database.executeSelectQuery("SELECT codigo FROM Transacao WHERE cpf_cliente = '" + vendaParaConfirmar.getClienteComprador().getCPF() + "' AND data_transacao = '" + vendaParaConfirmar.getDataTransacaoSemFormatar() + "' AND valor_total = " + vendaParaConfirmar.getValorTotal() + " AND isVenda = 1");
        int codigoTransacao = 0;
        try {
            while (codigoTransacaoRetornado.next()) {
                codigoTransacao = codigoTransacaoRetornado.getInt("codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (ItemTransacao produtoVenda : vendaParaConfirmar.getListaDeProdutos()) {
            Produto produtoVendido = produtoVenda.getProduto();
            produtoVendido.reduzirQuantidadePosVenda(produtoVenda.getQuantidade());
            database.executeQuery("INSERT INTO Item_Transacao VALUES ('" + produtoVendido.getCodigo() + "', " + codigoTransacao + ", " + produtoVenda.getQuantidade() + ", " + produtoVendido.getPreco() + ")");
            database.executeQuery("UPDATE Produto SET quantidade = " + produtoVendido.getQuantidade() + " WHERE codigo = '" + produtoVendido.getCodigo() + "'");
        }
        database.closeDatabase();
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
        caixa.realizarCompra(compraParaConfirmar.getValorTotal());
        caixa.setNovaCompraParaHistorico(compraParaConfirmar);
        database.openDatabase();
        database.executeQuery("UPDATE Caixa SET saldo = " + caixa.getSaldo());
        database.executeQuery("INSERT INTO Transacao (data_transacao, valor_total, isVenda) VALUES ('" + compraParaConfirmar.getDataTransacaoSemFormatar() + "', " + compraParaConfirmar.getValorTotal() + ", 0)");
        ResultSet codigoTransacaoRetornado = database.executeSelectQuery("SELECT codigo FROM Transacao WHERE data_transacao = '" + compraParaConfirmar.getDataTransacaoSemFormatar() + "' AND valor_total = " + compraParaConfirmar.getValorTotal() + " AND isVenda = 0");
        int codigoTransacao = 0;
        try {
            while (codigoTransacaoRetornado.next()) {
                codigoTransacao = codigoTransacaoRetornado.getInt("codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (ItemTransacao produtoCompra : compraParaConfirmar.getListaDeProdutos()) {
            Produto produtoComprado = produtoCompra.getProduto();
            produtoComprado.aumentarQuantidadePosCompra(produtoCompra.getQuantidade());
            database.executeQuery("INSERT INTO Item_Transacao VALUES ('" + produtoComprado.getCodigo() + "', " + codigoTransacao + ", " + produtoCompra.getQuantidade() + ", " + produtoComprado.getPreco() + ")");
            database.executeQuery("UPDATE Produto SET quantidade = " + produtoComprado.getQuantidade() + " WHERE codigo = '" + produtoComprado.getCodigo() + "'");
        }
        database.closeDatabase();
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
            codigoProduto = scan.nextLine();
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
                System.out.println("Deseja adicionar outro produto? [0- sim, adicionar outro produto | 1- não, finalizar compra]: ");
                do {
                    optionConfirm = scan.nextInt();
                    scan.nextLine();
                } while (optionConfirm < 0 || optionConfirm > 1);
            }
            if (optionConfirm == 1) break;
        } while (true);
        
        novaCompra.setValorTotal();
        System.out.println(novaCompra.obterReciboFormatado());
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
            String userAttempt = scan.nextLine();
            System.out.println("Infome a senha: ");
            String passAttempt = scan.nextLine();
            if (userAttempt.equals("admin") && passAttempt.equals("123")) break;
            else System.out.println("Usuário ou senha incorretos!");
        } while (true);
        clearConsole();
    }

    public static void verHistoricos() {
        int option;
        System.out.println("<<= = = = = = = = HISTÓRICOS = = = = = = = ==>");
        System.out.println(" || 1- Histórico de vendas                  ||");
        System.out.println(" || 2- Histórico de compras                 ||");
        System.out.println(" || 3- Voltar para o menu                   ||");
        System.out.println("<<= = = = = = = = = = = = = = = = = = = == ==>");
        System.out.println("Informe a opção desejada: ");
        option = scan.nextInt();
        switch (option) {
            case 1:
                System.out.println(caixa.getHistoricoDeVendas().toString());
                break;
            case 2:
                System.out.println(caixa.getHistoricoDeCompras().toString());
                break;
            case 3:
                menu();
                break;
            default:
                System.out.println("Opção inválida!");
        }
        verHistoricos();
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
        loadDataFromDB();
    }
}