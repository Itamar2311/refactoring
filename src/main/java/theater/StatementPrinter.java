package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private Invoice invoice;
    @SuppressWarnings({"checkstyle:DeclarationOrder", "checkstyle:SuppressWarnings"})
    private static Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        int totalAmount = 0;
        totalAmount = getTotalAmount();
        final int volumeCredits = getTotalVolumeCredits();
        final StringBuilder result = new StringBuilder();
        result.append("Statement for ")
                .append(invoice.getCustomer())
                .append(System.lineSeparator());

        for (Performance p : invoice.getPerformances()) {
            final int rslt = getAmount(p, getPlay(p));
            result.append(String.format("  %s: %s (%s seats)%n",
                    getPlay(p).getName(),
                    usd(rslt),
                    p.getAudience()));
        }

        result.append(String.format("Amount owed is %s%n", usd(totalAmount)));
        result.append(String.format("You earned %s credits%n", volumeCredits));
        return result.toString();
    }

    private int getTotalAmount() {
        int total = 0;
        for (Performance p : invoice.getPerformances()) {
            total += getAmount(p, getPlay(p));
        }
        return total;
    }

    private int getTotalVolumeCredits() {
        int total = 0;
        for (Performance p : invoice.getPerformances()) {
            total += getVolumeCredits(p);
        }
        return total;
    }

    private int getVolumeCredits(Performance performance) {
        int credits = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);

        // extra credit for comedy
        if ("comedy".equals(getPlay(performance).getType())) {
            credits += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }

        return credits;
    }

    private static Play getPlay(Performance performance) {
        final Play play = plays.get(performance.getPlayID());
        return play;
    }

    private static int getAmount(Performance performance, Play play) {
        int rslt = 0;
        switch (getPlay(performance).getType()) {
            case "tragedy":
                rslt = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    final int extra = performance.getAudience() - Constants.TRAGEDY_AUDIENCE_THRESHOLD;
                    rslt += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON * extra;
                }
                break;
            case "comedy":
                rslt = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    rslt += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                rslt += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", getPlay(performance).getType()));
        }
        return rslt;
    }

    private String usd(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(amount / (double) Constants.PERCENT_FACTOR);
    }
}
