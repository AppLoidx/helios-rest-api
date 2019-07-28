package com.apploidxxx.mail;

import com.sendgrid.*;
import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException {
        Email from = new Email("noreply@example.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("apploidyakutsk@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}