package tech.ian.mail.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.ian.mail.dto.EmailRecordDto;
import tech.ian.mail.entity.EmailEntity;
import tech.ian.mail.service.EmailService;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        var emailEntity = new EmailEntity();
        BeanUtils.copyProperties(emailRecordDto, emailEntity);

        emailService.sendEmail(emailEntity);
    }

}
