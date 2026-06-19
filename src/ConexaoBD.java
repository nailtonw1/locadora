
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/locadora";
    private static final String USUARIO = "root";
    private static final String SENHA = "Nail@1597";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao conectar no banco de dados:\n" + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}