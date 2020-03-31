import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
  StandardServiceRegistry registry;
  Metadata metadata;
  SessionFactory sessionFactory;
  Session session;

  public HibernateUtil() {
    registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    metadata = new MetadataSources(registry).getMetadataBuilder().build();
    sessionFactory = metadata.getSessionFactoryBuilder().build();
    session = sessionFactory.openSession();
  }

  public Session finishedSession() {
    return session;
  }
}