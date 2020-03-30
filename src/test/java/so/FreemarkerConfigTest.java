package so;

import freemarker.template.Template;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

@ExtendWith({SpringExtension.class})
@Import(FreemarkerConfig.class)
public class FreemarkerConfigTest {

    Map<String,String> data = new HashMap<>();

    @Autowired
    @Qualifier("emailConfigBean")
    private FreeMarkerConfigurationFactoryBean emailConfig;

    @Test
    void testFreemarkerTemplate() {
        // setup data
        data.put("message", "Hello World!");

        // check if autowiring works
        Assertions.assertNotNull(emailConfig);

        // test if loading template works
        try {
            Template template =
                    emailConfig
                        .getObject()                        // <-- get the configuration
                        .getTemplate("email.ftl");    // <-- load the template

            Assertions.assertNotNull(template);

            // dump the generated output to System.err
            template.process(data, new OutputStreamWriter(System.err));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
