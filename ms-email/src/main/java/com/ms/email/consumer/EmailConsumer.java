package com.ms.email.consumer;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
import com.ms.email.repository.EmailRepository;
import com.ms.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public EmailConsumer(EmailRepository emailRepository, EmailService emailService) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto){
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDto, emailModel);
        emailService.sendEmail(emailModel);
    }
}
