package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class WebApiExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        {
            // 환율 가져오기
            // https://open.er-api.com/v6/latest/USD
            URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = br.lines().collect(Collectors.joining());
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            ExRateData data = mapper.readValue(response, ExRateData.class);
            BigDecimal exRate = data.rates().get("KRW");

            System.out.println("API ExRate: " + data.rates().get("KRW"));

            return exRate;
        }
    }
}
