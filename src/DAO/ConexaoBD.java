
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexaoBD {

    private static final String URL = "**********";
    private static final String USUARIO = "*****";
    private static final String SENHA = "******";

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
