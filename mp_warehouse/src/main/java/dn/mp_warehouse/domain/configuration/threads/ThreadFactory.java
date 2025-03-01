package dn.mp_warehouse.domain.configuration.threads;

@FunctionalInterface
public interface ThreadFactory {

    Thread newThread(Runnable runnable);

}
