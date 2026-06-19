package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;


public class AluguelDAO {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void registrar(Aluguel aluguel) {
        String sql = "INSERT INTO alugueis (cliente_id, veiculo_id, dias, valor_total) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aluguel.getCliente().getId());
            ps.setInt(2, aluguel.getVeiculo().getId());
            ps.setInt(3, aluguel.getDias());
            ps.setDouble(4, aluguel.getValorTotal());
            ps.executeUpdate();

            veiculoDAO.atualizarDisponibilidade(aluguel.getVeiculo().getId(), false);

        } catch (SQLException e) {
            System.out.println("Erro ao registrar aluguel: " + e.getMessage());
        }
    }


    public void realizarViaMenu() {
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

            Cliente clienteEscolhido = clienteDAO.buscarPorId(clientes, idCliente);
            Veiculo veiculoEscolhido = veiculoDAO.buscarPorId(veiculos, idVeiculo);

            if (clienteEscolhido == null || veiculoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Cliente ou veículo não encontrado.");
                return;
            }

            Aluguel aluguel = new Aluguel(clienteEscolhido, veiculoEscolhido, dias);
            registrar(aluguel);

            JOptionPane.showMessageDialog(null,
                    "Aluguel registrado com sucesso!\n\n" + aluguel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite apenas números nos campos de ID e dias.");
        }
    }
}