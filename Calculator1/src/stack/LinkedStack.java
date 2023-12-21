package stack;

public class LinkedStack<E> implements Stack<E>{
    public LinkedList<E> list=new LinkedList<>();
    public LinkedStack(){}
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addNode(e);
    }

    @Override
    public E top() {
        return list.first();
    }

    @Override
    public E pop() {
        return list.removeNode();
    }
}
