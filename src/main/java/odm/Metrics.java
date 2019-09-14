package odm;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class Metrics {
  private static final StatsDClient statsd = new NonBlockingStatsDClient(
    "odm.jm",                          /* prefix to any stats; may be null or empty string */
    "localhost",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"odm"}            /* DataDog extension: Constant tags, always applied */
  );

  public void increment(String name) {
    statsd.increment(name);
  }

  public void decrement(String name) {
    statsd.decrement(name);
  }

  public void gauge(String name, long count) {
    statsd.gauge(name, count);
  }
}