package mireka.filter.builtin.local;

import javax.enterprise.context.Dependent;

import mireka.address.Recipient;
import mireka.filter.FilterReply;
import mireka.filter.StatelessFilterType;

import org.subethamail.smtp.RejectException;

@Dependent
public class AcceptPostmaster extends StatelessFilterType {

    @Override
    public FilterReply verifyRecipient(Recipient recipient)
            throws RejectException {
        if (recipient.isPostmaster())
            return FilterReply.ACCEPT;
        else
            return FilterReply.NEUTRAL;
    }
}
