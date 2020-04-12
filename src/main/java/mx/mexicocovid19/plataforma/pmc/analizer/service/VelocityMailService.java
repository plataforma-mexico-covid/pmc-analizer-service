package mx.mexicocovid19.plataforma.pmc.analizer.service;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.TipoEmailEnum;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.util.AnalizerUtils;

@Component
@Profile("mailon")
public class VelocityMailService implements MailService{

	private static final Logger logger = LogManager.getLogger(VelocityMailService.class);

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private SimpleMailMessage templateMessage;

	public void sendNotification(Ayuda ayuda) {
		logger.info("***************Sending message for Ayuda: {}", ayuda);
		ayuda.getCiudadano().getUser().getUsername();
	}

	@Override
	public void sendNotification(Ayuda ayuda, List<Ayuda> cercanos) {
		
		logger.info("***************Sending message for Ayuda: {}", ayuda);

		TipoEmailEnum tipoEmail = OrigenAyuda.OFRECE == ayuda.getOrigenAyuda() ? TipoEmailEnum.OFRECE_AYUDA_REMINDER
				: TipoEmailEnum.SOLICITA_AYUDA_REMINDER;
		String toEmail = ayuda.getCiudadano().getUser().getUsername();
//		String[] ccEmails = AnalizerUtils.extractEmails(cercanos);
		Map<String, Object> emailVariables = AnalizerUtils.createEmailVariables(tipoEmail, ayuda, cercanos);

		try {
			send(toEmail, null, emailVariables, tipoEmail);
		} catch (MessagingException e) {
			logger.error("Error while sending reminder email. ", e);
		}
	}

	private void send(String to, String[] cc, Map<String, Object> variables, TipoEmailEnum tipoEmailEnum)
			throws MessagingException {
		templateMessage.setTo(to);
//		templateMessage.setCc(cc);
		templateMessage.setSubject(tipoEmailEnum.getSubject());
		send(templateMessage, convertToVelocityContext(variables), tipoEmailEnum.getTemplate());
	}

	private VelocityContext convertToVelocityContext(final Map<String, Object> hTemplateVariables) {
		VelocityContext context = new VelocityContext();
		for (Map.Entry<String, Object> row : hTemplateVariables.entrySet()) {
			context.put(row.getKey(), row.getValue());
		}
		return context;
	}

	private void send(final SimpleMailMessage msg, final VelocityContext context, String templateLocation) {
		Template template = velocityEngine.getTemplate(templateLocation);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		MimeMessagePreparator preparator = mimeMessage -> {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(msg.getTo());
//			message.setCc(msg.getCc());
			message.setFrom(msg.getFrom());
			message.setSubject(msg.getSubject());

			String body = writer.toString();

			message.setText(body, true);
		};

		mailSender.send(preparator);
	}

}
