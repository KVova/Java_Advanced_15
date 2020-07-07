package volodymyr.one_to_many;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Application {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.configure("/META-INF/hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();

		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);

		Session session = factory.openSession();
		
		Post post = new Post();
		post.setTitle("post 1");
		
		Comment comment1 = new Comment();
		comment1.setAuthorName("author 1");
		comment1.setPost(post);
		
		Comment comment2 = new Comment();
		comment2.setAuthorName("author 2");
		comment2.setPost(post);
		
		Set<Comment> comments = new HashSet<>();
		comments.add(comment1);
		comments.add(comment2);
		
		post.setComments(comments);
		
		// save to DB
		Transaction transaction = session.beginTransaction();
		session.save(post);
		transaction.commit();
		
		// read from DB
		Post postFromDB = (Post) session.get(Post.class, 1);
		System.out.println(postFromDB + "--->" + postFromDB.getComments());
		
		Comment commentFromDB = (Comment) session.get(Comment.class, 2);
		System.out.println(commentFromDB + "--->" + commentFromDB.getPost());
	}
}