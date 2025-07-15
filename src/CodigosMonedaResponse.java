import java.util.List;

public class CodigosMonedaResponse {
    private String result;
    private List<List<String>> supported_codes;

    public String getResult() {
        return result;
    }

    public List<List<String>> getSupported_codes() {
        return supported_codes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCódigos soportados:\n");

        if (supported_codes != null) {
            for (List<String> codigo : supported_codes) {
                if (codigo.size() == 2) {
                    sb.append(codigo.get(0)).append(" - ").append(codigo.get(1)).append("\n");
                }
            }
        } else {
            sb.append("Sin códigos disponibles\n");
        }

        return sb.toString();
    }

}
