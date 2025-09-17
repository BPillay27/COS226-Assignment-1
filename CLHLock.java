import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    private static class QNode {
        volatile boolean locked = false;
    }

    private final AtomicReference<QNode> tail;
    private final ThreadLocal<QNode> currNode;
    private final ThreadLocal<QNode> myPred;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        currNode = ThreadLocal.withInitial(QNode::new);
        myPred = new ThreadLocal<>();
    }

    @Override
    public void lock() {
        // done
        QNode node = currNode.get();
        node.locked = true;
        QNode pred = tail.getAndSet(node);
        myPred.set(pred);

        int spins = 0;

        while (pred.locked) {
            if ((++spins & 0x3FF) == 0) {
                Thread.yield();
            }
        }
    }

    @Override
    public void unlock() {
        // done
        QNode node = currNode.get();
        node.locked = false;

        currNode.set(myPred.get());
    }

    // may need to @overide the lock and unlock functions
}
