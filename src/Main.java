import javax.swing.JOptionPane;


public class Main {

    static VeiculoDAO veiculoDAO = new VeiculoDAO();
    static ClienteDAO clienteDAO = new ClienteDAO();
    static AluguelDAO aluguelDAO = new AluguelDAO();

    public static void main(String[] args) {

        int opcao = -1;

        while (opcao != 0) {

            String menu = "===== LOCADORA DE VEÍCULOS =====\n"
                    + "1 - Cadastrar Cliente\n"
                    + "2 - Cadastrar Carro\n"
                    + "3 - Cadastrar Moto\n"
                    + "4 - Listar Veículos Disponíveis\n"
                    + "5 - Realizar Aluguel\n"
                    + "6 - Listar Clientes\n"
                    + "0 - Sair";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.PLAIN_MESSAGE);

            if (entrada == null) {
                break;
            }

            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite apenas um número do menu!");
                continue;
            }

            switch (opcao) {
                case 1 -> clienteDAO.cadastrarViaMenu();
                case 2 -> veiculoDAO.cadastrarCarroViaMenu();
                case 3 -> veiculoDAO.cadastrarMotoViaMenu();
                case 4 -> veiculoDAO.listarDisponiveisViaMenu();
                case 5 -> aluguelDAO.realizarViaMenu();
                case 6 -> clienteDAO.listarViaMenu();
                case 0 -> JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
}