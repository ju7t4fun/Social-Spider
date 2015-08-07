package com.epam.lab.spider.model;

import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.entity.sync.*;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yura Kovalik
 */
public class SynchronizedWrapperUtils {
    abstract static class Command<E extends PersistenceIdentifiable>{
        public abstract EntitySynchronized<E> wrap(E entity);
    }

    static Map<PersistenceGlobalIdentifier,WeakReference<EntitySynchronized>> weakCache = new HashMap<>();

    private static Map<Class<? extends PersistenceIdentifiable>, Command<?>> wrapCallBacks = new HashMap<>();

    public static<E extends PersistenceIdentifiable> void addCallBack(Class<E> clazz,Command<E> command){
        wrapCallBacks.put(clazz,command);
    }

    static {
        addCallBack(Attachment.class, new Command<Attachment>() {
            public AttachmentSynchronized wrap(Attachment entity) {
                return new AttachmentSynchronized(entity);
            }
        });
        addCallBack(Category.class, new Command<Category>() {
            public CategorySynchronized wrap(Category entity) {
                return new CategorySynchronized(entity);
            }
        });
        addCallBack(Event.class, new Command<Event>() {
            public EventSynchronized wrap(Event entity) {
                return new EventSynchronized(entity);
            }
        });
        addCallBack(Filter.class, new Command<Filter>() {
            public FilterSynchronized wrap(Filter entity) {
                return new FilterSynchronized(entity);
            }
        });
        addCallBack(Message.class, new Command<Message>() {
            public MessageSynchronized wrap(Message entity) {
                return new MessageSynchronized(entity);
            }
        });
        addCallBack(Owner.class, new Command<Owner>() {
            public OwnerSynchronized wrap(Owner entity) {
                return new OwnerSynchronized(entity);
            }
        });
        addCallBack(PostingTask.class, new Command<PostingTask>() {
            public PostingTaskSynchronized wrap(PostingTask entity) {
                return new PostingTaskSynchronized(entity);
            }
        });
        addCallBack(Post.class, new Command<Post>() {
            public PostSynchronized wrap(Post entity) {
                return new PostSynchronized(entity);
            }
        });
        addCallBack(Profile.class, new Command<Profile>() {
            public ProfileSynchronized wrap(Profile entity) {
                return new ProfileSynchronized(entity);
            }
        });
        addCallBack(Task.class, new Command<Task>() {
            public TaskSynchronized wrap(Task entity) {
                return new TaskSynchronized(entity);
            }
        });
        addCallBack(User.class, new Command<User>() {
            public UserSynchronized wrap(User entity) {
                return new UserSynchronized(entity);
            }
        });
        addCallBack(UserActions.class, new Command<UserActions>() {
            public UserActionsSynchronized wrap(UserActions entity) {
                return new UserActionsSynchronized(entity);
            }
        });
        addCallBack(Wall.class, new Command<Wall>() {
            public WallSynchronized wrap(Wall entity) {
                return new WallSynchronized(entity);
            }
        });
    }

    public static <E extends PersistenceIdentifiable> E wrap(E entity){
        if(entity instanceof EntitySynchronized)return entity;
        try {
            Class[] interfaces = entity.getClass().getInterfaces();
            for(Class aInterface:interfaces){
                if(aInterface.isInstance(PersistenceIdentifiable.class)){
                    Command<E> command = (Command<E>) wrapCallBacks.get(aInterface);
                    EntitySynchronized<E> wrapped = command.wrap(entity);
                    return(E)cache( wrapped,aInterface);
                }
            }
        }catch (ClassCastException x){}
        return entity;
    }

    private static <C extends PersistenceIdentifiable, T extends EntitySynchronized<? extends C>> T cache(T entity, Class<C> clazz){
        PersistenceGlobalIdentifier gId= new PersistenceGlobalIdentifier(clazz,entity.getId());
        weakCache.put(gId,new WeakReference<EntitySynchronized>(entity));
        return entity;
    }
}
