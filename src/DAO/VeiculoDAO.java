package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import model.Carro;
import model.Moto;
import model.Veiculo;


public class VeiculoDAO {

    public void cadastrarCarro(Carro carro) {
        String sql = "INSERT INTO veiculos (tipo, modelo, placa, valor_diaria, disponivel, num_portas) "
                + "VALUES ('CARRO', ?, ?, ?, TRUE, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getPlaca());
            ps.setDouble(3, carro.getValorDiaria());
            ps.setInt(4, carro.getNumPortas());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar carro: " + e.getMessage());
        }
    }

    public void cadastrarMoto(Moto moto) {
        String sql = "INSERT INTO veiculos (tipo, modelo, placa, valor_diaria, disponivel, cilindrada) "
                + "VALUES ('MOTO', ?, ?, ?, TRUE, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, moto.getModelo());
            ps.setString(2, moto.getPlaca());
            ps.setDouble(3, moto.getValorDiaria());
            ps.setInt(4, moto.getCilindrada());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar moto: " + e.getMessage());
        }
    }

    public List<Veiculo> listarDisponiveis() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE disponivel = TRUE ORDER BY id";
        try (Connection con = ConexaoBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(montarVeiculo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos: " + e.getMessage());
        }
        return lista;
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos ORDER BY id";
        try (Connection con = ConexaoBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(montarVeiculo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) {
        String sql = "UPDATE veiculos SET disponivel = ? WHERE id = ?";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, disponivel);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    private Veiculo montarVeiculo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        Veiculo v;

        if (tipo.equals("CARRO")) {
            v = new Carro(rs.getString("modelo"), rs.getString("placa"),
                    rs.getDouble("valor_diaria"), rs.getInt("num_portas"));
        } else {
            v = new Moto(rs.getString("modelo"), rs.getString("placa"),
                    rs.getDouble("valor_diaria"), rs.getInt("cilindrada"));
        }
        v.setId(rs.getInt("id"));
        v.setDisponivel(rs.getBoolean("disponivel"));
        return v;
    }


    public void cadastrarCarroViaMenu() {
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
            cadastrarCarro(carro);

            JOptionPane.showMessageDialog(null, "Carro cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos!");
        }
    }

    public void cadastrarMotoViaMenu() {
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
            cadastrarMoto(moto);

            JOptionPane.showMessageDialog(null, "Moto cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos!");
        }
    }

    public void listarDisponiveisViaMenu() {
        List<Veiculo> veiculos = listarDisponiveis();

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

    public Veiculo buscarPorId(List<Veiculo> lista, int id) {
        for (Veiculo v : lista) {
            if (v.getId() == id) return v;
        }
        return null;
    }
}