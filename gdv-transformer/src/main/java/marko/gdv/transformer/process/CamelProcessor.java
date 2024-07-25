package marko.gdv.transformer.process;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelProcessor {

    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:processJson")
                        .log("Processing JSON data")
                        .unmarshal().json()
                        .process(new GdvTransformProcessor())
                        .marshal().json()
                        .to("mock:result");  // This can be used to verify the result in unit tests
            }
        });
        context.start();
        return context;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext) {
        return new DefaultProducerTemplate(camelContext);
    }
}
