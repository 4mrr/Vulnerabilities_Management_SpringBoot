package vuln.zsmart.ma.vulnnosql.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vuln.zsmart.ma.vulnnosql.Beans.ConfirmationTokenRegistration;
import vuln.zsmart.ma.vulnnosql.DAO.ConfirmationTokenRegistrationDAO;

@Service
public class ConfirmationTokenRegistrationService {

    private JavaMailSender javaMailSender;
    @Autowired
    private ConfirmationTokenRegistrationDAO confirmationTokenRegistrationDAO;

    @Autowired
    public void EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmaill(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void save(ConfirmationTokenRegistration c)
    {
        confirmationTokenRegistrationDAO.save(c);
    }

    public ConfirmationTokenRegistration getCTR(String con)
    {
        return confirmationTokenRegistrationDAO.findByConfirmationToken(con);
    }
}
