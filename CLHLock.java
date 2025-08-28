import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    private static class QNode {volatile boolean locked = false;}

    private final AtomicReference<QNode> tail;
    private final ThreadLocal<QNode> currNode;
    private final ThreadLocal<QNode> myPred;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        currNode = ThreadLocal.withInitial(QNode::new);
        myPred = new ThreadLocal<>();
    }

    public void lock() {
        //TODO: Implement Function
    }

    public void unlock() {
        //TODO: Implement Function
    }
}

    
