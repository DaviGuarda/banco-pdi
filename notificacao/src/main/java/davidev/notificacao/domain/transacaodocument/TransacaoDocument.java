package davidev.notificacao.domain.transacaodocument;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "transacoes")
@Builder
public class TransacaoDocument implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Field(type = FieldType.Keyword)
    private UUID contaOrigemId;

    @Field(type = FieldType.Keyword)
    private UUID contaDestinoId;

    @Field(type = FieldType.Double)
    private BigDecimal valor;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime dataHora;

    @Field(type = FieldType.Text)
    private String descricao;
}
