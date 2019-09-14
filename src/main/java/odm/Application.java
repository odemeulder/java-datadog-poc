package odm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

@SpringBootApplication
public class Application {

  private static final StatsDClient statsd = new NonBlockingStatsDClient(
    "odm.jm",                          /* prefix to any stats; may be null or empty string */
    "localhost",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
  );

	public static void main(String[] args) {
    statsd.incrementCounter("app_start");
		SpringApplication.run(Application.class, args);
	}
}