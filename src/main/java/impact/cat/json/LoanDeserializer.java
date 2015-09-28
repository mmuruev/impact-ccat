package impact.cat.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import impact.cat.dao.Loan;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Class fetch data from JSON
 *   { sum: 123, days: 5 }
 * User: mf
 * Date: 28.09.15
 * Time: 19:30
 */
public class LoanDeserializer extends JsonDeserializer<Loan> {

    public static final String SUM_JSON_FIELD = "sum";
    public static final String DAYS_JSON_FIELD = "days";

    /**
     * @inheritDoc
     */
    @Override
    public Loan deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int days = node.get(DAYS_JSON_FIELD).asInt();
        BigDecimal sum = node.get(SUM_JSON_FIELD).decimalValue();
        return new Loan(sum, days);
    }
}