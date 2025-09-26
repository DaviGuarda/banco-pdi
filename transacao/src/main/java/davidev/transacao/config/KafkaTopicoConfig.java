package davidev.transacao.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicoConfig {

    public static final String TOPICO_TRANSACOES_CONCLUIDA = "transacoes-concluidas.0";

    @Bean
    public NewTopic transacoesConcluidasTopico() {
        return TopicBuilder.name(TOPICO_TRANSACOES_CONCLUIDA)
                .partitions(3)
                .replicas(1)
                .build();
    }
}