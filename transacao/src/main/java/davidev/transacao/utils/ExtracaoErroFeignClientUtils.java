package davidev.transacao.utils;

import static java.util.Objects.isNull;

public class ExtracaoErroFeignClientUtils {

    public static String extrairMensagemDeErro(Exception e) {
        String mensagem = e.getMessage();

        if (isNull(mensagem)) {
            return "Erro não identificado";
        }

        if (e instanceof feign.FeignException) {
            try {
                String pattern = "\"mensagem\":\"([^\"]+)\"";
                java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
                java.util.regex.Matcher matcher = regex.matcher(mensagem);

                if (matcher.find()) {
                    String mensagemExtraida = matcher.group(1);
                    return truncarMensagem(mensagemExtraida);
                }
            } catch (Exception ex) {
                // Se falhar o parsing, continua com lógica padrão
            }
        }

        return truncarMensagem(mensagem);
    }

    private static String truncarMensagem(String mensagem) {
        if (mensagem == null) {
            return "Erro não identificado";
        }

        return mensagem.length() > 250
                ? mensagem.substring(0, 250) + "..."
                : mensagem;
    }
}
