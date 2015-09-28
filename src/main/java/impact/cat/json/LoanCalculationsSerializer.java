package impact.cat.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import impact.cat.dao.LoanCalculations;

import java.io.IOException;

import static impact.cat.json.LoanDeserializer.*;

/**
 * Class create JSON like
 *  { sum: 123, days: 5, interest: 18.45, totalSum: 141.45, token: "myIdentifier" }
 * User: mf
 * Date: 28.09.15
 * Time: 19:40
 */
public class LoanCalculationsSerializer extends JsonSerializer<LoanCalculations> {

    public static final String INTEREST_JSON_FIELD = "interest";

    public static final String TOTAL_SUM_JSON_FIELD = "totalSum";

    public static final String TOKEN_JSON_FIELD = "token";

    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param loanCalculations    Value to serialize; can <b>not</b> be null.
     * @param jsonGenerator     Generator used to output resulting Json content
     * @param provider Provider that can be used to get serializers for
     */
    @Override
    public void serialize(LoanCalculations loanCalculations, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(SUM_JSON_FIELD, loanCalculations.getSum());
        jsonGenerator.writeNumberField(DAYS_JSON_FIELD, loanCalculations.getDays());
        jsonGenerator.writeNumberField(INTEREST_JSON_FIELD, loanCalculations.getInterest());
        jsonGenerator.writeNumberField(TOTAL_SUM_JSON_FIELD, loanCalculations.getTotalSum());
        jsonGenerator.writeStringField(TOKEN_JSON_FIELD, loanCalculations.getToken());
        jsonGenerator.writeEndObject();
    }
}