

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AluguelDAO {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void registrar(Aluguel aluguel) {
        String sql = "INSERT INTO alugueis (cliente_id, veiculo_id, dias, valor_total) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aluguel.getCliente().getId());
            ps.setInt(2, aluguel.getVeiculo().getId());
            ps.setInt(3, aluguel.getDias());
            ps.setDouble(4, aluguel.getValorTotal());
            ps.executeUpdate();

            // Depois de alugar, o veículo fica indisponível
            veiculoDAO.atualizarDisponibilidade(aluguel.getVeiculo().getId(), false);

        } catch (SQLException e) {
            System.out.println("Erro ao registrar aluguel: " + e.getMessage());
        }
    }
}
