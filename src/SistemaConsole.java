/*
* User: admin
* Password: 123

* Developed by: Pedro Luca Prates
* e-mail: pedrolucaofc@outlook.com
* site: https://pedroluca.vercel.app
*/

import java.util.Scanner;

class Product {
    String name;
    double price;
    int quantity;
}

public class SistemaConsole {
    public static int quantClients = 10;
    public static double moneyInCashier = 0;
    public static String clients[] = new String[quantClients];
    public static Product products[] = new Product[10];

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        loadDefaultProductsAndClients();
    }

    public static void loadDefaultProductsAndClients() {
        for (int i = 0; i < products.length; i++) {
            products[i] = new Product();
            products[i].name = "";
            clients[i] = "";
        }
        clients[0] = "Pedro";
        clients[1] = "Lucas";
        clients[2] = "João";
        clients[3] = "Tharlis";
        products[0].name = "Feijão";
        products[1].name = "Arroz";
        products[2].name = "Macarrão";
        products[3].name = "Tapioca";
        products[4].name = "Banana";
        products[0].price = 5.7;
        products[1].price = 3.4;
        products[2].price = 7.34;
        products[3].price = 15.99;
        products[4].price = 7.89;
        products[0].quantity = 7;
        products[1].quantity = 8;
        products[2].quantity = 20;
        products[3].quantity = 12;
        products[4].quantity = 5;
        login();
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
                System.out.println("\nO valor em caixa é de R$" + moneyInCashier + "\n");
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
        for (int i = 0; i < products.length; i++) {
            if (!products[i].name.equals("")) System.out.println((i + 1) + ": " + products[i].name + "\nPreço unitário: R$" + products[i].price + " | Quantidade: " + products[i].quantity + "\n");
        }
        do {
            System.out.println("========================\n[0 voltar ao menu | 1 excluir produto]\nOpção: ");
            option = scan.nextInt();
        } while (option < 0 || option > 1);
        if (option == 0) menu(); 
        else deleteProduct();
    }
    
    private static void showCustomers() {
        int option;
        System.out.print("\n");
        for (int i = 0; i < clients.length; i++) {
            if (!clients[i].equals("")) System.out.println((i + 1) + ": " + clients[i]);
        }
        do {
            System.out.println("========================\n[0 voltar ao menu | 1 excluir cliente]\nOpção: ");
            option = scan.nextInt();
        } while (option < 0 || option > 1);
        if (option == 0) menu(); 
        else deleteCustomer();
    }

    public static void addNewCustomer() {
        int count = 0, option;
        String newClient;
        for (int i = 0; i < clients.length; i++) {
            if (!clients[i].equals("")) count++;
        }
        if (count == clients.length) {
            System.out.println("Lista de clientes lotada! Deseja excluir algum cliente? \n[0 sim, excluir | 1 não, voltar ao menu]\nOpção: ");
            option = scan.nextInt();
            if (option == 0) deleteCustomer();
            else menu();
        }
        System.out.print("\nInforme o nome do novo cliente: ");
        newClient = scan.next();
        for (int i = 0; i < clients.length; i++) {
            if (clients[i].equals("")) {
                clients[i] = newClient;
                System.out.println("Cliente cadastrado!");
                menu();
            }
        }
    }

    public static void addNewProduct() {
        int count = 0, option, newQuantity;
        String newProduct;
        double newPrice;
        for (int i = 0; i < products.length; i++) {
            if (!products[i].name.equals("")) count++;
        }
        if (count == products.length) {
            System.out.println("Lista de produtos lotada! Deseja excluir algum produto? \n[0 sim, excluir | 1 não, voltar ao menu]\nOpção: ");
            option = scan.nextInt();
            if (option == 0) deleteProduct();
            else menu();
        }
        System.out.print("\nInforme o nome, o preço e a quantidade do novo produto (respectivamente): ");
        newProduct = scan.next();
        newPrice = scan.nextDouble();
        newQuantity = scan.nextInt();
        for (int i = 0; i < products.length; i++) {
            if (products[i].name.equals("")) {
                products[i].name = newProduct;
                products[i].price = newPrice;
                products[i].quantity = newQuantity;
                System.out.println("Produto cadastrado!");
                menu();
            }
        }
    }

    public static void deleteCustomer() {
        int option;
        System.out.println("");
        for (int i = 0; i < clients.length; i++) {
            if (!clients[i].equals("")) System.out.println((i + 1) + ": " + clients[i]);
        }
        do {
            System.out.println("Informe o ID do cliente que deseja excluir [0 para voltar ao menu]\nOpção: ");
            option = scan.nextInt();
            if (option == 0) menu();
            option--;
            if (clients[option].equals("")) {
                System.out.println("Esse ID ainda não pertence a nenhum cliente!");
                option = -1;
            }
        } while (option < 0 || option > clients.length);
        if (option == 0) menu();
        for (int i = 0; i < clients.length; i++) {
            if (i == (option)) clients[i] = "";
        }
        menu();
    }

    public static void deleteProduct() {
        int option;
        System.out.println("");
        for (int i = 0; i < products.length; i++) {
            if (!products[i].name.equals("")) System.out.println((i + 1) + ": " + products[i].name);
        }
        do {
            System.out.println("Informe o ID do produto que deseja excluir [0 para voltar ao menu]\nOpção: ");
            option = scan.nextInt();
            if (option == 0) menu();
            option--;
            if (products[option].name.equals("")) {
                System.out.println("Esse ID ainda não pertence a nenhum produto!");
                option = -1;
            }
        } while (option < 0 || option > products.length);
        if (option == 0) menu();
        for (int i = 0; i < products.length; i++) {
            if (i == (option)) {
                products[i].name = "";
                products[i].price = products[i].quantity = 0;
            }
        }
        menu();
    }

    public static void sellProducts() {
        int optionProduct, optionClient, optionQuantity, optionConfirm;
        double optionPrice;
        System.out.println("Produtos: ");
        for (int i = 0; i < products.length; i++) {
            if (!products[i].name.equals("")) System.out.println((i + 1) + ": " + products[i].name);
        }
        System.out.println("====================\nInforme o ID do produto a ser vendido: ");
        do {
            optionProduct = scan.nextInt();
            optionProduct--;
            if (products[optionProduct].name.equals("")) {
                System.out.println("O ID informado não pertence a nenhum produto cadastrado!");
                optionProduct = -1;
            }
        } while (optionProduct < 0 || optionProduct > products.length);
        System.out.println("Clientes: ");
        for (int i = 0; i < clients.length; i++) {
            if (!clients[i].equals("")) System.out.println((i + 1) + ": " + clients[i]); 
        }
        System.out.println("====================\nInforme o ID do cliente que está comprando: ");
        do {
            optionClient = scan.nextInt();
            optionClient--;
            if (clients[optionClient].equals("")) {
                System.out.println("O ID informado não pertence a nenhum cliente cadastrado!");
                optionClient = -1;
            }
        } while (optionClient < 0 || optionClient > products.length);
        System.out.println("Produto selecionado: " + products[optionProduct].name + "\nPreço: R$" + products[optionProduct].price + "\nQuantidade: " + products[optionProduct].quantity);
        System.out.println("Informe a quantidade que será vendida: ");
        optionQuantity = scan.nextInt();
        while (optionQuantity < 1 || optionQuantity > products[optionProduct].quantity) {
            System.out.println("Quantidade inválida! Informe uma quantidade entre 1 e " + products[optionProduct].quantity);
            optionQuantity = scan.nextInt();
        }
        optionPrice = products[optionProduct].price * optionQuantity;
        System.out.println("\nNota Fiscal\nProduto: " + products[optionProduct].name + "\nPreço unitário: R$" + products[optionProduct].price + "\nQuantidade: " + optionQuantity + " | Valor total: R$" + optionPrice);
        System.out.println("\nDeseja confirmar a venda? [0 sim, confirmar | 1 não, cancelar]\nOpção: ");
        do optionConfirm = scan.nextInt();
        while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 1) menu();
        if (optionConfirm == 0) {
            products[optionProduct].quantity -= optionQuantity;
            moneyInCashier += optionPrice;
            System.out.println("Venda realizada com sucesso!");
        }
        System.out.println("Deseja realizar outra venda? [0 sim, desejo | 1 não, voltar ao menu]\nOpção: ");
        do optionConfirm = scan.nextInt();
        while (optionConfirm < 0 || optionConfirm > 1);
        if (optionConfirm == 0) sellProducts();
        menu();
    }

    public static void buyProducts() {
        int optionProduct, optionQuantity, confirmPurchase = 0;
        double optionPrice;
        System.out.println("Produtos: ");
        for (int i = 0; i < products.length; i++) {
            if (!products[i].name.equals("")) System.out.println((i + 1) + ": " + products[i].name);
        }
        System.out.println("====================\nInforme o ID do produto a ser comprado: ");
        do {
            optionProduct = scan.nextInt();
            optionProduct--;
            if (products[optionProduct].name.equals("")) {
                System.out.println("O ID informado não pertence a nenhum produto cadastrado!");
                optionProduct = -1;
            }
        } while (optionProduct < 0 || optionProduct > products.length);
        do {
            System.out.println("Informe a quantidade de produtos que deseja adquirir: ");
            optionQuantity = scan.nextInt();
            optionPrice = optionQuantity * products[optionProduct].price;
            if (optionPrice > moneyInCashier) {
                System.out.println("O dinheiro em caixa é insuficiente! [0 alterar quantidade | 1 cancelar compra]\nOpção: ");
                do confirmPurchase = scan.nextInt();
                while (confirmPurchase < 0 || confirmPurchase > 1);
                if (confirmPurchase == 1) menu();
            } else confirmPurchase = 1;
        } while (confirmPurchase == 0);
        System.out.println("\nNota Fiscal\nProduto: " + products[optionProduct].name + "\nPreço unitário: R$" + products[optionProduct].price + "\nQuantidade: " + optionQuantity + " | Valor total: R$" + optionPrice);
        System.out.println("\nDeseja confirmar a compra? [0 sim, confirmar | 1 não, cancelar]\nOpção: ");
        do confirmPurchase = scan.nextInt();
        while (confirmPurchase < 0 || confirmPurchase > 1);
        if (confirmPurchase == 1) menu();
        if (confirmPurchase == 0) {
            products[optionProduct].quantity += optionQuantity;
            moneyInCashier -= optionPrice;
            System.out.println("Compra realizada com sucesso!");
        }
        System.out.println("Deseja realizar outra compra? [0 sim, desejo | 1 não, voltar ao menu]\nOpção: ");
        do confirmPurchase = scan.nextInt();
        while (confirmPurchase < 0 || confirmPurchase > 1);
        if (confirmPurchase == 0) buyProducts();
        menu();
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
        menu();
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