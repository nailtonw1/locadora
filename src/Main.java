
import javax.swing.JOptionPane;
import java.util.List;

/**
 * Classe principal do sistema.
 * Usa JOptionPane para fazer um menu simples (entrada e saída de dados).
 */
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

            // Se o usuário clicar em "Cancelar" ou fechar, encerra o programa
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
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarCarro();
                case 3 -> cadastrarMoto();
                case 4 -> listarVeiculosDisponiveis();
                case 5 -> realizarAluguel();
                case 6 -> listarClientes();
                case 0 -> JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }


    static void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        if (nome == null || nome.isBlank()) return;

        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        if (cpf == null) return;

        Cliente cliente = new Cliente(nome, cpf);
        clienteDAO.cadastrar(cliente);

        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
    }


    static void cadastrarCarro() {
        String modelo = JOptionPane.showInputDialog("Modelo do carro:");
        if (modelo == null) return;

        String placa = JOptionPane.showInputDialog("Placa:");
        if (placa == null) return;

        String valorTexto = JOptionPane.showInputDialog("Valor da diária (R$):");
        if (valorTexto == null) return;

        String portasTexto = JOptionPane.showInputDialog("Número de portas:");
        if (portasTexto == null) return;

        try {
            double valorDiaria = Double.parseDouble(valorTexto.replace(",", "."));
            int numPortas = Integer.parseInt(portasTexto.trim());

            Carro carro = new Carro(modelo, placa, valorDiaria, numPortas);
            veiculoDAO.cadastrarCarro(carro);

            JOptionPane.showMessageDialog(null, "Carro cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos!");
        }
    }


    static void cadastrarMoto() {
        String modelo = JOptionPane.showInputDialog("Modelo da moto:");
        if (modelo == null) return;

        String placa = JOptionPane.showInputDialog("Placa:");
        if (placa == null) return;

        String valorTexto = JOptionPane.showInputDialog("Valor da diária (R$):");
        if (valorTexto == null) return;

        String cilindradaTexto = JOptionPane.showInputDialog("Cilindrada (cc):");
        if (cilindradaTexto == null) return;

        try {
            double valorDiaria = Double.parseDouble(valorTexto.replace(",", "."));
            int cilindrada = Integer.parseInt(cilindradaTexto.trim());

            Moto moto = new Moto(modelo, placa, valorDiaria, cilindrada);
            veiculoDAO.cadastrarMoto(moto);

            JOptionPane.showMessageDialog(null, "Moto cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos!");
        }
    }


    static void listarVeiculosDisponiveis() {
        List<Veiculo> veiculos = veiculoDAO.listarDisponiveis();

        if (veiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum veículo disponível no momento.");
            return;
        }

        StringBuilder texto = new StringBuilder("Veículos disponíveis:\n\n");
        for (Veiculo v : veiculos) {
            // Aqui usamos v.toString(), que por sua vez chama getTipo() -> POLIMORFISMO
            texto.append("ID ").append(v.getId()).append(" - ").append(v).append("\n");
        }

        JOptionPane.showMessageDialog(null, texto.toString());
    }


    static void realizarAluguel() {
        // Lista clientes
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cadastre um cliente antes de alugar um veículo.");
            return;
        }

        StringBuilder listaClientes = new StringBuilder("Clientes cadastrados:\n\n");
        for (Cliente c : clientes) {
            listaClientes.append("ID ").append(c.getId()).append(" - ").append(c).append("\n");
        }
        JOptionPane.showMessageDialog(null, listaClientes.toString());

        String idClienteTexto = JOptionPane.showInputDialog("Digite o ID do cliente:");
        if (idClienteTexto == null) return;

        // Lista veículos disponíveis
        List<Veiculo> veiculos = veiculoDAO.listarDisponiveis();
        if (veiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há veículos disponíveis.");
            return;
        }

        StringBuilder listaVeiculos = new StringBuilder("Veículos disponíveis:\n\n");
        for (Veiculo v : veiculos) {
            listaVeiculos.append("ID ").append(v.getId()).append(" - ").append(v).append("\n");
        }
        JOptionPane.showMessageDialog(null, listaVeiculos.toString());

        String idVeiculoTexto = JOptionPane.showInputDialog("Digite o ID do veículo:");
        if (idVeiculoTexto == null) return;

        String diasTexto = JOptionPane.showInputDialog("Quantos dias de aluguel?");
        if (diasTexto == null) return;

        try {
            int idCliente = Integer.parseInt(idClienteTexto.trim());
            int idVeiculo = Integer.parseInt(idVeiculoTexto.trim());
            int dias = Integer.parseInt(diasTexto.trim());

            Cliente clienteEscolhido = buscarClientePorId(clientes, idCliente);
            Veiculo veiculoEscolhido = buscarVeiculoPorId(veiculos, idVeiculo);

            if (clienteEscolhido == null || veiculoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Cliente ou veículo não encontrado.");
                return;
            }


            Aluguel aluguel = new Aluguel(clienteEscolhido, veiculoEscolhido, dias);
            aluguelDAO.registrar(aluguel);

            JOptionPane.showMessageDialog(null,
                    "Aluguel registrado com sucesso!\n\n" + aluguel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite apenas números nos campos de ID e dias.");
        }
    }


    static void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente cadastrado.");
            return;
        }

        StringBuilder texto = new StringBuilder("Clientes cadastrados:\n\n");
        for (Cliente c : clientes) {
            texto.append("ID ").append(c.getId()).append(" - ").append(c).append("\n");
        }

        JOptionPane.showMessageDialog(null, texto.toString());
    }


    static Cliente buscarClientePorId(List<Cliente> lista, int id) {
        for (Cliente c : lista) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    static Veiculo buscarVeiculoPorId(List<Veiculo> lista, int id) {
        for (Veiculo v : lista) {
            if (v.getId() == id) return v;
        }
        return null;
    }
}
