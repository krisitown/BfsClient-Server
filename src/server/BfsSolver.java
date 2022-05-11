package server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

        do {
            for (int i = 0; i < queue.size(); i++) {
                WorkerThread worker = new WorkerThread(visited, queue, arguments.getGraph());
                Future future = executorService.submit(worker);
                tasks.add(future);
            }

            for (Future task : tasks) {
                try {
                    task.get(40, TimeUnit.SECONDS); //wait 40 seconds MAX for each task to complete
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        } while (queue.size() != 0); //after waiting for the futures to complete there might be new entries in the queue

        return visited.stream().map(Object::toString).collect(Collectors.toList());
    }

    class WorkerThread implements Runnable {
        private List<Character> visited;
        private LinkedList<Character> queue;
        private Map<Character, List<Character>> graph;

        public WorkerThread(List<Character> visited, LinkedList<Character> queue, Map<Character, List<Character>> graph) {
            this.visited = visited;
            this.queue = queue;
            this.graph = graph;
        }

        @Override
        public void run() {
            Character currentNode = queue.poll();
            List<Character> children = graph.get(currentNode);
            if(children != null) {
                for (Character child : children) {
                    if (!visited.contains(child)) {
                        visited.add(child);
                        queue.add(child);
                    }
                }
            }
        }
    }
}
