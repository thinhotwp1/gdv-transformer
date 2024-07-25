package marko.gdv.transformer.process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.GroovyClassLoader;
import lombok.extern.log4j.Log4j2;
import marko.gdv.transformer.entity.Contracts;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

@Log4j2
public class GdvTransformProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        JsonNode inputJson = exchange.getIn().getBody(JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();

        String inputJsonString = objectMapper.writeValueAsString(inputJson);
        String transformedJsonString = transformJson(inputJsonString);

        // Convert transformedJsonString back to JsonNode if needed
        JsonNode transformedJsonNode = objectMapper.readTree(transformedJsonString);
        System.out.println(transformedJsonNode);

        // Map the transformed JSON to ContractsData
        Contracts contractsData = objectMapper.treeToValue(transformedJsonNode, Contracts.class);
        System.out.println(contractsData.getContracts().size());

        // Set the transformed JSON to the exchange body
        exchange.getIn().setBody(transformedJsonNode);
    }

    private String transformJson(String inputJson) {
        try {
            Class<?> groovyClass;
            try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
                groovyClass = classLoader.parseClass(new File("transform.groovy"));
            }

            return (String) groovyClass.getMethod("convert", String.class).invoke(null, inputJson);
        } catch (Exception e) {
            log.error("Error during Groovy script execution: " + e.getMessage(), e);
            return null;
        }
    }
}
