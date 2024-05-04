package telran.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Friday13Range implements Iterable<Temporal> {

    private Temporal from;
    private Temporal to;

    public Friday13Range(Temporal from, Temporal to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<Temporal> iterator() {
        return new FridayIterator();
    }

    private class FridayIterator implements Iterator<Temporal> {

        private Temporal current = getNextFriday13(from);

        @Override
        public boolean hasNext() {
            LocalDate currentDate = LocalDate.from(current);
            LocalDate toDate = LocalDate.from(to);
            return !currentDate.isAfter(toDate);
        }

        @Override
        public Temporal next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Temporal result = current;
            current = getNextFriday13(current.plus(1, ChronoUnit.DAYS));
            return result;
        }

        private Temporal getNextFriday13(Temporal temporal) {
            return LocalDate.from(temporal).with(new NextFriday13());
        }
    }
}