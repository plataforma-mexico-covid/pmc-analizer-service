package mx.mexicocovid19.plataforma.pmc.analizer.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@Profile("mailon")
public class MailConfig {
    
	private static final String PROPERTY_NAME_HOST = "mail.host";
    private static final String PROPERTY_NAME_PORT = "mail.port";
    private static final String PROPERTY_NAME_USERNAME = "mail.username";
    private static final String PROPERTY_NAME_PASSWORD = "mail.password";

    private static final String PROPERTY_NAME_SMTP_AUTH = "mail.smtp.auth";
    private static final String PROPERTY_NAME_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String PROPERTY_NAME_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String PROPERTY_DEBUG = "mail.debug";

    private static final String PROPERTY_NAME_FROM_DEFAULT = "mail.from.default";
    private static final String PROPERTY_NAME_TO_DEFAULT = "mail.to.default";
    private static final String PROPERTY_NAME_SUBJECT_DEFAULT = "mail.subject.default";

    @Resource
    protected Environment env;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getRequiredProperty(PROPERTY_NAME_HOST));
        mailSender.setPort(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_PORT)));
        mailSender.setUsername(env.getRequiredProperty(PROPERTY_NAME_USERNAME));
        mailSender.setPassword(env.getRequiredProperty(PROPERTY_NAME_PASSWORD));
        mailSender.setJavaMailProperties(javaMailProperties());

        return mailSender;
    }

    @Bean()
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        props.setProperty("resource.loader", "class");
        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.setProperty("file.resource.loader.path", "classpath:/email");
        velocityEngine.init(props);
        return velocityEngine;
    }

    @Bean
    public SimpleMailMessage templateMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(env.getRequiredProperty(PROPERTY_NAME_FROM_DEFAULT));
        simpleMailMessage.setTo(env.getRequiredProperty(PROPERTY_NAME_TO_DEFAULT));
        simpleMailMessage.setSubject(env.getRequiredProperty(PROPERTY_NAME_SUBJECT_DEFAULT));

        return simpleMailMessage;
    }

    private Properties javaMailProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_SMTP_AUTH, env.getRequiredProperty(PROPERTY_NAME_SMTP_AUTH));
        properties.put(PROPERTY_NAME_SMTP_STARTTLS_ENABLE, env.getRequiredProperty(PROPERTY_NAME_SMTP_STARTTLS_ENABLE));
        properties.put(PROPERTY_NAME_TRANSPORT_PROTOCOL, env.getRequiredProperty(PROPERTY_NAME_TRANSPORT_PROTOCOL));
        properties.put(PROPERTY_DEBUG, env.getRequiredProperty(PROPERTY_DEBUG));
        return properties;
    }
}
