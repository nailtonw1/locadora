package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import model.Cliente;


public class ClienteDAO {

    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf) VALUES (?, ?)";
        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id";
        try (Connection con = ConexaoBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(rs.getString("nome"), rs.getString("cpf"));
                c.setId(rs.getInt("id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }


    public void cadastrarViaMenu() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        if (nome == null || nome.isBlank()) return;

        String cpf = JOptionPane.showInputDialog("CPF do cliente:");
        if (cpf == null) return;

        Cliente cliente = new Cliente(nome, cpf);
        cadastrar(cliente);

        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
    }

    public void listarViaMenu() {
        List<Cliente> clientes = listarTodos();

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

    public Cliente buscarPorId(List<Cliente> lista, int id) {
        for (Cliente c : lista) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}