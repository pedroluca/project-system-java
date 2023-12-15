/*
* User: admin
* Password: 123

* Developed by: Pedro Luca Prates
* e-mail: pedrolucaofc@outlook.com
* site: https://pedroluca.vercel.app
*/
package SistemaGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProductListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Product) {
            Product product = (Product) value;
            setText(product.name + " - Quantidade: " + product.quantity + ", Preço: " + product.price);
        }
        return this;
    }
}

class Product {
    String name;
    double price = 0;
    int quantity = 0;
}

public class SistemaInterface {
    public static int quantClients = 10;
    public static double moneyInCashier = 50;
    public static String clients[] = new String[quantClients];
    public static Product products[] = new Product[10];
    static Dimension bottomButtonSize = new Dimension(120, 45);
    static Font buttonFont = new Font("Arial", Font.BOLD, 16);

    public static void main(String args[]) {
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
        JFrame menuFrame = new JFrame("Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - menuFrame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - menuFrame.getHeight()) / 2);

        menuFrame.setLocation(x, y);
        
        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 para linhas, 2 para colunas

        JButton option1 = new JButton("Produtos em Estoque");
        JButton option2 = new JButton("Ver Clientes");
        JButton option3 = new JButton("Dinheiro em Caixa");
        JButton option4 = new JButton("Vender Produtos");
        JButton option5 = new JButton("Comprar Produtos");
        JButton option6 = new JButton("Sair e Fechar");
        
        JButton[] buttons = { option1, option2, option3, option4, option5, option6 };
        
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font(buttons[i].getFont().getName(), Font.PLAIN, 18));
            buttons[i].setPreferredSize(new Dimension(250, 50));
            menuPanel.add(buttons[i]);

            buttons[i].setActionCommand(String.valueOf(i));
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    int buttonIndex = Integer.parseInt(actionCommand);
                    switch (buttonIndex) {
                        case 0:
                            produtosEmEstoqueFunction();
                            menuFrame.dispose();
                            break;
                        case 1:
                            clientesFunction();
                            menuFrame.dispose();
                            break;
                        case 2:
                            dinheiroEmCaixaFunction();
                            menuFrame.dispose();
                            break;
                        case 3:
                            sellProducts();
                            menuFrame.dispose();
                            break;
                        case 4:
                            buyMoreProducts();
                            menuFrame.dispose();
                            break;
                        case 5:
                            System.exit(0);
                            break;
                    }
                }
            });
        }

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(menuPanel);
        
        menuFrame.getContentPane().add(BorderLayout.CENTER, centeringPanel);
        menuFrame.setVisible(true);
    }

    public static void produtosEmEstoqueFunction() {
        JFrame frame = new JFrame("Produtos em Estoque");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);

        frame.setLocation(x, y);

        DefaultListModel<Product> productListModel = new DefaultListModel<>();
        JList<Product> productList = new JList<>(productListModel);
        productList.setCellRenderer(new ProductListRenderer());
        productList.setFont(new Font("Arial", Font.PLAIN, 18));
        
        for (Product product : products) {
            if (product.name != null && !product.name.isEmpty()) {
                productListModel.addElement(product);
            }
        }
        
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Voltar");
        JButton deleteButton = new JButton("Excluir");
        JButton addButton = new JButton("Adicionar");
        backButton.setPreferredSize(bottomButtonSize);
        backButton.setFont(buttonFont);
        deleteButton.setPreferredSize(bottomButtonSize);
        deleteButton.setFont(buttonFont);
        addButton.setPreferredSize(bottomButtonSize);
        addButton.setFont(buttonFont);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
                frame.dispose();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = productList.getSelectedIndices();
                
                for (int i = selectedIndices.length - 1; i >= 0; i--) {
                    productListModel.remove(selectedIndices[i]);
                    products[selectedIndices[i]].name = ""; // Limpa o valor no vetor
                    products[selectedIndices[i]].price = 0; // Limpa o valor no vetor
                    products[selectedIndices[i]].quantity = 0; // Limpa o valor no vetor
                }
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newProduct(frame);
            }
        });
        
        JLabel hintLabel = new JLabel("Caso deseje excluir algum, clique no produto e aperte em excluir");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(productList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(hintLabel, BorderLayout.NORTH);
        frame.setVisible(true);        
    }

    public static void clientesFunction() {
        JFrame frame = new JFrame("Clientes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);

        frame.setLocation(x, y);

        DefaultListModel<String> clientListModel = new DefaultListModel<>();
        JList<String> clientList = new JList<>(clientListModel);
        clientList.setFont(new Font("Arial", Font.PLAIN, 18));

        for (String client : clients) {
            if (client != null && !client.isEmpty()) {
                clientListModel.addElement(client);
            }
        }

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Voltar");
        JButton deleteButton = new JButton("Excluir");
        JButton addButton = new JButton("Adicionar");
        backButton.setPreferredSize(bottomButtonSize);
        backButton.setFont(buttonFont);
        deleteButton.setPreferredSize(bottomButtonSize);
        deleteButton.setFont(buttonFont);
        addButton.setPreferredSize(bottomButtonSize);
        addButton.setFont(buttonFont);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
                frame.dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = clientList.getSelectedIndices();

                for (int i = selectedIndices.length - 1; i >= 0; i--) {
                    clientListModel.remove(selectedIndices[i]);
                    clients[selectedIndices[i]] = ""; // Limpa o valor no vetor
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novoClienteFunction(frame);
            }
        });

        JLabel hintLabel = new JLabel("Caso deseje excluir algum, clique no cliente e aperte em excluir");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(clientList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(hintLabel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    public static void novoClienteFunction(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nameLabel = new JLabel("Nome do Cliente:");
        JTextField nameField = new JTextField(12);

        panel.add(nameLabel);
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < products.length; i++) {
                if (clients[i].equals("")) {
                    clients[i] = nameField.getText();
                    break;
                }
            }
            clientesFunction();
            frame.dispose();
        }
    }

    public static void newProduct(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nameLabel = new JLabel("Nome do Produto:");
        JLabel priceLabel = new JLabel("Preço de Venda:");
        JLabel quantityLabel = new JLabel("Quantidade:");
        JTextField nameField = new JTextField(12);
        JTextField priceField = new JTextField(12);
        JTextField quantityField = new JTextField(12);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < products.length; i++) {
                if (products[i].name.equals("")) {
                    products[i].name = nameField.getText();
                    products[i].price = Double.parseDouble(priceField.getText());
                    products[i].quantity = Integer.parseInt(quantityField.getText());
                    break;
                }
            }
            produtosEmEstoqueFunction();
            frame.dispose();
        }
    }

    public static void dinheiroEmCaixaFunction() {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JLabel nameLabel = new JLabel("O dinheiro em caixa é: R$" + moneyInCashier);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(nameLabel);
        JOptionPane.showMessageDialog(null, panel, "", JOptionPane.PLAIN_MESSAGE);
        menu();
    }

    public static void sellProducts() {
        JFrame frame = new JFrame("Vender produtos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);

        frame.setLocation(x, y);

        DefaultListModel<String> productListModel = new DefaultListModel<>();
        JList<String> productList = new JList<>(productListModel);
        productList.setFont(new Font("Arial", Font.PLAIN, 18));
        
        for (Product product : products) {
            if (product.name != null && !product.name.isEmpty()) {
                productListModel.addElement(product.name);
            }
        }
        
        JPanel buttonPanel = new JPanel();
        JButton sellButton = new JButton("Vender");
        JButton backButton = new JButton("Voltar");
        sellButton.setPreferredSize(bottomButtonSize);
        sellButton.setFont(buttonFont);
        backButton.setPreferredSize(bottomButtonSize);
        backButton.setFont(buttonFont);
        buttonPanel.add(sellButton);
        buttonPanel.add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
                frame.dispose();
            }
        });
        
        JLabel hintLabel = new JLabel("Para vender, clique no produto e aperte em vender");
        
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndicesProducts = productList.getSelectedIndices();
                
                for (int i = selectedIndicesProducts.length - 1; i >= 0; i--) {
                    selectBuyerClient(products[selectedIndicesProducts[i]]);
                    frame.dispose();
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(productList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(hintLabel, BorderLayout.NORTH);        
        frame.setVisible(true);
    }

    public static void selectBuyerClient(Product product) {
        JFrame frame = new JFrame("Vender produtos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);

        frame.setLocation(x, y);

        DefaultListModel<String> clientListModel = new DefaultListModel<>();
        JList<String> clientList = new JList<>(clientListModel);
        clientList.setFont(new Font("Arial", Font.PLAIN, 18));

        for (String client : clients) {
            if (client != null && !client.isEmpty()) {
                clientListModel.addElement(client);
            }
        }
        
        JPanel buttonPanel = new JPanel();
        JButton sellButton = new JButton("Continuar");
        JButton backButton = new JButton("Voltar");
        sellButton.setPreferredSize(bottomButtonSize);
        sellButton.setFont(buttonFont);
        backButton.setPreferredSize(bottomButtonSize);
        backButton.setFont(buttonFont);
        buttonPanel.add(sellButton);
        buttonPanel.add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
                frame.dispose();
            }
        });
        
        JLabel hintLabel = new JLabel("Para continuar, selecione o cliente");
        
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndicesClient = clientList.getSelectedIndices();

                for (int i = selectedIndicesClient.length - 1; i >= 0; i--) {
                    sellProductPopup(product, clients[selectedIndicesClient[i]], frame);
                }
            }
        });        

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(clientList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(hintLabel, BorderLayout.NORTH);        
        frame.setVisible(true);
    }

    public static void sellProductPopup(Product product, String client, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel clientLabel = new JLabel("Cliente: " + client);
        JLabel productLabel = new JLabel("Produto: " + product.name);
        JLabel priceLabel = new JLabel("Valor unitário: R$" + product.price);
        JLabel quantityLabel = new JLabel("Quantidade: [max: " + product.quantity + "]");
        SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, product.quantity, 1); // Valor inicial, valor mínimo, valor máximo, passo
        JSpinner quantitySpinner = new JSpinner(quantityModel);

        panel.add(clientLabel);
        panel.add(productLabel);
        panel.add(priceLabel);
        panel.add(quantityLabel);
        panel.add(quantitySpinner);

        int result = JOptionPane.showConfirmDialog(null, panel, "Quantidade", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int quantity = (int) quantitySpinner.getValue(); // Pegue o valor após o clique em OK
            if (quantity > product.quantity) {
                selectBuyerClient(product);
                frame.dispose();
            }
            double productCost = product.price * quantity;
            confirmSellingProduct(productCost, client, product, quantity, frame);
        }
    }

    public static void confirmSellingProduct(Double sellValue, String client, Product product, Integer quantity, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JLabel clientLabel = new JLabel("Cliente: " + client);
        JLabel productLabel = new JLabel("Produto: " + product.name);
        JLabel quantityLabel = new JLabel("Quantidade: " + quantity);
        JLabel priceLabel = new JLabel("Valor total: R$" + sellValue);

        panel.add(clientLabel);
        panel.add(productLabel);
        panel.add(priceLabel);
        panel.add(quantityLabel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Finalizar venda", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < products.length; i++) {
                if (products[i] == product) {
                    products[i].quantity -= quantity;
                    moneyInCashier += sellValue;
                    break;
                }
            }
            menu();
            frame.dispose();
        } else {
            sellProducts();
            frame.dispose();
        }
    } 

    public static void buyMoreProducts() {
        JFrame frame = new JFrame("Reabastecer estoque de produtos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        DefaultListModel<String> productListModel = new DefaultListModel<>();
        JList<String> productList = new JList<>(productListModel);
        productList.setFont(new Font("Arial", Font.PLAIN, 18));
        
        for (Product product : products) {
            if (product.name != null && !product.name.isEmpty()) productListModel.addElement(product.name);
        }
        
        JPanel buttonPanel = new JPanel();
        JButton buyButton = new JButton("Comprar");
        JButton backButton = new JButton("Voltar");
        buyButton.setPreferredSize(bottomButtonSize);
        buyButton.setFont(buttonFont);
        backButton.setPreferredSize(bottomButtonSize);
        backButton.setFont(buttonFont);
        buttonPanel.add(buyButton);
        buttonPanel.add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
                frame.dispose();
            }
        });
        
        JLabel hintLabel = new JLabel("Para comprar, clique no produto e aperte no botão 'comprar'");
        
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productList.getSelectedIndex();
                if (selectedIndex != -1)  buyProductPopup(products[selectedIndex], frame);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(productList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(hintLabel, BorderLayout.NORTH);        
        frame.setVisible(true);
    }

    public static void buyProductPopup(Product product, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(5, 3));
        JLabel productLabel = new JLabel("Produto: " + product.name);
        JLabel priceLabel = new JLabel("Valor unitário de compra: R$" + (product.price / 2));
        JLabel quantityLabel = new JLabel("Quantidade:");
        SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1); // Valor inicial, valor mínimo, valor máximo, passo
        JSpinner quantitySpinner = new JSpinner(quantityModel);

        panel.add(productLabel);
        panel.add(priceLabel);
        panel.add(quantityLabel);
        panel.add(quantitySpinner);

        int result = JOptionPane.showConfirmDialog(null, panel, "Quantidade", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int quantity = (int) quantitySpinner.getValue(); // Pegue o valor após o clique em OK
            double productCost = (product.price / 2) * quantity;
            confirmBuyingProduct(productCost, quantity, product, frame);
        }
    }

    public static void confirmBuyingProduct(Double buyCost, Integer quantity, Product product, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(5, 3));
        JLabel productLabel = new JLabel("Produto: " + product.name);
        JLabel quantityLabel = new JLabel("Quantidade: " + quantity);
        JLabel priceLabel = new JLabel("Valor total: R$" + buyCost);

        panel.add(productLabel);
        panel.add(priceLabel);
        panel.add(quantityLabel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Finalizar compra", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < products.length; i++) {
                if (products[i] == product) {
                    products[i].quantity += quantity;
                    moneyInCashier -= buyCost;
                    break;
                }
            }
            menu();
            frame.dispose();
        } else {
            buyMoreProducts();
            frame.dispose();
        }
    }
    
    public static void login() {
        JFrame frame = new JFrame("Sistema de Vendas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS)); // Define o layout vertical
        JLabel userLabel = new JLabel("Username: ");
        JTextField userField = new JTextField(20);
        userField.setPreferredSize(new Dimension(userField.getPreferredSize().width, 25));
        userField.setFont(userField.getFont().deriveFont(14f));
        usernamePanel.add(userLabel);
        usernamePanel.add(userField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS)); // Define o layout vertical
        JLabel passLabel = new JLabel("Password: ");
        JPasswordField passField = new JPasswordField(20);
        passField.setPreferredSize(new Dimension(passField.getPreferredSize().width, 25));
        passField.setFont(passField.getFont().deriveFont(14f));
        passwordPanel.add(passLabel);
        passwordPanel.add(passField);

        JPanel buttonPanel = new JPanel();
        JButton loginBtn = new JButton("Log in");
        JButton registerBtn = new JButton("Register");
        Insets buttonMargin = new Insets(5, 10, 5, 10);
        loginBtn.setMargin(buttonMargin);
        registerBtn.setMargin(buttonMargin);
        Font loginButtonFont = new Font(loginBtn.getFont().getName(), Font.BOLD, 14);
        userLabel.setFont(loginButtonFont);
        passLabel.setFont(loginButtonFont);
        loginBtn.setFont(loginButtonFont);
        registerBtn.setFont(loginButtonFont);
        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        mainPanel.add(usernamePanel, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(passwordPanel, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                
                if (username.equals("admin") && password.equals("123")) {
                  menu();
                  frame.dispose();
                } else {
                  login();
                  frame.dispose();
                }
            }
        });
    }
}
