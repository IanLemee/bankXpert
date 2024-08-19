package tech.ian.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ian.user.client.dtoClient.ResponseBrapiDto;

@FeignClient(name = "BrapiClient", url = "https://brapi.dev")
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{stockName}")
    ResponseBrapiDto searchStocks(@RequestParam("token") String token, @RequestParam("stockName") String stockName);
}
