package uz.pdp.hospital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.hospital.entity.FireBaseToken;
import uz.pdp.hospital.exception.SmsSendingFailException;
import uz.pdp.hospital.exception.SmsServiceBroken;
import uz.pdp.hospital.model.request.SmsModel;
import uz.pdp.hospital.model.request.SmsToken;
import uz.pdp.hospital.model.response.SmsResponse;
import uz.pdp.hospital.repository.TokenRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uz.pdp.hospital.enums.Constants.CAN_NOT_SEND_SMS;
import static uz.pdp.hospital.enums.Constants.CAN_NOT_TAKE_SMS_SENDING_SERVICE_TOKEN;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final TokenRepository tokenRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    private static final String userEmail = "samandarshodmonov1998@gmail.com";
    private static final  String userSecret = "TaEotaLMYaUqqGTgTrzjGnmIFo7whQfaf82vMU8o";

    private static final String GET_TOKEN = "https://notify.eskiz.uz/api/auth/login";
    private static final String RELOAD_TOKEN = "https://notify.eskiz.uz/api/auth/refresh";
    private static final String SMS_SEND = "https://notify.eskiz.uz/api/message/sms/send";

    public String getToken() {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", userEmail);
            requestBody.put("password", userSecret);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody);
            ResponseEntity<String> response = restTemplate.postForEntity(GET_TOKEN, request, String.class);
            String body = response.getBody();
            SmsToken smsToken = objectMapper.readValue(body, SmsToken.class);
            FireBaseToken build = FireBaseToken.builder().token(smsToken.getData().getToken()).build();
            FireBaseToken fireBaseToken = new FireBaseToken();
            List<FireBaseToken> all = tokenRepository.findAll();
            if (all.isEmpty()) {
                fireBaseToken = tokenRepository.save(build);
            } else {
                fireBaseToken.setId( all.get(0).getId());
                fireBaseToken.setToken(build.getToken());
                fireBaseToken = tokenRepository.save(fireBaseToken);
            }
            return fireBaseToken.getToken();
        } catch (Exception e) {
            throw new SmsServiceBroken(CAN_NOT_TAKE_SMS_SENDING_SERVICE_TOKEN);
        }
    }

    public SmsResponse sendSms(SmsModel smsModel) {
        HttpEntity<Map<String, String>> request = null;
        HttpHeaders headers = new HttpHeaders();
        String token = null;
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            List<FireBaseToken> all = tokenRepository.findAll();
            if (!all.isEmpty()) {
                token = all.get(0).getToken();
            } else {
                token = getToken();
            }
            headers.set("Authorization", "Bearer " + token);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("mobile_phone", "998" + smsModel.getMobilePhone());
            requestBody.put("message", smsModel.getMessage());
            requestBody.put("from", String.valueOf(smsModel.getFrom()));
            requestBody.put("callback_url", smsModel.getCallBackUrl());
            request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(SMS_SEND, request, String.class);
            String body = response.getBody();
            return objectMapper.readValue(body, SmsResponse.class);
        } catch (Exception e) {
            try {
                token = reFreshToken(token);
                HttpHeaders headers1 = request.getHeaders();
                headers1.setBearerAuth(token);
                restTemplate.postForEntity(SMS_SEND, request, String.class);
            } catch (Exception e1) {
                throw new SmsSendingFailException(CAN_NOT_SEND_SMS);
            }
        }
        return null;
    }

    private String reFreshToken(String oldToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + oldToken);
        HttpEntity<?> httpEntity =new HttpEntity<>(headers);
        restTemplate.patchForObject(RELOAD_TOKEN, httpEntity, Void.class);
        return getToken();
    }
}
