package server;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BfsSolver {
    public List<String> solve(Arguments arguments) {
        int poolSize = arguments.getNumberOfThreads();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Future> tasks = new ArrayList<>();

        List<Character> visited = new ArrayList<>();
        visited.add(arguments.getStartingNode());
        LinkedList<Character> queue = new LinkedList<>();
        queue.add(arguments.getStartingNode());

        while (queue.size() != 0) {
            Character currentNode = queue.poll();
            List<Character> children = arguments.getGraph().get(currentNode);

            if (children != null) {
                WorkerThread worker = new WorkerThread(visited, queue, children);
                Future future = executorService.submit(worker);
                tasks.add(future);
            }
        }

        for (Future future : tasks) {
            try {
                future.get(40, TimeUnit.SECONDS); //wait 40 seconds MAX for each task to complete
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }

        return visited.stream().map(Object::toString).collect(Collectors.toList());
    }

    class WorkerThread implements Runnable {
        private List<Character> visited;
        private LinkedList<Character> queue;
        private List<Character> children;

        public WorkerThread(List<Character> visited, LinkedList<Character> queue, List<Character> children) {
            this.visited = visited;
            this.queue = queue;
            this.children = children;
        }

        @Override
        public void run() {
            for (Character child : children) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    queue.add(child);
                }
            }
        }
    }
}
