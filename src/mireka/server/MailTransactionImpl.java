package mireka.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mireka.MailData;
import mireka.address.Recipient;
import mireka.filter.Destination;
import mireka.filter.MailTransaction;

import org.subethamail.smtp.MessageContext;

public class MailTransactionImpl implements MailTransaction {
    private final Map<String, Object> attributes =
            new HashMap<String, Object>();

    public final MessageContext messageContext;

    public String from;

    public List<Recipient> recipients = new ArrayList<Recipient>();

    private MailData data;

    /**
     * null means uninitialized or "invalid at this state".
     */
    private Destination destinationForCurrentRecipient;

    /**
     * Contains only accepted recipients.
     */
    private final Map<Recipient, Destination> recipientDestinationMap =
            new HashMap<Recipient, Destination>();

    public MailTransactionImpl(MessageContext messageContext) {
        super();
        this.messageContext = messageContext;
    }

    /**
     * it resets the stream if necessary before returning it
     */
    @Override
    public MailData getData() {
        return data;
    }

    public void setData(MailData data) {
        this.data = data;
    }

    @Override
    public void replaceData(MailData mailData) {
        this.data = mailData;
    }

    @Override
    public MessageContext getMessageContext() {
        return messageContext;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public List<Recipient> getRecipients() {
        return recipients;
    }

    @Override
    public InetAddress getRemoteInetAddress() {
        return ((InetSocketAddress) messageContext.getRemoteAddress())
                .getAddress();
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setDestinationForCurrentRecipient(Destination destination) {
        destinationForCurrentRecipient = destination;
    }

    @Override
    public Destination getDestinationForCurrentRecipient() {
        if (destinationForCurrentRecipient == null)
            throw new IllegalStateException();
        return destinationForCurrentRecipient;
    }

    public void addDestinationForRecipient(Recipient recipient,
            Destination destination) {
        recipientDestinationMap.put(recipient, destination);
    }

}
