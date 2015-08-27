/*******************************************************************************
 * Copyright 2014 Springer Science+Business Media Deutschland GmbH
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package omelet.mail;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/***
 * Checking email using pop3 protocol
 *
 * @author kapilA
 */
public class Email implements IEmail {
    final private String host;
    final private String port;
    final private String userName;
    final private String password;
    private Properties props = System.getProperties();
    private String folderName;
    private Folder folder;

    private static final int maxcountEMailCheck = 10;
    private static final Logger LOGGER = Logger.getLogger(Email.class);

    private Email(Builder builder) {
        MailProtocol protocol;

        this.host = builder.host;
        this.port = builder.port;
        this.userName = builder.userName;
        this.password = builder.password;
        protocol = builder.protocol;
        this.folderName = "Inbox";
        connect(protocol);
    }

    private void connect(MailProtocol protocol) {
        Session session;
        switch (protocol) {
            case POP3:
                setPop3Config();
                break;
            case IMAP:
                setImapConfig();
                break;
            case SMTP:
                setSmtpConfig();
                break;
            default:
                setPop3Config();
                break;
        }

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
        connectToStore(protocol.toString().toLowerCase(), session);
    }

    private void setPop3Config() {
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.port", port);
        props.setProperty("mail.store.protocol", "pop3s");
        props.setProperty("mail.pop3.user", userName);
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
    }

    private void setImapConfig() {
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
    }

    private void setSmtpConfig() {
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
    }

    private void connectToStore(String protocol, Session session) {
        Store store;

        try {
            store = session.getStore(protocol);
            store.connect(userName, password);
            folder = store.getFolder(folderName);
        } catch (NoSuchProviderException e) {
            LOGGER.error(e);
        } catch (MessagingException e) {
            LOGGER.error(e);
        }
    }

    /***
     * Builder class to build email objects
     *
     * @author kapil
     */
    public static class Builder {

        private String host;
        private String port;
        private String userName;
        private String password;
        private MailProtocol protocol = MailProtocol.POP3;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(String portNo) {
            this.port = portNo;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setProtocol(MailProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }

    /***
     * Return format of email Message
     */
    @Override
    public String getMailFormat(Message msg) {
        String format = null;

        try {
            format = msg.getContentType();
        } catch (MessagingException e) {

            LOGGER.error(e);
        }
        return format;
    }

    private enum EMAIL_FILTER {
        TO_ADDR, FROM_ADD, SUBJECT
    }

    /***
     * Return List of Message filter by Subject,From_ADD,To_ADDR
     * Element 0 is the newest one!!
     *
     * @param emailFilter enum
     * @param filterText  :text present in Subject of email
     * @return list of messages
     */
    private List<Message> getEmails(EMAIL_FILTER emailFilter, String filterText) {
        Stopwatch sw = new Stopwatch();
        sw.start();

        List<Message> returnMessage = new ArrayList<Message>();
        int loopCount;
        try {
            folder.open(Folder.READ_ONLY);
            Message[] msgs = folder.getMessages();
            int inboxMessageCount = folder.getMessageCount();
            LOGGER.info("Message count is: " + inboxMessageCount);
            if (inboxMessageCount < maxcountEMailCheck) {
                loopCount = 0;
            } else {
                loopCount = inboxMessageCount - maxcountEMailCheck;
            }
            for (int i = inboxMessageCount - 1; i >= loopCount; i--) {
                switch (emailFilter) {
                    case SUBJECT:
                        if (msgs[i].getSubject().toString()
                                .equalsIgnoreCase(filterText)) {
                            returnMessage.add(msgs[i]);
                        }
                        break;
                    case FROM_ADD:
                        // Assumption is from address is only one
                        if (msgs[i].getFrom()[0].toString().contains(filterText)) {
                            returnMessage.add(msgs[i]);
                        }
                        break;
                    case TO_ADDR:
                        for (Address addr : msgs[i].getRecipients(RecipientType.TO)) {
                            LOGGER.info("Sno: " + i + " To Email Add is: "
                                    + addr.toString());
                            if (addr.toString().contains(filterText)) {
                                returnMessage.add(msgs[i]);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            // CLose the folder
            folder.close(true);
        } catch (MessagingException e) {
            LOGGER.error(e);
        }
        sw.stop();
        LOGGER.info("Time Taken by getMessage is: "
                + sw.elapsedTime(TimeUnit.SECONDS));
        return returnMessage;
    }

    /***
     * Get the Body of the Email message
     */
    @Override
    public String getEmailBody(Message message) {
        String line;
        StringBuilder messageBody = new StringBuilder();
        BufferedReader br = null;

        try {
            folder.open(Folder.READ_ONLY);
            br = new BufferedReader(new InputStreamReader(
                    message.getInputStream()));
            while ((line = br.readLine()) != null) {
                messageBody.append(line);
            }
            br.close();
            folder.close(true);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (MessagingException e) {
            LOGGER.error(e);
        }
        return messageBody.toString();
    }

    /***
     * Return the Html link in the body of email after the text
     */
    @Override
    public String getHTMLLinkAfterText(Message message,
                                       String textAfterWhichtoFetchHtmlLinks) {

        String text = getEmailBody(message);
        String filteredText;
        String httpLink = null;
        // check if the URL is present
        if (text.indexOf(textAfterWhichtoFetchHtmlLinks) != -1) {
            filteredText = text.substring(text
                    .indexOf(textAfterWhichtoFetchHtmlLinks));
            // ideally this should be the link
            httpLink = filteredText.substring(filteredText.indexOf("http"))
                    .split(" ")[0].split(">")[0].split("\"")[0];
        } else {
            Reporter.log(text);
        }
        return httpLink;
    }

    /***
     * if not set then folder name default is inbox Note in pop3 protocol folder
     * will always be inbox
     */
    @Override
    public void setFolder(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public List<Message> getEmailsByAdd(FilterEmails searchCat,
                                        String emailAddress) {
        switch (searchCat) {
            case FROM:
                return getEmails(EMAIL_FILTER.FROM_ADD, emailAddress);
            case TO:
                return getEmails(EMAIL_FILTER.TO_ADDR, emailAddress);
            default:
                break;
        }
        return null;
    }

    /***
     * Return List of email messages filtered by Subject text
     */
    @Override
    public List<Message> getEmailsBySubject(String subject) {
        return getEmails(EMAIL_FILTER.SUBJECT, subject);
    }

    /***
     * Return list of email messages filter by {@link FilterEmails} To,From
     */
    @Override
    public List<Message> filterEmailsByAdd(FilterEmails searchCat,
                                           Message[] messages, String emailAddress) {
        List<Message> returnMessage = new ArrayList<Message>();
        try {
            switch (searchCat) {
                case FROM:
                    for (Message msg : messages) {
                        if (msg.getFrom()[0].toString().contains(emailAddress)) {
                            returnMessage.add(msg);
                        }
                    }
                    break;
                case TO:
                    for (Message msg : messages) {
                        for (Address addr : msg.getRecipients(RecipientType.TO)) {
                            if (addr.toString().contains(emailAddress)) {
                                returnMessage.add(msg);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e);
        }
        return returnMessage;
    }

    @Override
    public List<Message> filerEmailsBySubject(List<Message> message,
                                              String emailSubject) {
        Stopwatch sw = new Stopwatch();
        sw.start();
        List<Message> returnMessage = new ArrayList<Message>();
        LOGGER.info("Count of the message for filter by Subject: "
                + message.size());
        for (Message msg : message) {
            try {
                if (msg.getSubject().equalsIgnoreCase(emailSubject)) {
                    returnMessage.add(msg);
                }
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                LOGGER.error(e);
            }
        }
        sw.stop();
        LOGGER.info("Time Taken by Filter EmailBy Subjects is: "
                + sw.elapsedTime(TimeUnit.SECONDS));
        return returnMessage;
    }

    @Override
    public boolean verifyPatternInEmail(Message message, String patterToMatch) {
        // TODO Auto-generated method stub
        String messageBody = getEmailBody(message);
        return Pattern.matches(messageBody, patterToMatch);
    }
}
