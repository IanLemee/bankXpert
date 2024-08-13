package tech.ian.user.producers;

import tech.ian.user.dto.CreateUserResponse;
import tech.ian.user.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.ian.user.entity.Account;
import tech.ian.user.entity.TransactionalAccount;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(CreateUserResponse user) {
        var emailDto = new EmailDto();

        emailDto.setUserId(user.id());
        emailDto.setEmailTo(user.email());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(user.name() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

    public void publishTransactionalEmail(Account userResponse, TransactionalAccount accountTransactionalResponse) {
        var emailDto = new EmailDto();

        emailDto.setUserId(userResponse.getUser().getId());
        emailDto.setEmailTo(userResponse.getEmail());
        emailDto.setSubject("Transacao feita com sucesso");
        emailDto.setText(userResponse.getEmail() + " A transacao foi feita com sucesso, no valor de " + accountTransactionalResponse.getAmount() + " seu saldo atual é de " + accountTransactionalResponse.getSender().getBalance());

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
