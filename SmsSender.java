import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

class SmsSender {
    public static final String ACCOUNT_SID = "<YOUR ACCOUNT SID>";
    public static final String AUTH_TOKEN = "<YOUR AUTHENTICATION TOKEN>";
    public static final String FROM_NUMBER = "<YOUR TWILIO PHONE NUMBER>"; // your Twilio number

    SmsSender() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    void getDetails(String phoneNumber, String message) {
        try {
            Message msg = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(FROM_NUMBER),
                message
            ).create();
            System.out.println("SMS sent to " + phoneNumber + " with SID: " + msg.getSid());
        } catch (Exception e) {
            System.out.println("Failed to send SMS: " + e.getMessage());
        }
    }
}
