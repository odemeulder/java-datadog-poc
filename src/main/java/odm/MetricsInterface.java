package odm;

public interface MetricsInterface {
  public void increment(String name);
  public void decrement(String name);
  public void gauge(String name, long value);
}