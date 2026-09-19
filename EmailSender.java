import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.Method;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;

class EmailSender {
    private static final String API_KEY = "<YOUR API KEY>";
    private final SendGrid sg;

    EmailSender() {
        this.sg = new SendGrid(API_KEY);
    }

    void sendEmail(String toEmail, String subject, String body) throws IOException {
        Email from = new Email("<YOUR EMAIL>"); 
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println("Email sent to " + toEmail + " with status code: " + response.getStatusCode());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
