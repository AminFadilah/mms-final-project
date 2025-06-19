package id.co.mii.serverapp.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Participant;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;

    public void generateIcsFile(Meeting meeting, String attendeeEmail) {
        try {
                StringBuilder icsContent = new StringBuilder();
                icsContent.append("BEGIN:VCALENDAR\n");
                icsContent.append("METHOD:REQUEST\n");
                icsContent.append("PRODID:Microsoft Exchange Server 2010\n");
                icsContent.append("VERSION:2.0\n");
    
                // Add VTIMEZONE information
                icsContent.append("BEGIN:VTIMEZONE\n");
                icsContent.append("TZID:SE Asia Standard Time\n");
                icsContent.append("BEGIN:STANDARD\n");
                icsContent.append("DTSTART:16010101T000000\n");
                icsContent.append("TZOFFSETFROM:+0700\n");
                icsContent.append("TZOFFSETTO:+0700\n");
                icsContent.append("END:STANDARD\n");
                icsContent.append("BEGIN:DAYLIGHT\n");
                icsContent.append("DTSTART:16010101T000000\n");
                icsContent.append("TZOFFSETFROM:+0700\n");
                icsContent.append("TZOFFSETTO:+0700\n");
                icsContent.append("END:DAYLIGHT\n");
                icsContent.append("END:VTIMEZONE\n");
    
                icsContent.append("BEGIN:VEVENT\n");
                // Set the ORGANIZER with CN and mailto dynamically
                String organizerCN = meeting.getInitiator().getName();
                String organizerEmail = meeting.getInitiator().getEmail();
                icsContent.append("ORGANIZER;CN=").append(organizerCN).append(":mailto:").append(organizerEmail)
                        .append("\n");
    
                // Add attendees
                icsContent.append("ATTENDEE:" + "mailto:" + attendeeEmail + "\n");
    
                icsContent.append("DESCRIPTION;LANGUAGE=en-US:")
                        .append(meeting.getDescription()).append("\n");
                icsContent.append("SUMMARY;LANGUAGE=en-US:").append(meeting.getName()).append("\n");
                System.out.println(meeting.getStartMeeting());
                icsContent.append("DTSTART;TZID=SE Asia Standard Time:")
                        .append(meeting.getStartMeeting().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")))
                        .append("\n");
                icsContent.append("DTEND;TZID=SE Asia Standard Time:")
                        .append(meeting.getEndMeeting().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")))
                        .append("\n");
                icsContent.append("CLASS:PUBLIC\n");
                icsContent.append("PRIORITY:5\n");
                icsContent.append("DTSTAMP:")
                        .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")))
                        .append("\n");
                icsContent.append("TRANSP:OPAQUE\n");
                icsContent.append("STATUS:CONFIRMED\n");
                icsContent.append("SEQUENCE:0\n");
                icsContent.append("LOCATION;LANGUAGE=en-US:").append(meeting.getLinkRoom()).append("\n");
                icsContent.append("END:VEVENT\n");
                icsContent.append("END:VCALENDAR");
    
                String icsFileName = "meeting.ics";
    
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    
                helper.setTo(attendeeEmail);
                helper.setSubject("Invitation: " + meeting.getName());
                helper.setText("Please find the calendar invitation attached.", true);
    
                // Attach the ICS file
                helper.addAttachment(icsFileName,
                        new ByteArrayDataSource(icsContent.toString().getBytes(), "text/calendar"));
    
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
                // Handle exception here
            }
        }
    }
