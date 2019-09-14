package odm;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.stereotype.Component;

@Component("metrics")
public class Metrics implements MetricsInterface {
  private static final StatsDClient statsd = new NonBlockingStatsDClient(
    "odm.jm",                          /* prefix to any stats; may be null or empty string */
    "localhost",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"odm"}            /* DataDog extension: Constant tags, always applied */
  );

  @Override
  public void increment(String name) {
    statsd.increment(name);
  }

  @Override
  public void decrement(String name) {
    statsd.decrement(name);
  }

  @Override
  public void gauge(String name, long value) {
    statsd.gauge(name, value);
  }
}